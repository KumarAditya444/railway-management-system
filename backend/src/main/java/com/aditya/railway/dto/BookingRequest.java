package com.aditya.railway.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record BookingRequest(@NotNull @Positive Long trainId, @NotNull @FutureOrPresent LocalDate travelDate,
 @NotBlank @Size(max=100) String passengerName, @Min(1) @Max(120) int age,
 @NotBlank @Pattern(regexp="Male|Female|Other", message="must be Male, Female, or Other") String gender,
 @NotBlank @Email @Size(max=120) String email,
 @NotBlank @Pattern(regexp="[0-9]{10,15}", message="must contain 10 to 15 digits") String phone) {}
