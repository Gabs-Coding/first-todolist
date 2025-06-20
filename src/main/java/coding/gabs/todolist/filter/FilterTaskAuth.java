package coding.gabs.todolist.filter;

import coding.gabs.todolist.entity.User;
import coding.gabs.todolist.exception.InvalidCredentials;
import coding.gabs.todolist.exception.UserNotAuthenticated;
import coding.gabs.todolist.exception.UserNotFound;
import coding.gabs.todolist.service.UserService;
import coding.gabs.todolist.util.FilterErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    private final UserService userService;

    public FilterTaskAuth(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 1. get username and password
        LoginInfo userCredentials;
        User user;
        try {
            userCredentials = getLoginInfo(request);
            user = userService.validateUserCredentials(userCredentials.getUsername(),
                    userCredentials.getPassword());
        } catch (RuntimeException e) {
            int status = getStatusCodeByException(e);
            FilterErrorResponse.write(
                    response,
                    status,
                    e.getMessage(),
                    request.getRequestURI());
            return;
        }

        request.setAttribute("userId", user.getId());

        // 3. release access
        filterChain.doFilter(request, response);
    }

    private int getStatusCodeByException(RuntimeException e) {
        int statusCode;
        switch (e) {
            case UserNotFound ignored -> statusCode = HttpServletResponse.SC_NOT_FOUND;
            case InvalidCredentials ignored -> statusCode = HttpServletResponse.SC_UNAUTHORIZED;
            case UserNotAuthenticated ignored -> statusCode = HttpServletResponse.SC_UNAUTHORIZED;
            default -> throw new IllegalStateException("Unexpected value: " + e);
        }
        return statusCode;
    }

    public String getMessage(RuntimeException e) {
        String message;
        switch (e) {
            case UserNotFound ignored -> message = "User not found";
            case InvalidCredentials ignored -> message = "User not authorized";
            case UserNotAuthenticated ignored -> message = "User not authenticated";
            default -> message = e.getMessage();
        }
        return message;
    }

    // I'm almost sure that this isn't a secure method. The user password is exposed,
    // and I'm not sure what to do for fix it. I'll search how to deal better with
    // it. TODO: search a batter way to validate the user password.
    private LoginInfo getLoginInfo(HttpServletRequest request) {
        String rawAuthFromRequest = request.getHeader(HttpHeaders.AUTHORIZATION.toLowerCase());
        Optional<String> authFound = Optional.ofNullable(rawAuthFromRequest);

        if (authFound.isEmpty()) {
            return new LoginInfo("foo", "bar");
        }

        String usernameAndPasswordLiterals = rawAuthFromRequest.substring(rawAuthFromRequest.indexOf(" ") + 1);
        byte[] usernameAndPasswordEncoded = Base64.getDecoder().decode(usernameAndPasswordLiterals);
        String authDecoded = new String(usernameAndPasswordEncoded);
        String username = authDecoded.substring(0, authDecoded.indexOf(":"));
        String password = authDecoded.substring(authDecoded.indexOf(":") + 1);
        return new LoginInfo(username, password);
    }

    @Data
    private static class LoginInfo {
        String username;
        String password;

        public LoginInfo(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
