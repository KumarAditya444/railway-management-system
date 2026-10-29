package com.aditya.railway.service;
import com.aditya.railway.dto.*;import com.aditya.railway.exception.ApiException;import com.aditya.railway.model.*;import com.aditya.railway.repository.*;
import org.springframework.http.HttpStatus;import org.springframework.jdbc.core.JdbcTemplate;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;
import java.util.*;import java.util.concurrent.ThreadLocalRandom;
@Service public class RailwayService {
 private final TrainRepository trains;private final BookingRepository bookings;private final JdbcTemplate jdbc;
 public RailwayService(TrainRepository trains,BookingRepository bookings,JdbcTemplate jdbc){this.trains=trains;this.bookings=bookings;this.jdbc=jdbc;}
 public List<Train> trains(String source,String destination){return trains.search(blankToNull(source),blankToNull(destination));}
 public Train createTrain(TrainRequest r){if(r.availableSeats()>r.totalSeats())throw new ApiException(HttpStatus.BAD_REQUEST,"Available seats cannot exceed total seats.");if(r.sourceStation().equalsIgnoreCase(r.destinationStation()))throw new ApiException(HttpStatus.BAD_REQUEST,"Source and destination must be different.");return trains.create(r);}
 public Train updateTrain(long id,TrainRequest r){if(r.availableSeats()>r.totalSeats())throw new ApiException(HttpStatus.BAD_REQUEST,"Available seats cannot exceed total seats.");return trains.update(id,r);}
 public boolean deleteTrain(long id){try{return trains.delete(id);}catch(org.springframework.dao.DataIntegrityViolationException e){throw new ApiException(HttpStatus.CONFLICT,"Cannot delete a train that has booking records.");}}
 @Transactional public Booking book(BookingRequest r){
   // Conditional update prevents overbooking when concurrent requests arrive.
   int updated=jdbc.update("UPDATE trains SET available_seats=available_seats-1 WHERE id=? AND active=TRUE AND available_seats>0",r.trainId());
   if(updated==0){if(trains.findById(r.trainId()).isEmpty())throw new ApiException(HttpStatus.NOT_FOUND,"Train not found.");throw new ApiException(HttpStatus.CONFLICT,"No seats are available on this train.");}
   Train train=trains.findById(r.trainId()).orElseThrow();String pnr;
   do{pnr="RC"+String.format("%010d",ThreadLocalRandom.current().nextLong(1_000_000_000L,9_999_999_999L));}while(bookings.findByPnr(pnr).isPresent());
   return bookings.book(pnr,r,train.fare());
 }
 public Booking findBooking(String pnr){return bookings.findByPnr(pnr.toUpperCase()).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"No booking found for that PNR."));}
 public List<Booking> allBookings(){return bookings.findAll();}
 @Transactional public Booking cancel(String pnr){Booking b=findBooking(pnr);if(!"CONFIRMED".equals(b.status()))throw new ApiException(HttpStatus.CONFLICT,"This booking is already cancelled.");if(!bookings.cancel(pnr.toUpperCase()))throw new ApiException(HttpStatus.CONFLICT,"Unable to cancel booking.");jdbc.update("UPDATE trains SET available_seats=LEAST(total_seats,available_seats+1) WHERE id=?",b.trainId());return findBooking(pnr);}
 private String blankToNull(String x){return x==null||x.isBlank()?null:x.trim();}
}
