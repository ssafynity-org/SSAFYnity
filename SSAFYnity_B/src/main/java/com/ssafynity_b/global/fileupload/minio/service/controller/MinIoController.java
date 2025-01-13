package com.ssafynity_b.global.fileupload.minio.service.controller;

import com.ssafynity_b.global.fileupload.minio.service.MinIoService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class MinIoController {
    private final MinIoService minIoService;

    @GetMapping
    public ResponseEntity<?> getFile(@AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        String base64Image = minIoService.getFileToMinIO(userDetails);
        if (base64Image == null) {
            return ResponseEntity.internalServerError().body("Failed to fetch image");
        }
        return ResponseEntity.ok().body(base64Image);
    }

}
