public class Box {
    public int position;
    public String type; // Start,Field,Bet,Luck,DirectComand,Empty

    public Box(int position,String type) {
        this.position = position;
        this.type = type;
    }

    public int getPosition() {
        return this.position;
    }
    public String getType() { return this.type; }


}
