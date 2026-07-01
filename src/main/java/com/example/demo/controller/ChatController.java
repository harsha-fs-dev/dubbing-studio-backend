package com.example.demo.controller;


import com.example.demo.dto.request.ChatRequest;
import com.example.demo.dto.response.ChatResponse;
import com.example.demo.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/ask")
    public ChatResponse ask(@RequestBody ChatRequest request) {

        String answer =
                chatService.answerQuestion(request.getQuestion());

        return new ChatResponse(answer);
    }
}