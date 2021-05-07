import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class Box {
    public int position;
    public String type; // Start,Field,Bet,Luck,DirectComand,Empty
    private String name;

    public Box(int position,String type,String name) {
        this.position = position;
        this.type = type;
        this.name = name;
    }

    public int getPosition() {
        return this.position;
    }
    public String getType() { return this.type; }

    public String toString() {
        if (this.type == "FIELD") System.out.println(this.position + " | " + this.type + " | " + this.name);
        else System.out.println(this.position + " | " + this.name);
        return "\n";
    }


}
