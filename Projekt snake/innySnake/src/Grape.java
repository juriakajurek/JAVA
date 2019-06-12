import java.awt.*;


public class Grape extends Food{

    public Grape(int xCoor, int yCoor, int tileSize) {
        super(xCoor, yCoor, tileSize);
        color = Color.MAGENTA;
        setNutrition(10);
    }

}
