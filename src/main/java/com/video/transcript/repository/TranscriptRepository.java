package com.video.transcript.repository;

import com.video.transcript.model.Transcript;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface TranscriptRepository extends MongoRepository<Transcript, String> {

}
