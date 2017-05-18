import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgproc.Imgproc;
public class test {


    public static void main(String[] args) {
         System.out.println("Hello, OpenCV");

    // Load the native library.
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    new DetectFaceDemo().run();
    }

}

/*
 * Detects faces in an image, draws boxes around them, and writes the results to "faceDetection.png".
 * 
 */

class DetectFaceDemo {
  public void run() {
    System.out.println("\nRunning DetectFaceDemo");

     /*Create a face detector from the cascade file in the resources
     directory.*/
   CascadeClassifier faceDetector = new CascadeClassifier("/Users/sukanthgunda/Documents/opencv-3.2.0/data/lbpcascades/lbpcascade_frontalface.xml");
   // Mat image = Imgcodecs.imread(getClass().getResource("E:/lol.png").getPath());
     Mat image = Imgcodecs.imread("/Users/sukanthgunda/Pictures/test.JPG");
if (faceDetector.empty()){
     System.out.println("faceless");
    }
    if(image.empty()){
     System.out.println("imageless");
    }

    // Detect faces in the image.
    // MatOfRect is a special container class for Rect.
    MatOfRect faceDetections = new MatOfRect();
    faceDetector.detectMultiScale(image, faceDetections);

    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

    // Draw a bounding box around each face.
    for (Rect rect : faceDetections.toArray()) {
        Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
    }

    // Save the visualized detection.
    String filename = "/Users/sukanthgunda/Desktop/faceDetection.png";
    System.out.println(String.format("Writing %s", filename));
    Imgcodecs.imwrite(filename, image);
  }
}