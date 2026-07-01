package com.example.demo.dto.response;

import com.example.demo.enums.SessionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionResponse {

    private Long id;
    private String sessionTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String studioRoom;
    private SessionStatus status;
    private String notes;
    private LocalDateTime createdAt;

    // Flattened project info
    private Long projectId;
    private String projectTitle;

    // Flattened artist info
    private Long artistId;
    private String artistName;
}