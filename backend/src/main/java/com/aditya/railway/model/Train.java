package com.aditya.railway.model;
import java.math.BigDecimal;
import java.time.LocalTime;
public record Train(Long id, String trainNumber, String trainName, String sourceStation, String destinationStation,
                    LocalTime departureTime, LocalTime arrivalTime, String duration, BigDecimal fare,
                    int totalSeats, int availableSeats, boolean active) {}
