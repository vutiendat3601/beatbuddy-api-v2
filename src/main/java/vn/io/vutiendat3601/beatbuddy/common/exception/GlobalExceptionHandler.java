package vn.io.vutiendat3601.beatbuddy.common.exception;

import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import vn.io.vutiendat3601.beatbuddy.common.dto.ResponseDto;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ResponseDto> handleHandlerMethodValidationException(
      HandlerMethodValidationException e) {
    final MessageSourceResolvable firstError = e.getAllErrors().get(0);
    final String message = firstError.getDefaultMessage();
    log.error("Validation error: {}", message);
    return ResponseEntity.badRequest()
        .body(
            new ResponseDto(
                message,
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now()));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ResponseDto> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    final String message = "%s is invalid value".formatted(e.getName());
    log.error("Validation error: {}", message);
    return ResponseEntity.badRequest()
        .body(
            new ResponseDto(
                message,
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now()));
  }
}
