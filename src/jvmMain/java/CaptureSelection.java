import model.Coordinate;


import java.awt.*;



public class CaptureSelection {






    public Coordinate getCoordinateFromMouseClick() throws AWTException {

        //Initialising Robot class
        Robot t = new Robot();

        //set mouse cursor at particular position
        Point position= MouseInfo.getPointerInfo().getLocation();
        int x = (int)position.getX();
        int y = (int)position.getY();
        System.out.println("The coordinates of the selected area is:(" + x + "," + y +")");

        Coordinate coordinate = new Coordinate(x,y);

        //Create Rectangle class
        Rectangle rectangle = new Rectangle(x-50, y-50, 100,100);


        return coordinate;


    }





}
