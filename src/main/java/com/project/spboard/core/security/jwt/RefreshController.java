package com.project.spboard.core.security.jwt;

import com.project.spboard.core.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final RefreshService refreshService;

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refreshToken(HttpServletRequest request) {

        return refreshService.validateRefreshToken(request);
    }

}
