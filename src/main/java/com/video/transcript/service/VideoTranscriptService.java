package com.video.transcript.service;

import com.video.transcript.model.TranscriptRequest;
import com.video.transcript.model.TranscriptResponse;

import java.io.IOException;

public interface VideoTranscriptService {
    TranscriptResponse generateEnrichedTranscript(TranscriptRequest transcriptRequest) throws IOException, InterruptedException;
    void deleteTranscript() throws IOException, InterruptedException;
}
