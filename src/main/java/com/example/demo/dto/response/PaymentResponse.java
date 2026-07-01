package com.example.demo.dto.response;

import com.example.demo.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String transactionReference;
    private PaymentStatus status;
    private String notes;
    private LocalDateTime createdAt;

    // Flattened artist info
    private Long artistId;
    private String artistName;

    // Flattened project info
    private Long projectId;
    private String projectTitle;
}