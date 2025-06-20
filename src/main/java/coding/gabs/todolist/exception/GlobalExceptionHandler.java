package coding.gabs.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsInDatabase.class)
    public ProblemDetail userAlreadyExistsInDatabaseException(UserAlreadyExistsInDatabase userAlreadyExistsInDatabase) {
        ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, userAlreadyExistsInDatabase.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, userAlreadyExistsInDatabase.getMessage());
    }

    @ExceptionHandler(InvalidLocalDateTime.class)
    public ProblemDetail invalidLocalDateTimeException(InvalidLocalDateTime exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
    }

    @ExceptionHandler(InvalidCredentials.class)
    public ProblemDetail invalidCredentialsException(InvalidCredentials exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(UserNotFound.class)
    public ProblemDetail userNotFoundException(UserNotFound exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
