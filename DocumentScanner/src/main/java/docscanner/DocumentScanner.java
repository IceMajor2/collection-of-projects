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
        String image = "receipt.jpg"; //scanner.nextLine();

        Mat imgMatrix = ImageHandler.loadImage(image);
        Mat cannied = ImageHandler.cannyEdgeProcess(imgMatrix);
        ImageHandler.saveImage(cannied, image);
    }
}
