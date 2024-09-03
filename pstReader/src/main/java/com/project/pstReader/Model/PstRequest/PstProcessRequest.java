package com.project.pstReader.Model.PstRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PstProcessRequest {
    //private String token;
    private MultipartFile pstFile;
//    private File pstFile;

}
