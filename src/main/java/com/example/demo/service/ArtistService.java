package com.example.demo.service;

import com.example.demo.dto.request.ArtistRequest;
import com.example.demo.dto.response.ArtistResponse;
import com.example.demo.entity.Artist;
import com.example.demo.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    // CREATE
    public ArtistResponse createArtist(ArtistRequest request) {
        Artist artist = Artist.builder()
                .name(request.getName())
                .gender(request.getGender())
                .phone(request.getPhone())
                .languageSpecialization(request.getLanguageSpecialization())
                .experienceYears(request.getExperienceYears())
                .paymentPerHour(request.getPaymentPerHour())
                .build();

        Artist saved = artistRepository.save(artist);
        return mapToResponse(saved);
    }

    // GET ALL
    public List<ArtistResponse> getAllArtists() {
        return artistRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public ArtistResponse getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));
        return mapToResponse(artist);
    }

    // UPDATE
    public ArtistResponse updateArtist(Long id, ArtistRequest request) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));

        artist.setName(request.getName());
        artist.setGender(request.getGender());
        artist.setPhone(request.getPhone());
        artist.setLanguageSpecialization(request.getLanguageSpecialization());
        artist.setExperienceYears(request.getExperienceYears());
        artist.setPaymentPerHour(request.getPaymentPerHour());

        Artist updated = artistRepository.save(artist);
        return mapToResponse(updated);
    }

    // DELETE
    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));
        artistRepository.delete(artist);
    }

    // SEARCH BY NAME
    public List<ArtistResponse> searchArtists(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // MAPPER: Entity → Response DTO
    private ArtistResponse mapToResponse(Artist artist) {
        return ArtistResponse.builder()
                .id(artist.getId())
                .name(artist.getName())
                .gender(artist.getGender())
                .phone(artist.getPhone())
                .languageSpecialization(artist.getLanguageSpecialization())
                .experienceYears(artist.getExperienceYears())
                .paymentPerHour(artist.getPaymentPerHour())
                .createdAt(artist.getCreatedAt())
                .build();
    }
}