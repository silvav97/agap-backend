package com.agap.management.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {

    @JsonProperty("user")
    private UserResponseDTO user;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResponseDTO {
        private Integer id;
        private String email;
        private String name;
        @JsonProperty("isActive")
        private boolean isActive;
        private List<String> roles;

    }
}
