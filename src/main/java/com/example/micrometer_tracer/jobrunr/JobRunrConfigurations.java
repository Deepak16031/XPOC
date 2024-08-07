package com.example.micrometer_tracer.jobrunr;

import com.mongodb.client.MongoClient;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.storage.nosql.documentdb.AmazonDocumentDBStorageProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobRunrConfigurations {

  @Bean
  StorageProvider storageProvider(MongoClient mongoClient, JobMapper jobMapper) {
    AmazonDocumentDBStorageProvider documentDBStorageProvider =
        new AmazonDocumentDBStorageProvider(mongoClient, "jobrunr");
    documentDBStorageProvider.setJobMapper(jobMapper);
    return documentDBStorageProvider;
  }
}
