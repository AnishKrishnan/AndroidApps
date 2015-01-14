import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		VideoCapture cap = new VideoCapture(0);

		

		if (cap.isOpened()) {

			Mat frame = new Mat();

			for (int i = 0; i < 10; i++) {
				cap.retrieve(frame);

				Highgui.imwrite("tmp.jpg", frame);
			}

		}
	}

}
