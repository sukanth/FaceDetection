import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class WebCamExample {
    /** Global variables */

    private static String face_cascade_name = "haarcascade_frontalface_alt.xml";
    private static String eyes_cascade_name = "haarcascade_eye_tree_eyeglasses.xml";

    private static CascadeClassifier face_cascade;
    private static CascadeClassifier eyes_cascade;
    private static String window_name = "Capture - Face detection";

    public void detedctAndDisplay(){
        face_cascade = new CascadeClassifier("C:\\Users\\Admin\\Desktop\\face detection\\FaceDetection\\src\\haarcascade_frontalface_alt.xml"); 
        eyes_cascade = new CascadeClassifier("C:\\Users\\Admin\\Desktop\\face detection\\FaceDetection\\src\\"+eyes_cascade_name);
    }

    public static void detectAndDisplay(Mat frame)
    {
        CascadeClassifier face_cascade= new CascadeClassifier("/Users/sukanthgunda/Documents/opencv-3.2.0/data/lbpcascades/lbpcascade_frontalface.xml");

        Mat frame_gray = new Mat();
        MatOfRect faces = new MatOfRect();

        Rect[] facesArray = faces.toArray();

        // Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.equalizeHist(frame_gray, frame_gray);

        //-- Detect faces


        face_cascade.detectMultiScale(frame_gray,faces );


        for (int i = 0; i < facesArray.length; i++)
        {

            Point center = new Point(facesArray[i].x + facesArray[i].width * 0.5, facesArray[i].y + facesArray[i].height * 0.5);
            
            Core.ellipse(frame, center, new Size(facesArray[i].width * 0.5, facesArray[i].height * 0.5), 0, 0, 360, new Scalar(255, 0, 255), 4, 8, 0);

            Mat faceROI = frame_gray.submat(facesArray[i]);

            MatOfRect eyes = new MatOfRect();

            Rect[] eyesArray = eyes.toArray();

            //-- In each face, detect eyes
            eyes_cascade.detectMultiScale(faceROI, eyes, 1.1, 2, 0,new Size(30, 30), new Size());

            for (int j = 0; j < eyesArray.length; j++)
            {

                Point center1 = new Point(facesArray[i].x + eyesArray[i].x + eyesArray[i].width * 0.5,   facesArray[i].y + eyesArray[i].y + eyesArray[i].height * 0.5);
                int radius = (int) Math.round((eyesArray[i].width + eyesArray[i].height) * 0.25);
                Core.circle(frame, center1, radius, new Scalar(255, 0, 0), 4, 8, 0);

            }
        }
        //-- Show what you got

        Highgui.imwrite(window_name, frame);
    }

    /**
    * @param args
    */
    public static void main(String[] args)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //face_cascade = new CascadeClassifier("C:\\Users\\Admin\\Desktop\\face detection\\FaceDetection\\src\\haarcascade_frontalface_alt.xml");
        CascadeClassifier face_cascade1 = new CascadeClassifier("/Users/sukanthgunda/Documents/opencv-3.2.0/data/lbpcascades/lbpcascade_frontalface.xml"); 
        //eyes_cascade = new CascadeClassifier("C:\\Users\\Admin\\Desktop\\face detection\\FaceDetection\\src\\"+eyes_cascade_name);
        VideoCapture capture;
        Mat frame;
        frame = new Mat();
        capture = new VideoCapture(0);

        if(!capture.isOpened())
        {
            System.out.println("Did not connect to camera.");
        }
        else
        {
            capture.retrieve(frame);

            detectAndDisplay(frame);

            capture.release();
        }
    }

}