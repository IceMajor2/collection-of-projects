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
        //Scanner scanner = new Scanner(System.in);
        String image = "receipt2.png"; //scanner.nextLine();

        Mat imgMatrix = ImageHandler.loadImage(image);
        Mat cannied = ImageHandler.cannyEdgeProcess(imgMatrix);
        var largestContours = ImageHandler.largestContours(cannied);
        var documentCnt = ImageHandler.documentContour(largestContours);
        ImageHandler.drawBorder(imgMatrix, documentCnt);
        ImageHandler.saveImage(imgMatrix, image);
    }
}
