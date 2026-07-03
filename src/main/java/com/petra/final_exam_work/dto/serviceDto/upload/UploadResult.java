package com.petra.final_exam_work.dto.serviceDto.upload;

import java.util.ArrayList;
import java.util.List;

public class UploadResult {

    private final List<UploadedPhoto> uploaded = new ArrayList<>();
    private final List<FailedUpload> failed = new ArrayList<>();

    public boolean hasFailure() {
        return !failed.isEmpty();
    }

    public int successCount() {
        return uploaded.size();
    }

    public int failureCount() {
        return failed.size();
    }
}
