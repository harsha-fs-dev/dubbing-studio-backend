package com.example.demo.service;

import com.example.demo.dto.response.DashboardResponse;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.ProjectStatus;
import com.example.demo.enums.SessionStatus;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ArtistRepository artistRepository;
    private final ProjectRepository projectRepository;
    private final SessionRepository sessionRepository;
    private final PaymentRepository paymentRepository;

    public DashboardResponse getDashboardStats() {
        return DashboardResponse.builder()
                .totalArtists(artistRepository.count())
                .totalProjects(projectRepository.count())
                .completedProjects(projectRepository.countByStatus(ProjectStatus.COMPLETED))
                .totalSessions(sessionRepository.count())
                .completedSessions(sessionRepository.countByStatus(SessionStatus.COMPLETED))
                .totalPayments(paymentRepository.count())
                .totalRevenue(paymentRepository.sumAllAmounts())
                .pendingPayments(paymentRepository.countByStatus(PaymentStatus.PENDING))
                .build();
    }
}