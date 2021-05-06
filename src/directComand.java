public class directComand extends Box{
    private Card function;

    public directComand(int position, String type, Card function){
        super(position,type);
        this.function = function;
    }

    public Card getCard() { return this.function; }
}
