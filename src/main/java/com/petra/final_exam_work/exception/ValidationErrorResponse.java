package com.petra.final_exam_work.exception;

import java.util.Map;

public record ValidationErrorResponse (
        String message,
        int status,
        Map<String, String> errors
) {}
