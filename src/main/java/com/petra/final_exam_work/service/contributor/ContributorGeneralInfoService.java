package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.contributor.ContributorAlbumStatsMapper;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorInfo.ContentStatsResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorInfo.ContributorAlbumStatsResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContributorGeneralInfoService {

    private final UserRepository userRepository;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final ContributorAlbumStatsMapper contributorAlbumStatsMapper;

    public ContributorGeneralInfoService(UserRepository userRepository, PhotoAlbumRepository photoAlbumRepository, ContributorAlbumStatsMapper contributorAlbumStatsMapper) {
        this.userRepository = userRepository;
        this.photoAlbumRepository = photoAlbumRepository;
        this.contributorAlbumStatsMapper = contributorAlbumStatsMapper;
    }


    //####################### INFO CONTRIBUTOR ######################
    public ContributorAlbumStatsResponse getInfoContributor(
            CustomUserDetails userDetails
    ) {

        UUID publicUuid = userDetails.getPublicUuid();

        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        List<PhotoAlbum> albums =
                photoAlbumRepository.findAllByOwnedByUser_PublicUuid(user.getPublicUuid());

        Map<ContentType, List<PhotoAlbum>> grouped =
                albums.stream()
                        .collect(Collectors.groupingBy(PhotoAlbum::getContentType));

        List<ContentStatsResponse> stats = grouped.entrySet()
                .stream()
                .map(entry -> {

                    List<PhotoAlbum> items = entry.getValue();

                    ContentStatsResponse response = new ContentStatsResponse();

                    response.setType(entry.getKey());

                    response.setTotal(items.size());

                    response.setPublished((int) items.stream()
                            .filter(a -> a.getContentStatus() == ContentStatus.PUBLISHED)
                            .count());

                    response.setDraft((int) items.stream()
                            .filter(a -> a.getContentStatus() == ContentStatus.DRAFT)
                            .count());

                    response.setArchived((int) items.stream()
                            .filter(a -> a.getContentStatus() == ContentStatus.ARCHIVED)
                            .count());

                    response.setScheduled((int) items.stream()
                            .filter(a -> a.getContentStatus() == ContentStatus.SCHEDULED)
                            .count());

                    return response;
                })
                .toList();

        return contributorAlbumStatsMapper.toDto(user,stats);
    }
}
