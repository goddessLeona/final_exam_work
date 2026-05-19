package com.petra.final_exam_work.service.memberAccess;

import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContributorStatus;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MemberAccessService {

    private final PhotoAlbumRepository photoAlbumRepository;

    public MemberAccessService(PhotoAlbumRepository photoAlbumRepository) {
        this.photoAlbumRepository = photoAlbumRepository;
    }

    public void validateMemberAccess(User user) {

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRole().equals("ADMIN"));

        if (isAdmin) {
            return;
        }

        boolean isMember = user.getRoles().stream()
                .anyMatch(role -> role.getRole().equals("MEMBER"));

        if (isMember) {
            return;
        }

        boolean isApporovedContributor =
                user.getContributorStatus() == ContributorStatus.APPROVED;

        boolean hasPubliehedAlbums = photoAlbumRepository.existsByOwnedByUserAndContentStatus(
                user,
                ContentStatus.PUBLISHED
        );

        if (isApporovedContributor && hasPubliehedAlbums) {
            return;
        }

        throw new ApiException("You do not have access to member content",HttpStatus.FORBIDDEN);

    }
}
