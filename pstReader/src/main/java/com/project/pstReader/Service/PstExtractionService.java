package com.project.pstReader.Service;

import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PstExtractionService {

    private static final Logger logger = LoggerFactory.getLogger(PstExtractionService.class);
    private final EmailService emailService;
    private final AttachmentService attachmentService;

    public void extractDataFromPstFile(File file) {
        if (!file.exists() || file.length() == 0) {
            throw new IllegalArgumentException("File is empty or does not exist");
        }

        logger.info("Processing file: {}", file.getName());

        try {
            PSTFile pstFile = new PSTFile(file);
            processFolder(pstFile.getRootFolder());
        } catch (Exception e) {
            logger.error("Error processing PST file: {}", file.getName(), e);
        }
    }

    private void processFolder(PSTFolder folder) throws PSTException, IOException {
        if (folder.getContentCount() > 0) {
            processEmails(folder);
        }

        if (folder.hasSubfolders()) {
            for (PSTFolder childFolder : folder.getSubFolders()) {
                processFolder(childFolder);
            }
        }
    }

    private void processEmails(PSTFolder folder) throws PSTException, IOException {
        PSTMessage email = (PSTMessage) folder.getNextChild();
        while (email != null) {
            emailService.saveEmail(email);
            attachmentService.processAttachments(email);
            email = (PSTMessage) folder.getNextChild();
        }
    }
}
