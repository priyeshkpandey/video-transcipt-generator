package com.video.transcript.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "transcripts")
public class Transcript {
    @Id
    private String id;
    private String videoUrl;
}
