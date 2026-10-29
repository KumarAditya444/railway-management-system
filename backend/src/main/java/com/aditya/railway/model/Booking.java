package com.aditya.railway.model;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
public record Booking(Long id, String pnr, Long trainId, String trainName, String trainNumber, String sourceStation, String destinationStation, LocalDate travelDate,
                      String passengerName, int age, String gender, String email, String phone,
                      String status, BigDecimal fare, LocalDateTime bookedAt) {}
