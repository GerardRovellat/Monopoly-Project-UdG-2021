public class Box {
    public int position;
    public String type; // fild,bet,luck,bet,direct_comand

    public Box(int position,String type) {
        this.position = position;
        this.type = type;
    }

    public int getPosition() {
        return this.position;
    }
}
