package vn.io.vutiendat3601.beatbuddy.common.dto;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

public record ResponseDto(
    String message, HttpStatus status, int statusCode, ZonedDateTime timestamp) {}
