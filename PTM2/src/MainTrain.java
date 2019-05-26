//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;

public class MainTrain {

	public static void main(String[] args) {
		MyActiveObject o = new MyActiveObject();
		for (int i = 0; i < 5; i++)
			o.generateMaze();
		o.stop();

	}
}
