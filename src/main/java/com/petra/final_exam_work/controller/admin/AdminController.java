package com.petra.final_exam_work.controller.admin;

import com.petra.final_exam_work.dto.requestDto.admin.AdminConsentReviewRequest;
import com.petra.final_exam_work.dto.responseDto.admin.AdminConsentFormDataResponse.ConsentFormDataResponse;
import com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse.AdminDashboardConsentFormResponse;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.admin.AdminConsentFormService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminConsentFormService adminConsentFormService;

    public AdminController(AdminConsentFormService adminConsentFormService) {
        this.adminConsentFormService = adminConsentFormService;
    }

    //###################### GET Admin dashboard consent form #####################

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardConsentFormResponse> getDashboard (
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok(adminConsentFormService.getDashboard(userDetails));
    }

    //###################### GET Consent id ################################

    @GetMapping("/consent/{id}")
    public ResponseEntity<ConsentFormDataResponse> getConsentForm(
            @PathVariable ("id") UUID publicUuid
    ){
        return ResponseEntity.ok(adminConsentFormService.getConsentFormDataId(publicUuid));
    }

    //###################### GET Admin-ConsentForm Data #####################

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/consent/{id}/document/{type}")
    public ResponseEntity<Resource> getDocument(
            @PathVariable ("id") UUID publicUuid,
            @PathVariable String type
            ) {

        return adminConsentFormService.getDocumentData(publicUuid, type);
    }

    //##### UPDATE admin Consent form #####

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/consent/{id}/review")
    public ResponseEntity<Void> reviewConsentForm (
            @PathVariable UUID id,
            @Valid
            @RequestBody AdminConsentReviewRequest request
            ) {

        adminConsentFormService.reviewConsentForm(id,request);

        return ResponseEntity.ok().build();
    }
}
