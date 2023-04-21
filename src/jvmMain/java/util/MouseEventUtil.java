package util;

import model.Coordinate;
import java.awt.*;

public class MouseEventUtil {

    /**
     * Gets the current location of the mouse and saves it into a Coordinate
     * @return Coordinate
     * @throws AWTException
     */
    public static Coordinate getCoordinateFromMouseClick() throws AWTException {
        //Initialising Robot class
        Robot t = new Robot();
        //set mouse cursor at particular position
        Point position= MouseInfo.getPointerInfo().getLocation();
        int x = (int)position.getX();
        int y = (int)position.getY();
        System.out.println("The coordinates of the selected area is:(" + x + "," + y +")");
        return new Coordinate(x,y);
    }
}
