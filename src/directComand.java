public class directComand extends Box{
    private Card function;
    private String command;

    public directComand(int position, String type, String command,Card function){
        super(position,type);
        this.function = function;
        this.command = command;
    }

    public Card getCard() { return this.function; }

    public String getCommand() { return this.command; }
}
