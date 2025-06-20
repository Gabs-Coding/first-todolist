package coding.gabs.todolist.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Data
public class FilterErrorResponse {
    public static void write(HttpServletResponse response, int status, String errorMessage, String path) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        ApiError apiError = new ApiError(
                status,
                HttpStatus.valueOf(status).getReasonPhrase(),
                errorMessage,
                path
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(apiError);

        response.getWriter().write(json);
    }
}
