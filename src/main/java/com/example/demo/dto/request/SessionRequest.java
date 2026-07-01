package com.example.demo.dto.request;

import com.example.demo.enums.SessionStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionRequest {

    @NotBlank(message = "Session title is required")
    @Size(min = 2, max = 150, message = "Session title must be between 2 and 150 characters")
    private String sessionTitle;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotBlank(message = "Studio room is required")
    @Size(max = 100, message = "Studio room must not exceed 100 characters")
    private String studioRoom;

    @NotNull(message = "Status is required")
    private SessionStatus status;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Artist ID is required")
    private Long artistId;
}