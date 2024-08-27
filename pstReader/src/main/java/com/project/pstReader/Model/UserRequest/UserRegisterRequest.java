package com.project.pstReader.Model.UserRequest;

import com.project.pstReader.Model.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    private Integer userId;
    private String username;
    private String password;
    private Integer managerId;
    private Role role = Role.USER;
}
