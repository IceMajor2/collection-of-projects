package docscanner;

import static docscanner.DocumentScanner.RESOURCES_PATH;
import java.io.File;
import static java.io.File.separator;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
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
    
    public static List<MatOfPoint> largestContours(Mat img) {
        List<MatOfPoint> contours = new ArrayList<>();
        Mat ranking = new Mat();
        Imgproc.findContours(img, contours, ranking,
                Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        // sort: biggest area first
        contours.sort((c1, c2) -> {
            return Double.valueOf(Imgproc.contourArea(c2))
                    .compareTo(Imgproc.contourArea(c1));
        });
        return contours.subList(0, 5);
    }
    
    public static MatOfPoint documentContour(List<MatOfPoint> contours) {
        MatOfPoint2f documentCnt = null;
        
        for(var cont : contours) {
            // getting MOP2f because the latter 'arcLength' method works only
            // with 2f type
            MatOfPoint2f cont2f = new MatOfPoint2f();
            cont.convertTo(cont2f, CvType.CV_32F);
            
            // approximate contour
            double perimeter = Imgproc.arcLength(cont2f, true);
            MatOfPoint2f approxCnt = new MatOfPoint2f();
            Imgproc.approxPolyDP(cont2f, approxCnt, 0.02 * perimeter, true);
            
            if(approxCnt.toList().size() == 4) {
                documentCnt = approxCnt;
                break;
            }
        }
        // lastly convert MOP2f back to MOP
        MatOfPoint docMOP = new MatOfPoint();
        try {
            documentCnt.convertTo(docMOP, CvType.CV_32S);
        } catch(NullPointerException e) {
            System.out.println("ERROR: Did not detect document on the picture.");
        }
        return docMOP;
    }
    
    public static void drawBorder(Mat img, MatOfPoint contour) {
       // Mat drawnPic = new Mat();
       // img.copyTo(drawnPic);
        
        Imgproc.drawContours(img, List.of(contour),
                -1, new Scalar(0, 255, 0), 2);
    }
}
