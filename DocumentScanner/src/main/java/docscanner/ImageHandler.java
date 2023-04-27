package docscanner;

import static docscanner.DocumentScanner.RESOURCES_PATH;
import java.io.File;
import static java.io.File.separator;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageHandler {
    
    private static Mat removeNoise(Mat img) {
        Mat blurred = new Mat(img.rows(), img.cols(), img.type());
        Imgproc.GaussianBlur(img, blurred, new Size(5, 5), 0);
        return blurred;
    }

    private static Mat toGreyScale(Mat img) {
        Mat greyscaled = new Mat();
        Imgproc.cvtColor(img, greyscaled, Imgproc.COLOR_RGB2GRAY);
        return greyscaled;
    }
    
    private static Mat toCannyEdge(Mat img) {
        Mat canny = new Mat(img.rows(), img.cols(), img.type());
        Imgproc.Canny(img, canny, 75, 200);
        return canny;
    }
    
    public static Mat cannyEdgeProcess(Mat img) {
        Mat edges = toGreyScale(img);
        edges = removeNoise(edges);
        return toCannyEdge(edges);
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
    
    public static List<MatOfPoint> contours(Mat cannyEdged) {
        List<MatOfPoint> contours = new ArrayList<>();
        Mat ranking = new Mat();
        Imgproc.findContours(cannyEdged, contours, ranking,
                Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        // sort: biggest area first
        contours.sort((c1, c2) -> {
            return Double.valueOf(Imgproc.contourArea(c2))
                    .compareTo(Imgproc.contourArea(c1));
        });
        return contours.subList(0, 5);
    }
}
