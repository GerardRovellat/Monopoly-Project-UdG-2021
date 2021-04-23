import java.util.ArrayList;

public class Card {
    private String type;
    private boolean postposable;


    public Card(String type,boolean postposable) {
        this.type = type;
        this.postposable = postposable;
    }

    public boolean isPostposable() {
        return this.postposable;
    }


}
