package com.aditya.railway.controller;
import com.aditya.railway.dto.BookingRequest;import com.aditya.railway.model.Booking;import com.aditya.railway.service.RailwayService;
import jakarta.validation.Valid;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/bookings") @CrossOrigin(origins="http://localhost:5173")
public class BookingController {private final RailwayService service;public BookingController(RailwayService s){service=s;}
 @PostMapping public ResponseEntity<Booking> book(@Valid @RequestBody BookingRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.book(r));}
 @GetMapping("/{pnr}") public Booking track(@PathVariable String pnr){return service.findBooking(pnr);}
 @GetMapping public List<Booking> all(){return service.allBookings();}
 @PatchMapping("/{pnr}/cancel") public Booking cancel(@PathVariable String pnr){return service.cancel(pnr);}
}
