import java.awt.*;

public class Bomb extends Food{

    public Bomb(int xCoor, int yCoor, int tileSize) {
        super(xCoor, yCoor, tileSize);
        color = Color.BLACK;
        setNutrition(-10);
    }

}
