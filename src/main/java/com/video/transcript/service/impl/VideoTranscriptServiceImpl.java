package com.video.transcript.service.impl;

import com.video.transcript.model.Transcript;
import com.video.transcript.model.TranscriptRequest;
import com.video.transcript.model.TranscriptResponse;
import com.video.transcript.repository.TranscriptRepository;
import com.video.transcript.service.VideoTranscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VideoTranscriptServiceImpl implements VideoTranscriptService {

    private static final String USER_DIRECTORY = System.getProperty("user.dir");
    private static final String PYTHON_PATH = USER_DIRECTORY + File.separator + "python_venv" + File.separator + "bin" + File.separator + "python";
    private static final String VIDEO_DIRECTORY =  USER_DIRECTORY + File.separator + "tmp";
    private static final String TRANSCRIPT_FILE_NAME_PREFIX = "transcript-";
    private static final String TRANSCRIPT_FILE_SUFFIX = "-transcript.txt";
    private static final Random random = new Random();

    private final TranscriptRepository transcriptRepository;


    @Autowired
    public VideoTranscriptServiceImpl(TranscriptRepository transcriptRepository) {
        this.transcriptRepository = transcriptRepository;
    }

    @Override
    public TranscriptResponse generateEnrichedTranscript(TranscriptRequest transcriptRequest) throws IOException, InterruptedException {
        Transcript transcript = new Transcript();
        final String videoUrl = transcriptRequest.getVideoUrl();
        final String transcriptFileName = VIDEO_DIRECTORY + File.separator + TRANSCRIPT_FILE_NAME_PREFIX + random.nextInt(10000) + ".mp3";
        transcript.setVideoUrl(videoUrl);
        Transcript savedTranscript = transcriptRepository.save(transcript);

        // Generate audio file for the YouTube video URL
        String[] videoToAudioCommand = {
                "yt-dlp",
                "-x",
                "--audio-format", "mp3",
                "-o", transcriptFileName,
                videoUrl
        };

        ProcessBuilder pb = new ProcessBuilder(videoToAudioCommand);
        pb.inheritIO();
        Process processGenerateAudio = pb.start();
        processGenerateAudio.waitFor(15, TimeUnit.MINUTES);

        // Generate transcript from the audio file
        String[] transcriptFromAudioCommand = {
                PYTHON_PATH,
                USER_DIRECTORY + File.separator + "transcript_generator.py",
                transcriptFileName
        };
        pb = new ProcessBuilder(transcriptFromAudioCommand);
        //pb.inheritIO();
        String verboseOutputFileName = transcriptFileName + ".tmp.txt";
        File verboseOutputFile = new File(verboseOutputFileName);
        verboseOutputFile.createNewFile();
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(verboseOutputFile)
                .redirectError(ProcessBuilder.Redirect.INHERIT);
        Process processGenerateTranscript = pb.start();
        processGenerateTranscript.waitFor(15, TimeUnit.MINUTES);

        String transcriptText = Files.readString(verboseOutputFile.toPath());
        transcriptText = transcriptText.substring(transcriptText.indexOf("Detected language"), transcriptText.indexOf("Saving result in file"));
        TranscriptResponse transcriptResponse = new TranscriptResponse();
        transcriptResponse.setVideoUrl(savedTranscript.getVideoUrl());
        transcriptResponse.setTranscript(transcriptText);
        return transcriptResponse;
    }

    @Override
    public void deleteTranscript() throws IOException, InterruptedException {
        String[] deleteTmpDirectoryCommand = {
                "rm",
                "-v",
                "-f",
                VIDEO_DIRECTORY + File.separator + TRANSCRIPT_FILE_NAME_PREFIX + "*"
        };

        ProcessBuilder pb = new ProcessBuilder(deleteTmpDirectoryCommand);
        pb.inheritIO();
        Process processDeleteTempDirectory = pb.start();
        processDeleteTempDirectory.waitFor(2, TimeUnit.MINUTES);

    }

}
