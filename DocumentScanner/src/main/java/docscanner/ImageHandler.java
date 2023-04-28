package docscanner;

import static docscanner.DocumentScanner.RESOURCES_PATH;
import static java.io.File.separator;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
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

        for (var cont : contours) {
            // getting MOP2f because the latter 'arcLength' method works only
            // with 2f type
            MatOfPoint2f cont2f = new MatOfPoint2f();
            cont.convertTo(cont2f, CvType.CV_32F);

            // approximate contour
            double perimeter = Imgproc.arcLength(cont2f, true);
            MatOfPoint2f approxCnt = new MatOfPoint2f();
            Imgproc.approxPolyDP(cont2f, approxCnt, 0.02 * perimeter, true);

            if (approxCnt.toList().size() == 4) {
                documentCnt = approxCnt;
                break;
            }
        }
        // lastly convert MOP2f back to MOP
        MatOfPoint docMOP = new MatOfPoint();
        try {
            documentCnt.convertTo(docMOP, CvType.CV_32S);
        } catch (NullPointerException e) {
            System.out.println("ERROR: Did not detect document on the picture.");
            docMOP = null;
        }
        return docMOP;
    }

    public static void drawBorder(Mat img, MatOfPoint contour) {
        // Mat drawnPic = new Mat();
        // img.copyTo(drawnPic);

        Imgproc.drawContours(img, List.of(contour),
                -1, new Scalar(0, 255, 0), 2);
    }

    public static MatOfPoint orderPoints(MatOfPoint unorderedContour) {
        // contour needs to have precisely FOUR points
        List<Point> fourPointList = unorderedContour.toList();
        
        List<Point> ptsOrd = new ArrayList<>(List.of(new Point(), new Point(),
                new Point(), new Point()));
        // sorting contour's points by their distance from x, y 0 point
        // biggest distance is first
        fourPointList.sort((p1, p2) -> {
            double xySum01 = p1.x + p1.y;
            double xySum02 = p2.x + p2.y;
            return Double.valueOf(xySum02).compareTo(xySum01);
        });
        ptsOrd.set(0, fourPointList.get(3)); // top-left point (x + y is smallest)
        ptsOrd.set(3, fourPointList.get(0)); // bottom-right point (x + y is largest)

        // same as previously, but now calculating the diff between x and y
        fourPointList.sort((p1, p2) -> {
            double xyDiff01 = p1.x - p1.y;
            double xyDiff02 = p2.x - p2.y;
            return Double.valueOf(xyDiff01).compareTo(xyDiff02);
        });
        ptsOrd.set(1, fourPointList.get(3));
        ptsOrd.set(2, fourPointList.get(0));

        // creating a to-return MatOfPoint
        MatOfPoint dstMat = new MatOfPoint();
        // adding in-order points to matrix
        dstMat.fromList(ptsOrd);
        return dstMat;
    }

    public static void transformRectangle(Mat imgMatrix, MatOfPoint unordered) {
        MatOfPoint ordered = orderPoints(unordered);
        double[][] xyEdges = new double[4][2];

        double outWidth = postTransformWidth(ordered);
        double outHeight = postTransformHeight(ordered);

        //double[] newTLPointPos = {0, 0};
        //double[] newTRPointPos = {outWidth, 0};
        //double[] newBLPointPos = {0, outHeight};
        //double[] newBRPointPos = {outWidth, outHeight};
        
        Mat perspective = Imgproc.getPerspectiveTransform(unordered, ordered);
        Mat warped = new Mat();
        Imgproc.warpPerspective(imgMatrix, warped, perspective,
                new Size(outWidth, outHeight));
    }

    private static double postTransformWidth(MatOfPoint ordered) {
        Point topLeft = ordered.toList().get(0);
        Point topRight = ordered.toList().get(1);
        Point bottomLeft = ordered.toList().get(2);
        Point bottomRight = ordered.toList().get(3);

        double widthTop = Math.sqrt(Math.pow((topRight.x - topLeft.x), 2)
                + Math.pow((topRight.y - topLeft.y), 2)); // Pythagorean theorem
        double widthBottom = Math.sqrt(Math.pow((bottomRight.x - bottomLeft.x), 2)
                + Math.pow((bottomRight.y - bottomLeft.y), 2));

        // width of the transformed image will be
        // the biggest of 2 parallel rectangle's side (perspective-dependent)
        return widthTop > widthBottom ? widthTop : widthBottom;
    }

    private static double postTransformHeight(MatOfPoint ordered) {
        Point topLeft = ordered.toList().get(0);
        Point topRight = ordered.toList().get(1);
        Point bottomLeft = ordered.toList().get(2);
        Point bottomRight = ordered.toList().get(3);

        double heightLeft = Math.sqrt(Math.pow((topLeft.x - bottomLeft.x), 2)
                + Math.pow((topLeft.y - bottomLeft.y), 2));
        double heightRight = Math.sqrt(Math.pow((topRight.x - bottomRight.x), 2)
                + Math.pow((topRight.y - bottomRight.y), 2));

        return heightLeft > heightRight ? heightLeft : heightRight;
    }
}
