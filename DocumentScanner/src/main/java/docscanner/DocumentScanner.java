package docscanner;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import static java.io.File.separator;
import java.util.List;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

public class DocumentScanner {

    public static final String RESOURCES_PATH = String.format("src%smain%sresources%s",
            separator, separator, separator);

    static {
        OpenCV.loadLocally();
    }

    public static void main(String[] args) {
        
    }
}

/* TODO:
-   work on the "documentContour" alghoritm. It does not detect imperfect
    images. When the contour's is not totally smooth, then most likely
    it won't work. Maybe SMOOTHEN the image/all contours further?
*/
