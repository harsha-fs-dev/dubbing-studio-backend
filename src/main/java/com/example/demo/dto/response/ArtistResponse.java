package com.example.demo.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistResponse {

    private Long id;
    private String name;
    private String gender;
    private String phone;
    private String languageSpecialization;
    private Integer experienceYears;
    private BigDecimal paymentPerHour;
    private LocalDateTime createdAt;
}
