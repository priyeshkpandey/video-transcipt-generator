package com.video.transcript.main;

import com.video.transcript.repository.TranscriptRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.video.transcript"})
@EntityScan("com.video.transcript.model")
@EnableMongoRepositories(basePackageClasses = TranscriptRepository.class)
public class TranscriptServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(TranscriptServiceRunner.class, args);
    }
}
