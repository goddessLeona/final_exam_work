package com.petra.final_exam_work.controller.admin;

import com.petra.final_exam_work.dto.responseDto.AdminDashboardConsentFormresponse.AdminDashboardConsentFormResponse;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.admin.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //###################### GET Admin dashboard consent form #####################

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardConsentFormResponse> getDashboard (
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok(adminService.getDashboard(userDetails));
    }
}
