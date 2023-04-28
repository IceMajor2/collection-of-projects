package docscanner;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import static java.io.File.separator;
import org.opencv.core.Point;

public class DocumentScanner {

    public static final String RESOURCES_PATH = String.format("src%smain%sresources%s",
            separator, separator, separator);

    static {
        OpenCV.loadLocally();
    }

    public static void main(String[] args) {
        Point p01 = new Point(50.0, 30.0);
        Point p02 = new Point(200.0, 42.0);
        Point p03 = new Point(60.0, 320.0);
        Point p04 = new Point(220.0, 340.0);
        
    }
}

/* TODO:
-   work on the "documentContour" alghoritm. It does not detect imperfect
    images. When the contour's is not totally smooth, then most likely
    it won't work. Maybe SMOOTHEN the image/all contours further?
*/
