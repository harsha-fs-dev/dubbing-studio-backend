package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "artists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be a valid 10-digit number")
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @NotBlank(message = "Language specialization is required")
    @Size(max = 150, message = "Language specialization must not exceed 150 characters")
    @Column(name = "language_specialization", nullable = false, length = 150)
    private String languageSpecialization;

    @NotNull(message = "Experience years is required")
    @Min(value = 0, message = "Experience years must be 0 or more")
    @Max(value = 50, message = "Experience years must not exceed 50")
    @Column(name = "experience_years", nullable = false)
    private Integer experienceYears;

    @NotNull(message = "Payment per hour is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Payment per hour must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Payment per hour must be a valid amount")
    @Column(name = "payment_per_hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentPerHour;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
