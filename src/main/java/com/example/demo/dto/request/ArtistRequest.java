package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be a valid 10-digit number")
    private String phone;

    @NotBlank(message = "Language specialization is required")
    @Size(max = 150, message = "Language specialization must not exceed 150 characters")
    private String languageSpecialization;

    @NotNull(message = "Experience years is required")
    @Min(value = 0, message = "Experience years must be 0 or more")
    @Max(value = 50, message = "Experience years must not exceed 50")
    private Integer experienceYears;

    @NotNull(message = "Payment per hour is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Payment per hour must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Payment per hour must be a valid amount")
    private BigDecimal paymentPerHour;
}
