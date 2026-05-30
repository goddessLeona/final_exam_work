package com.petra.final_exam_work.scheduler;

import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class ArchiveCleanUpScheduler {

    private final PhotoAlbumRepository photoAlbumRepository;

    public ArchiveCleanUpScheduler(PhotoAlbumRepository photoAlbumRepository) {
        this.photoAlbumRepository = photoAlbumRepository;
    }

    //check once an hour
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void cleanUpArchivedAlbums() {

        Instant cutoff = Instant.now().minus(Duration.ofDays(7));

        List<PhotoAlbum> albums =
                photoAlbumRepository.findByContentStatusAndArchivedAtBefore(
                        ContentStatus.ARCHIVED,
                        cutoff
                );

        for (PhotoAlbum album : albums) {

            if (album.getArchivedAt() == null) continue;
            if (album.getContentStatus() != ContentStatus.ARCHIVED) continue;

            album.setContentStatus(ContentStatus.DELETED);

            //log.info("Deleting archived album {}", album.getPublicUuid());
        }
    }

}
