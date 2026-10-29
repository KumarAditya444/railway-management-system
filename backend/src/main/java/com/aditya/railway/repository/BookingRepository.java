package com.aditya.railway.repository;
import com.aditya.railway.dto.BookingRequest;import com.aditya.railway.model.Booking;
import org.springframework.jdbc.core.JdbcTemplate;import org.springframework.jdbc.core.RowMapper;import org.springframework.stereotype.Repository;
import java.util.*;
@Repository public class BookingRepository {
 private final JdbcTemplate jdbc; public BookingRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
 private final RowMapper<Booking> mapper=(r,n)->new Booking(r.getLong("id"),r.getString("pnr"),r.getLong("train_id"),r.getString("train_name"),r.getString("train_number"),r.getString("source_station"),r.getString("destination_station"),r.getDate("travel_date").toLocalDate(),r.getString("passenger_name"),r.getInt("age"),r.getString("gender"),r.getString("email"),r.getString("phone"),r.getString("status"),r.getBigDecimal("fare"),r.getTimestamp("booked_at")==null?null:r.getTimestamp("booked_at").toLocalDateTime());
 private static final String SELECT="SELECT b.*, t.train_name, t.train_number FROM bookings b JOIN trains t ON t.id=b.train_id ";
 public List<Booking> findAll(){return jdbc.query(SELECT+"ORDER BY b.booked_at DESC",mapper);}
 public Optional<Booking> findByPnr(String pnr){return jdbc.query(SELECT+"WHERE b.pnr=?",mapper,pnr).stream().findFirst();}
 public Booking book(String pnr, BookingRequest x, java.math.BigDecimal fare){jdbc.update("INSERT INTO bookings(pnr,train_id,travel_date,passenger_name,age,gender,email,phone,fare) VALUES(?,?,?,?,?,?,?,?,?)",pnr,x.trainId(),x.travelDate(),x.passengerName().trim(),x.age(),x.gender(),x.email().trim().toLowerCase(),x.phone(),fare);return findByPnr(pnr).orElseThrow();}
 public boolean cancel(String pnr){return jdbc.update("UPDATE bookings SET status='CANCELLED' WHERE pnr=? AND status='CONFIRMED'",pnr)>0;}
}
