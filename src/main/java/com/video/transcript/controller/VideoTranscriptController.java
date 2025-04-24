package com.video.transcript.controller;

import com.video.transcript.model.TranscriptRequest;
import com.video.transcript.model.TranscriptResponse;
import com.video.transcript.service.VideoTranscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/transcript")
public class VideoTranscriptController {

    private final VideoTranscriptService videoTranscriptService;

    @Autowired
    public VideoTranscriptController(VideoTranscriptService videoTranscriptService) {
        this.videoTranscriptService = videoTranscriptService;
    }

    @PostMapping("/enriched")
    public ResponseEntity<TranscriptResponse> createEnrichedTranscript(@RequestBody TranscriptRequest transcriptRequest) {
        TranscriptResponse transcriptResponse = videoTranscriptService.generateEnrichedTranscript(transcriptRequest);
        return new ResponseEntity<>(transcriptResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<String> cleanUpTranscript() throws IOException, InterruptedException {
        videoTranscriptService.deleteTranscript();
        return new ResponseEntity<>("Temp directory cleaned up", HttpStatus.OK);
    }
}
