import java.awt.*;

public class Pear extends Food{

    public Pear(int xCoor, int yCoor, int tileSize) {
        super(xCoor, yCoor, tileSize);
        color = Color.YELLOW;
        setNutrition(5);
    }

}
