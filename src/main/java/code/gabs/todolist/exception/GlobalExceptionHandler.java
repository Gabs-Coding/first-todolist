package code.gabs.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsInDatabase.class)
    public ResponseEntity<String> userAlreadyExistsInDatabaseException(UserAlreadyExistsInDatabase userAlreadyExistsInDatabase) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(userAlreadyExistsInDatabase.getMessage());
    }

    @ExceptionHandler(InvalidLocalDateTime.class)
    public ResponseEntity<String> invalidLocalDateTimeException(InvalidLocalDateTime exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidCredentials.class)
    public ResponseEntity<String> invalidCredentialsException(InvalidCredentials exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> userNotFoundException(UserNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
