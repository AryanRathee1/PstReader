package com.project.pstReader.Model.AdminRequest;

import com.project.pstReader.Model.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRegisterRequest {

    private Integer userId;
    private String username;
    private String password;
    private Integer managerId;
    private Role role = Role.ADMIN;
}
