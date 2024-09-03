package com.project.pstReader.Service;

import com.pff.PSTAttachment;
import com.pff.PSTException;
import com.pff.PSTMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);
    private static final String OUTPUT_DIR = "attachments/";

    public void processAttachments(PSTMessage email) {
        int numberOfAttachments = email.getNumberOfAttachments();
        for (int x = 0; x < numberOfAttachments; x++) {
            try {
                PSTAttachment attachment = email.getAttachment(x);
                saveAttachment(attachment);
            } catch (IOException | PSTException e) {
                logger.error("Error processing attachment", e);
            }
        }
    }

    private void saveAttachment(PSTAttachment attachment) throws IOException, PSTException {
        String filename = attachment.getLongFilename();
        if (filename.isEmpty()) {
            filename = attachment.getFilename();
        }

        File outputFile = new File(OUTPUT_DIR + filename);
        outputFile.getParentFile().mkdirs();
        try (InputStream attachmentStream = attachment.getFileInputStream();
             FileOutputStream out = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = attachmentStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        logger.info("Saved attachment: {}", filename);
    }
}