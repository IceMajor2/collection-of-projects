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
        String imgName = "receipt.jpg";
        
        // get canny
        Mat src = ImageHandler.loadImage(imgName);
        Mat canny = ImageHandler.cannyEdgeProcess(src);
        //ImageHandler.saveImage(canny, "chall3.jpg");
        
        // get document's contour
        var bigCnts = ImageHandler.largestContours(canny);
        var docCnt = ImageHandler.documentContour(bigCnts);
        
        if(docCnt == null) {
            return;
        }
        
        // draw a border along the doc's countour
        // ImageHandler.drawBorder(src, docCnt);
        // ImageHandler.saveImage(src, "contour-selected.jpg");
        
        // transform perspective -> get bird's eye view
        Mat transformed = ImageHandler.transformRectangle(src, docCnt);
        
        Mat tfGreyScale = ImageHandler.toGreyScale(transformed);
        Mat tresholdMatrix = ImageHandler.blackAndWhiteFeel(tfGreyScale);
        
        ImageHandler.saveImage(tresholdMatrix, "product.jpg");
    }
}

/* TODO:
-   work on the "documentContour" alghoritm. It does not detect imperfect
    images. When the contour's is not totally smooth, then most likely
    it won't work. Maybe SMOOTHEN the image/all contours further?
 */
