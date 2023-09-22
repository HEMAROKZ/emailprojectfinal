package bdaytemplate.customeexception;

public class EmployeeIdNotFoundException extends RuntimeException{

    public EmployeeIdNotFoundException(String message) {
        super(message);
    }
//throwable is super class of all exceptions
    public EmployeeIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
