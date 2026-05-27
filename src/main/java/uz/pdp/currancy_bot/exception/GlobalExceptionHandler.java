package uz.pdp.currancy_bot.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import uz.pdp.currancy_bot.model.dto.AppErrorDto;
import uz.pdp.currancy_bot.utils.ErrorConstants;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<AppErrorDto> handleExternalApiError(RestClientException ex, HttpServletRequest request) {
        AppErrorDto error = AppErrorDto.builder().
                path(request.getRequestURI()).
                error(HttpStatus.BAD_GATEWAY.value()).
                message(ex.getMessage()).
                timestamp(LocalDateTime.now().toString()).
                build();
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppErrorDto> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error at {}: ", request.getRequestURI(), ex);
        AppErrorDto error = AppErrorDto.builder().
                path(request.getRequestURI()).
                error(HttpStatus.INTERNAL_SERVER_ERROR.value()).
                message(ex.getMessage()).
                timestamp(LocalDateTime.now().toString()).
                build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
