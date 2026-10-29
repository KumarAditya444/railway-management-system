package com.aditya.railway.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalTime;
public record TrainRequest(
 @NotBlank @Size(max=12) String trainNumber,
 @NotBlank @Size(max=100) String trainName,
 @NotBlank @Size(max=80) String sourceStation,
 @NotBlank @Size(max=80) String destinationStation,
 @NotNull LocalTime departureTime, @NotNull LocalTime arrivalTime,
 @NotBlank String duration, @NotNull @DecimalMin("0.0") BigDecimal fare,
 @Min(1) @Max(5000) int totalSeats, @Min(0) int availableSeats, boolean active) {}
