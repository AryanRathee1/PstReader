package com.project.pstReader.Controller;

import com.project.pstReader.Model.PstRequest.PstProcessRequest;
import com.project.pstReader.Service.PstProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/pst")
@RequiredArgsConstructor
public class PstController {

    private final PstProcessingService pstProcessingService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPstFile(
            @ModelAttribute PstProcessRequest request,
            Principal connectedUser
    ) {
        pstProcessingService.extractFromPstFile(request.getPstFile(),connectedUser);
        return ResponseEntity.ok("PST file uploaded successfully");
    }

}
