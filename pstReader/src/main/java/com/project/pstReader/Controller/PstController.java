package com.project.pstReader.Controller;

import com.project.pstReader.Model.PstRequest.PstProcessRequest;
import com.project.pstReader.Service.PstProcessingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/pst")
@RequiredArgsConstructor
public class PstController {

    private static final Logger log = LoggerFactory.getLogger(PstController.class);
    private final PstProcessingService pstProcessingService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPstFile(
            @ModelAttribute PstProcessRequest request,
            Principal connectedUser
    ) throws IOException {
        log.info("Is PST file NULL ?: {}",request.getPstFile() == null);
        if (request.getPstFile() == null) {
            return ResponseEntity.badRequest().body("PST file is required");
        }

        MultipartFile file = request.getPstFile();
        File pstFile = convertMultipartFileToFile(file);

        // Check if the file was created and exists
        if (!pstFile.exists()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File could not be saved or found.");
        }

        log.info("File saved at: {}", pstFile.getAbsolutePath());

        pstProcessingService.extractFromPstFile(pstFile,connectedUser);
        return ResponseEntity.ok("PST file uploaded successfully");
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = File.createTempFile("uploaded_", file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }

}
