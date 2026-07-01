package com.example.demo.controller;

import com.example.demo.dto.request.SessionRequest;
import com.example.demo.dto.response.SessionResponse;
import com.example.demo.enums.SessionStatus;
import com.example.demo.service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    // POST /api/sessions
    @PostMapping
    public ResponseEntity<SessionResponse> createSession(@Valid @RequestBody SessionRequest request) {
        SessionResponse response = sessionService.createSession(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/sessions
    @GetMapping
    public ResponseEntity<List<SessionResponse>> getAllSessions() {
        List<SessionResponse> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    // GET /api/sessions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable Long id) {
        SessionResponse response = sessionService.getSessionById(id);
        return ResponseEntity.ok(response);
    }

    // PUT /api/sessions/{id}
    @PutMapping("/{id}")
    public ResponseEntity<SessionResponse> updateSession(
            @PathVariable Long id,
            @Valid @RequestBody SessionRequest request) {
        SessionResponse response = sessionService.updateSession(id, request);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/sessions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/sessions/search?studioRoom=Studio A
    @GetMapping("/search")
    public ResponseEntity<List<SessionResponse>> searchSessions(@RequestParam String studioRoom) {
        List<SessionResponse> results = sessionService.searchSessions(studioRoom);
        return ResponseEntity.ok(results);
    }

    // GET /api/sessions/status?status=SCHEDULED
    @GetMapping("/status")
    public ResponseEntity<List<SessionResponse>> getSessionsByStatus(@RequestParam SessionStatus status) {
        List<SessionResponse> results = sessionService.getSessionsByStatus(status);
        return ResponseEntity.ok(results);
    }
}