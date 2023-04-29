package docscanner;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import static java.io.File.separator;

public class DocumentScanner {

    public static final String RESOURCES_PATH = String.format("src%smain%sresources%s",
            separator, separator, separator);

    static {
        OpenCV.loadLocally();
    }

    public static void main(String[] args) {
        String imgName = "chall3.jpg";
        
        // get canny
        Mat src = ImageHandler.loadImage(imgName);
        Mat canny = ImageHandler.cannyEdgeProcess(src);
        
        // get document's contour
        var bigCnts = ImageHandler.largestContours(canny);
        var docCnt = ImageHandler.documentContour(bigCnts);

        if(docCnt == null) {
            return;
        }
        
        // draw a border along the doc's countour
         ImageHandler.drawBorder(src, docCnt);
         ImageHandler.saveImage(src, "contour-selected.jpg");
        
        // transform perspective -> get bird's eye view
        Mat transformed = ImageHandler.transformRectangle(src, docCnt);
        
        Mat tfGreyScale = ImageHandler.toGreyScale(transformed);
        Mat tresholdMatrix = ImageHandler.blackAndWhiteFeel(tfGreyScale);
        
        ImageHandler.saveImage(tresholdMatrix, "product.jpg");
    }
}

/* TODO:
-   fix edge detection (precisely: top-right and bottom-left)
 */
