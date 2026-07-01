package com.example.demo.repository;

import com.example.demo.entity.Session;
import com.example.demo.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByStatus(SessionStatus status);

    List<Session> findByStudioRoomContainingIgnoreCase(String studioRoom);

    long countByStatus(SessionStatus status);

    @Query("""
        SELECT s.artist.name
        FROM Session s
        GROUP BY s.artist.name
        ORDER BY COUNT(s.id) DESC
        LIMIT 1
    """)
    String findArtistWithMostSessions();
}