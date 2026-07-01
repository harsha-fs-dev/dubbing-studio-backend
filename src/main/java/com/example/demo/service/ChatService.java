package com.example.demo.service;

import com.example.demo.entity.Artist;
import com.example.demo.enums.SessionStatus;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ArtistRepository artistRepository;
    private final SessionRepository sessionRepository;

    public String answerQuestion(String question) {

        String q = question.toLowerCase();

        if (q.contains("highest salary")
                || q.contains("highest payment")
                || q.contains("highest paid")) {

            Artist artist = artistRepository
                    .findTopByOrderByPaymentPerHourDesc()
                    .orElse(null);

            if (artist == null) {
                return "No artists found.";
            }

            return artist.getName()
                    + " has the highest payment per hour: ₹"
                    + artist.getPaymentPerHour();
        }

        if (q.contains("how many artists")) {
            return "Total artists: " + artistRepository.count();
        }

        if (q.contains("completed sessions")) {
            return "Completed sessions: "
                    + sessionRepository.countByStatus(SessionStatus.COMPLETED);
        }

        if (q.contains("scheduled sessions")) {
            return "Scheduled sessions: "
                    + sessionRepository.countByStatus(SessionStatus.SCHEDULED);
        }

        if (q.contains("most sessions")) {

            String artist =
                    sessionRepository.findArtistWithMostSessions();

            return artist == null
                    ? "No session data available."
                    : artist + " has the most sessions.";
        }

        return """
                I can answer:
                • Who has highest salary?
                • How many artists are there?
                • How many completed sessions?
                • How many scheduled sessions?
                • Which artist has most sessions?
                """;
    }
}
