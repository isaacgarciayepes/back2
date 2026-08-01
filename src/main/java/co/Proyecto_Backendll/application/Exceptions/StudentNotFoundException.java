package co.Proyecto_Backendll.application.Exceptions;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(long id) {
        super("Student not found with id: " + id);
    }

    public StudentNotFoundException(long id, String message) {
        super(message);
    }

    public StudentNotFoundException(long id, String message, Throwable cause) {
        super(message, cause);
    }
}
