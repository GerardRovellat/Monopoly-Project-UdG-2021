import javafx.util.Pair;
import java.util.HashMap;

/**
 * @author Marc Got
 * @file
 * @class
 * @brief
 */
public class Exception {

    private HashMap<Integer, Pair<String,String>> error_messages = new HashMap<>();

    public Exception () {
        error_messages.put(0,new Pair<String,String>("ERROR 1","INFO DEL ERROR"));
    }

    public void throwException(int exception) throws java.lang.Exception {
        if (exception < error_messages.size()-1)
            throw new java.lang.Exception("Exception trowed:\n-------Reference: " + exception + "\n-------Type: " + error_messages.get(exception).getKey() + "\n-------Info: " + error_messages.get(exception).getValue());
        else throw new java.lang.Exception("Throw Exception error ( Out of range )");
    }
}