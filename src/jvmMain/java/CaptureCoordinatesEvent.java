import util.MouseEventUtil;
import javax.swing.*;
import java.awt.*;

/**
 * Event that puts up a confirm dialog -
 * and waits for the user to select a region to capture
 */
public class CaptureCoordinatesEvent implements Runnable {
    public CaptureCoordinatesEvent() {}
    @Override
    public void run() {

        int input = JOptionPane.showConfirmDialog(null,
                "Place this at the center of your mini-map and click OK", "Set location", JOptionPane.DEFAULT_OPTION);
        try {
            MainKt.setCaptureRegionCoordinate(MouseEventUtil.getCoordinateFromMouseClick());
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}





