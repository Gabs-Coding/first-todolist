package coding.gabs.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsInDatabase.class)
    public ProblemDetail userAlreadyExistsInDatabaseException(UserAlreadyExistsInDatabase e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage());
    }

    @ExceptionHandler(InvalidLocalDateTime.class)
    public ProblemDetail invalidLocalDateTimeException(InvalidLocalDateTime e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                e.getMessage());
    }

    @ExceptionHandler(InvalidCredentials.class)
    public ProblemDetail invalidCredentialsException(InvalidCredentials e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                e.getMessage());
    }

    @ExceptionHandler(UserNotFound.class)
    public ProblemDetail userNotFoundException(UserNotFound e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage());
    }

    @ExceptionHandler(TaskNotFound.class)
    public ProblemDetail taskNotFoundException(TaskNotFound e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage());
    }

    @ExceptionHandler(UserNotAuthorized.class)
    public ProblemDetail userNotAuthorizedException(UserNotAuthorized e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                e.getMostSpecificCause().getMessage());
    }
}
