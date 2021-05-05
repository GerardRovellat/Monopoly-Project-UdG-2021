import java.util.ArrayList;

public class Card {
    private String type; //CHARGE,FINE,GET,GIVE,GO,PAY
    private boolean postposable;


    public Card(String type,boolean postposable) {
        this.type = type;
        this.postposable = postposable;
    }

    public boolean isPostposable() {
        return this.postposable;
    }
    public String getType() {
        return this.type;
    }

}
