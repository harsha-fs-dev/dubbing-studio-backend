package com.example.demo.controller;

import com.example.demo.dto.request.ArtistRequest;
import com.example.demo.dto.response.ArtistResponse;
import com.example.demo.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    // POST /api/artists
    @PostMapping
    public ResponseEntity<ArtistResponse> createArtist(@Valid @RequestBody ArtistRequest request) {
        ArtistResponse response = artistService.createArtist(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/artists
    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        List<ArtistResponse> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    // GET /api/artists/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable Long id) {
        ArtistResponse response = artistService.getArtistById(id);
        return ResponseEntity.ok(response);
    }

    // PUT /api/artists/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponse> updateArtist(
            @PathVariable Long id,
            @Valid @RequestBody ArtistRequest request) {
        ArtistResponse response = artistService.updateArtist(id, request);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/artists/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/artists/search?name=raj
    @GetMapping("/search")
    public ResponseEntity<List<ArtistResponse>> searchArtists(@RequestParam String name) {
        List<ArtistResponse> results = artistService.searchArtists(name);
        return ResponseEntity.ok(results);
    }
}