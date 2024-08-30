package com.project.pstReader.Controller;

import com.project.pstReader.Model.AccessRequest;
import com.project.pstReader.Model.Entity.AccessStatus;
import com.project.pstReader.Service.AccessControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pst/access")
@RequiredArgsConstructor
public class AccessControlController {
    // TODO: Improve the output of the endpoints based on the required type by the user/frontend

    private final AccessControlService accessControlService;

    @PostMapping("/provide")
    public ResponseEntity<?> provideAccess(
            @RequestBody AccessRequest accessRequest
    ) {
        var provideStatus = accessControlService.provideAccess(accessRequest.getUserId(), accessRequest.getPstId());
        if (provideStatus) {
            return ResponseEntity.ok("Access provided successfully");
        }
        return ResponseEntity.badRequest().body("Access Not Provided, Please try again after requesting access appropriately");
    }

    @PostMapping("/revoke")
    public ResponseEntity<?> revokeAccess(
            @RequestBody AccessRequest accessRequest
    ) {
        accessControlService.revokeAccess(accessRequest.getUserId(), accessRequest.getPstId());
        return ResponseEntity.ok("Access revoked successfully");
    }

    @PostMapping("/check")
    public ResponseEntity<Boolean> checkAccess(
            @RequestBody AccessRequest accessRequest
    ) {
        boolean access = accessControlService.checkAccess(accessRequest.getUserId(), accessRequest.getPstId());
        return ResponseEntity.ok(access);
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestAccess(
            @RequestBody AccessRequest accessRequest,
            @RequestParam AccessStatus accessStatus
            ) {
        return accessControlService.requestAccess(accessRequest.getUserId(), accessRequest.getPstId(), accessStatus);
    }

}
