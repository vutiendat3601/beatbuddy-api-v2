package vn.io.vutiendat3601.beatbuddy.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Token", description = "Schema holding Token data")
public record TokenDto(
    @Schema(name = "token", description = "Token value") String token,
    @Schema(name = "tokenType", description = "Token type") String tokenType,
    @Schema(name = "expiresIn", description = "Token expiration time in miliseconds")
        long expiresIn) {}
