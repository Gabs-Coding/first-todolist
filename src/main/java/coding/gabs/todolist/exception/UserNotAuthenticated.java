package coding.gabs.todolist.exception;

public class UserNotAuthenticated extends RuntimeException {
    public UserNotAuthenticated(String message) {
        super(message);
    }
}
