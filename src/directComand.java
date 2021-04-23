public class directComand extends Box{
    private Card function;

    public directComand(int position,Card function){
        super(position);
        this.function = function;
    }

    public Card getCard() { return this.function; }
}
