public class directCommand extends Box{
    private Card function;

    public directCommand(int position, String type, Card function){
        super(position,type,function.getType());
        this.function = function;
    }

    public Card getCard() { return this.function; }
}
