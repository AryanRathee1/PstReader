package com.project.pstReader.Service;

import com.project.pstReader.Model.Entity.*;
import com.project.pstReader.Repository.PstAccessTableRepository;
import com.project.pstReader.Repository.PstFileRepository;
import com.project.pstReader.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PstProcessingService {

    private final PstFileRepository pstFileRepository;
    private final PstAccessTableRepository pstAccessTableRepository;
    private final UserRepository userRepository;

    public void extractFromPstFile(MultipartFile file, Principal connectedUser) {

        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var manager = userRepository.findByUserId(user.getManagerId());

        var pstFileVar = updatePstFileTable(user);

//        log.info("userId: {}", user.getUserId());
        updatePstAccessTable(pstFileVar, Optional.of(user));
        updatePstAccessTable(pstFileVar, manager);

        extractDataFromPstFile(file);
    }

    private void extractDataFromPstFile(MultipartFile file) {
        // TODO: Implement logic to extract data from PST file
    }

    private PstFile updatePstFileTable(User user) {
        var pstFileVar = PstFile.builder()
                .uploadedBy(user.getUserId())
                .managerId(user.getManagerId())
                .build();
        pstFileRepository.save(pstFileVar);
        return pstFileVar;
    }

    private void updatePstAccessTable(PstFile pstFileVar, Optional<User> user) {
        if (user.isPresent()) {

            User managedUser = user.get();

            PstAccessTable pstAccessTableVarUser = PstAccessTable.builder()
                    .accessStatus(AccessStatus.PERMANENT)
                    .accessEndTime(null)
                    .accessStartTime(LocalDateTime.now())
                    .userId(managedUser.getUserId())
                    .pstId(pstFileVar.getPstId())
                    .pstFile(pstFileVar)
                    .user(managedUser)
                    .build();

//            log.info("userId: {}", user.get().getUserId());
            pstAccessTableRepository.save(pstAccessTableVarUser);
        }
    }
}
