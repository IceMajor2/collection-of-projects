package docscanner;

import java.io.File;
import java.util.Scanner;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static java.io.File.separator;
import org.opencv.core.Size;

public class DocumentScanner {

    public static final String RESOURCES_PATH = String.format("src%smain%sresources%s",
            separator, separator, separator);

    static {
        OpenCV.loadLocally();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String image = "receipt.jpg"; //scanner.nextLine();

        Mat imgMatrix = loadImage(image);
        Mat greyscaled = toGreyScale(imgMatrix);
        Mat denoised = removeNoise(greyscaled);
        Mat cannied = cannyEdge(denoised);
        saveImage(cannied, image);
    }
    
    public static Mat removeNoise(Mat img) {
        Mat blurred = new Mat(img.rows(), img.cols(), img.type());
        Imgproc.GaussianBlur(img, blurred, new Size(5, 5), 0);
        return blurred;
    }

    public static Mat toGreyScale(Mat img) {
        Mat greyscaled = new Mat();
        Imgproc.cvtColor(img, greyscaled, Imgproc.COLOR_RGB2GRAY);
        return greyscaled;
    }
    
    public static Mat cannyEdge(Mat img) {
        Mat canny = new Mat(img.rows(), img.cols(), img.type());
        Imgproc.Canny(img, canny, 75, 200);
        return canny;
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
