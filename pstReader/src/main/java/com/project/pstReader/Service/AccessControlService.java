package com.project.pstReader.Service;

import com.project.pstReader.Model.Entity.AccessStatus;
import com.project.pstReader.Model.Entity.PstAccessTable;
import com.project.pstReader.Repository.PstAccessTableRepository;
import com.project.pstReader.Repository.PstFileRepository;
import com.project.pstReader.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final PstAccessTableRepository pstAccessTableRepository;
    private final UserRepository userRepository;
    private final PstFileRepository pstFileRepository;

    public boolean checkAccess(Integer userId, Integer pstId) {
        var user = pstAccessTableRepository.findByUserIdAndPstId(userId, pstId);
        if (user.isPresent()) {
            var accessStatus = user.get().getAccessStatus();
            if (accessStatus == AccessStatus.TEMPORARY) {
                return !user.get().getAccessEndTime().isBefore(LocalDateTime.now());
            }
            return accessStatus == AccessStatus.PERMANENT;
        }
        return false;
    }

    public ResponseEntity<?> requestAccess(Integer userId, Integer pstId, AccessStatus accessStatus) {
        var pstAccessTable = PstAccessTable.builder()
                .userId(userId)
                .pstId(pstId)
                .accessStatus(accessStatus) //  REQUESTED_TEMPORARY or REQUESTED_PERMANENT
                .user(userRepository.findByUserId(userId).isPresent()? userRepository.findByUserId(userId).get() : null)
                .pstFile(pstFileRepository.findByPstId(pstId).isPresent()? pstFileRepository.findByPstId(pstId).get() : null)
                .accessStartTime(null)
                .accessEndTime(null)
                .build();
        
        pstAccessTableRepository.save(pstAccessTable);
        
        // TODO: Send the request to the uploader of the PST file, either via email, notification or 
        //  to the requestedAccess tab on the frontend of the user
        
        return ResponseEntity.accepted().build();
    }

    public Boolean revokeAccess(Integer userId, Integer pstId) {
        var pstAccessTable = pstAccessTableRepository.findByUserIdAndPstId(userId, pstId);
        if (pstAccessTable.isPresent()) {
            pstAccessTable.get().setAccessStatus(AccessStatus.REVOKED);
            pstAccessTableRepository.save(pstAccessTable.get());
            return true;
        }

        // TODO: Delete record from PstAccessTable of revoked user?
        // TODO: Send the email or notification to the user that their access has been revoked
        return false;
    }

    public Boolean provideAccess(Integer userId, Integer pstId) {
        var pstAccessTable = pstAccessTableRepository.findByUserIdAndPstId(userId, pstId);
        if (pstAccessTable.isPresent()) {
            var accessStatus = pstAccessTable.get().getAccessStatus();
            if (accessStatus == AccessStatus.REQUESTED_TEMPORARY) {
                pstAccessTable.get().setAccessStatus(AccessStatus.TEMPORARY);
                pstAccessTable.get().setAccessStartTime(LocalDateTime.now());
                pstAccessTable.get().setAccessEndTime(LocalDateTime.now().plusDays(7));
                pstAccessTableRepository.save(pstAccessTable.get());
                return true;
            } else if (accessStatus == AccessStatus.REQUESTED_PERMANENT) {
                pstAccessTable.get().setAccessStatus(AccessStatus.PERMANENT);
                pstAccessTable.get().setAccessStartTime(LocalDateTime.now());
                pstAccessTableRepository.save(pstAccessTable.get());
                return true;
            }

            // TODO: Send the email or notification to the user that their access has been provided
            return false;
        }
        return false;
    }
}
