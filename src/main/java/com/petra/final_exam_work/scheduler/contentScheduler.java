package com.petra.final_exam_work.scheduler;

import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

public class contentScheduler {

    private final PhotoAlbumRepository photoAlbumRepository;

    public contentScheduler(PhotoAlbumRepository photoAlbumRepository) {
        this.photoAlbumRepository = photoAlbumRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void publishScheduledContent() {

        Instant now = Instant.now();

        List<PhotoAlbum> scheduledAlbums =
                photoAlbumRepository
                        .findByContentStatusAndPublishedAtBefore(
                                ContentStatus.SCHEDULED,
                                now
                        );

        for (PhotoAlbum album : scheduledAlbums) {

            album.setContentStatus(ContentStatus.PUBLISHED);
        }
    }
}
