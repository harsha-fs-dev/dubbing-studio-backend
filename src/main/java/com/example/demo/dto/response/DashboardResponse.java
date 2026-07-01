package com.example.demo.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private long totalArtists;
    private long totalProjects;
    private long completedProjects;
    private long totalSessions;
    private long completedSessions;
    private long totalPayments;
    private BigDecimal totalRevenue;
    private long pendingPayments;
}