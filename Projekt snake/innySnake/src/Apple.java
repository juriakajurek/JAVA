import java.awt.*;


public class Apple extends Food{

    public Apple(int xCoor, int yCoor, int tileSize) {
        super(xCoor, yCoor, tileSize);
        color = Color.RED;
        setNutrition(1);
    }

}