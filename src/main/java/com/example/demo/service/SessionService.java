package com.example.demo.service;

import com.example.demo.dto.request.SessionRequest;
import com.example.demo.dto.response.SessionResponse;
import com.example.demo.entity.Artist;
import com.example.demo.entity.Project;
import com.example.demo.entity.Session;
import com.example.demo.enums.SessionStatus;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ProjectRepository projectRepository;
    private final ArtistRepository artistRepository;

    // CREATE
    public SessionResponse createSession(SessionRequest request) {

        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + request.getProjectId()));

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + request.getArtistId()));

        Session session = Session.builder()
                .sessionTitle(request.getSessionTitle())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .studioRoom(request.getStudioRoom())
                .status(request.getStatus())
                .notes(request.getNotes())
                .project(project)
                .artist(artist)
                .build();

        Session saved = sessionRepository.save(session);
        return mapToResponse(saved);
    }

    // GET ALL
    public List<SessionResponse> getAllSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public SessionResponse getSessionById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));
        return mapToResponse(session);
    }

    // UPDATE
    public SessionResponse updateSession(Long id, SessionRequest request) {

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));

        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + request.getProjectId()));

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + request.getArtistId()));

        session.setSessionTitle(request.getSessionTitle());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setStudioRoom(request.getStudioRoom());
        session.setStatus(request.getStatus());
        session.setNotes(request.getNotes());
        session.setProject(project);
        session.setArtist(artist);

        Session updated = sessionRepository.save(session);
        return mapToResponse(updated);
    }

    // DELETE
    public void deleteSession(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));
        sessionRepository.delete(session);
    }

    // SEARCH BY STUDIO ROOM
    public List<SessionResponse> searchSessions(String studioRoom) {
        return sessionRepository.findByStudioRoomContainingIgnoreCase(studioRoom)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY STATUS
    public List<SessionResponse> getSessionsByStatus(SessionStatus status) {
        return sessionRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // MAPPER: Entity → Response DTO
    private SessionResponse mapToResponse(Session session) {
        return SessionResponse.builder()
                .id(session.getId())
                .sessionTitle(session.getSessionTitle())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .studioRoom(session.getStudioRoom())
                .status(session.getStatus())
                .notes(session.getNotes())
                .createdAt(session.getCreatedAt())
                .projectId(session.getProject().getId())
                .projectTitle(session.getProject().getTitle())
                .artistId(session.getArtist().getId())
                .artistName(session.getArtist().getName())
                .build();
    }
}