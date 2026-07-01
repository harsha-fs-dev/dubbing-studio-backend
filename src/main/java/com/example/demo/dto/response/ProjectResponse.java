package com.example.demo.dto.response;

import com.example.demo.enums.ProjectStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {

    private Long id;
    private String title;
    private String clientName;
    private String language;
    private LocalDate deadline;
    private BigDecimal budget;
    private ProjectStatus status;
    private LocalDateTime createdAt;
}