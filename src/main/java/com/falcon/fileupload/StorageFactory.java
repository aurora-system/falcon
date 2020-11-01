package com.falcon.fileupload;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageFactory {

    private final Environment environment;
    private final FirebaseStorageStrategy firebaseStorageStrategy;
    private final FileStorageStrategy fileStorageStrategy;

    public StorageFactory(Environment environment, 
    		FirebaseStorageStrategy firebaseStorageStrategy, 
    		FileStorageStrategy fileStorageStrategy) {
        this.environment = environment;
        this.firebaseStorageStrategy = firebaseStorageStrategy;
        this.fileStorageStrategy = fileStorageStrategy;
    }


    public StorageStrategy createStrategy() {
        String[] activeProfiles = environment.getActiveProfiles();
        log.info("Active profiles '{}'", Arrays.toString(activeProfiles));

        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev")))) {
            return this.fileStorageStrategy;
        } else if (Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("prod")))) {
            return this.firebaseStorageStrategy;
        } else {
            return this.fileStorageStrategy;
        }
    }
}