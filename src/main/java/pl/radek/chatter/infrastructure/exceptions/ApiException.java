package pl.radek.chatter.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class ApiException {
    private String message;
    private HttpStatus status;
    private ZonedDateTime timestamp;
}
