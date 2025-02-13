package com.project.spboard.test;

import com.project.spboard.core.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/auth")
    public String testPage() {
        return "authenticated";
    }

    @GetMapping("/deny")
    public String denyPage() {
        return "deny";
    }

    @GetMapping("/checkLogin")
    public ResponseEntity<ApiResponse<String>> checkLogin() {
        return ApiResponse.success(null);
    }
}
