package brvnxc.com.git.spring_security_api.exception;

public class LoginFiledException extends RuntimeException {
    public LoginFiledException(String message) {
        super(message);
    }
}
