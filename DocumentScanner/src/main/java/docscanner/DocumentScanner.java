package docscanner;

import java.io.File;
import java.util.Scanner;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import static java.io.File.separator;

public class DocumentScanner {

    public static final String RESOURCES_PATH = String.format("src%smain%sresources%s",
            separator, separator, separator);

    public static void main(String[] args) {
        OpenCV.loadLocally();
        Scanner scanner = new Scanner(System.in);
        String image = "receipt.jpg"; //scanner.nextLine();
        
        Mat imgMatrix = loadImage(image);
        saveImage(imgMatrix, "receipt.jpg");
    }

    public static Mat loadImage(String imgName) {
        Imgcodecs imageCodecs = new Imgcodecs();
        File file = new File(RESOURCES_PATH + "in" + separator + imgName);
        return imageCodecs.imread(file.getAbsolutePath());
    }

    public static void saveImage(Mat imageMatrix, String imgName) {
        Imgcodecs imgCodecs = new Imgcodecs();
        File file = new File(RESOURCES_PATH + "out" + separator + imgName);
        imgCodecs.imwrite(file.getAbsolutePath(), imageMatrix);
    }
}
