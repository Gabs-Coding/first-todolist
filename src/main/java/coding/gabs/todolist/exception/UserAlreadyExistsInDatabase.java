package coding.gabs.todolist.exception;

public class UserAlreadyExistsInDatabase extends RuntimeException {
  public UserAlreadyExistsInDatabase(String message) {
    super(message);
  }
}
