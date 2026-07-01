package com.example.demo.service;

import com.example.demo.dto.request.ProjectRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Project;
import com.example.demo.enums.ProjectStatus;
import com.example.demo.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    // CREATE
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = Project.builder()
                .title(request.getTitle())
                .clientName(request.getClientName())
                .language(request.getLanguage())
                .deadline(request.getDeadline())
                .budget(request.getBudget())
                .status(request.getStatus())
                .build();

        Project saved = projectRepository.save(project);
        return mapToResponse(saved);
    }

    // GET ALL
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return mapToResponse(project);
    }

    // UPDATE
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        project.setTitle(request.getTitle());
        project.setClientName(request.getClientName());
        project.setLanguage(request.getLanguage());
        project.setDeadline(request.getDeadline());
        project.setBudget(request.getBudget());
        project.setStatus(request.getStatus());

        Project updated = projectRepository.save(project);
        return mapToResponse(updated);
    }

    // DELETE
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        projectRepository.delete(project);
    }

    // SEARCH BY TITLE
    public List<ProjectResponse> searchProjects(String title) {
        return projectRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY STATUS
    public List<ProjectResponse> getProjectsByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // MAPPER: Entity → Response DTO
    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .clientName(project.getClientName())
                .language(project.getLanguage())
                .deadline(project.getDeadline())
                .budget(project.getBudget())
                .status(project.getStatus())
                .createdAt(project.getCreatedAt())
                .build();
    }
}