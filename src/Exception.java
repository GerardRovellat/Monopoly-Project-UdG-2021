import java.util.ArrayList;
import java.util.HashMap;

public class Exception {

    private HashMap<Integer,String> error_messages = new HashMap<Integer,String>();

    public Exception () {
        error_messages.put(0,"ERROR 1");
        error_messages.put(1,"ERROR 1");
        error_messages.put(2,"ERROR 1");
        error_messages.put(3,"ERROR 1");
        error_messages.put(4,"ERROR 1");
        error_messages.put(5,"ERROR 1");
    }
    public void throwException(int exception) {
        System.out.println("Exception trowed:");
        System.out.println("Reference: " + exception);
    }

}
