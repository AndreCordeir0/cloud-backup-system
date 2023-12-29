package com.cloud.backup.system.scheduler;

import com.cloud.backup.system.dao.impl.UserUploadsDAO;
import com.cloud.backup.system.service.impl.UserUploadsService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class FileScheduler {
    @Inject
    UserUploadsService userUploadsService;
    Logger logger = LoggerFactory.getLogger(FileScheduler.class);

//    @Scheduled(every="1h", delay = 1, delayUnit = TimeUnit.HOURS)
    @Scheduled(every = "20s")
    void deleteFilesMarked() {
        CompletableFuture.runAsync(()->userUploadsService.deleteFilesMarked()).exceptionally((ex)->{
            logger.error(ex.getMessage());
            return null;
        });
    }
}
