package com.example.demo.service;

import com.example.demo.dto.request.PaymentRequest;
import com.example.demo.dto.response.PaymentResponse;
import com.example.demo.entity.Artist;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Project;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ArtistRepository artistRepository;
    private final ProjectRepository projectRepository;

    // CREATE
    public PaymentResponse createPayment(PaymentRequest request) {

        // Check duplicate transaction reference
        if (paymentRepository.findByTransactionReference(request.getTransactionReference()).isPresent()) {
            throw new RuntimeException("Transaction reference already exists: " + request.getTransactionReference());
        }

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + request.getArtistId()));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + request.getProjectId()));

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .paymentDate(request.getPaymentDate())
                .paymentMethod(request.getPaymentMethod())
                .transactionReference(request.getTransactionReference())
                .status(request.getStatus())
                .notes(request.getNotes())
                .artist(artist)
                .project(project)
                .build();

        Payment saved = paymentRepository.save(payment);
        return mapToResponse(saved);
    }

    // GET ALL
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return mapToResponse(payment);
    }

    // UPDATE
    public PaymentResponse updatePayment(Long id, PaymentRequest request) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        // Allow same reference on update, but block if another record owns it
        boolean referenceConflict = paymentRepository
                .findByTransactionReference(request.getTransactionReference())
                .filter(existing -> !existing.getId().equals(id))
                .isPresent();

        if (referenceConflict) {
            throw new RuntimeException("Transaction reference already used by another payment: "
                    + request.getTransactionReference());
        }

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + request.getArtistId()));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + request.getProjectId()));

        payment.setAmount(request.getAmount());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionReference(request.getTransactionReference());
        payment.setStatus(request.getStatus());
        payment.setNotes(request.getNotes());
        payment.setArtist(artist);
        payment.setProject(project);

        Payment updated = paymentRepository.save(payment);
        return mapToResponse(updated);
    }

    // DELETE
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
    }

    // SEARCH BY STATUS
    public List<PaymentResponse> searchPayments(PaymentStatus status) {
        return paymentRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // MAPPER: Entity → Response DTO
    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .paymentMethod(payment.getPaymentMethod())
                .transactionReference(payment.getTransactionReference())
                .status(payment.getStatus())
                .notes(payment.getNotes())
                .createdAt(payment.getCreatedAt())
                .artistId(payment.getArtist().getId())
                .artistName(payment.getArtist().getName())
                .projectId(payment.getProject().getId())
                .projectTitle(payment.getProject().getTitle())
                .build();
    }
}
