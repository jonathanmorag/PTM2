import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyActiveObject implements MazeGenerator {
	
	private BlockingQueue<Runnable> queue;
	private volatile boolean stop = false;
	
	public MyActiveObject() {
		queue  = new LinkedBlockingQueue<>();
		new Thread(()-> {
			while(!stop) {
				try {
					queue.take().run();
				} catch (InterruptedException e) { e.printStackTrace(); }
			}
		}).start();
		
	}
	
	@Override
	public void generateMaze() {
		try {
			queue.put(()-> {
				Random r = new Random();
				int[][]mat = new int[4][4];
				for(int[] arr : mat) {
					for(int a : arr) {
						a = r.nextInt(10);
						System.out.print(a+" ");
					}
					System.out.println();
				}
				System.out.println();
			});
		} catch (InterruptedException e) {}
		
	}
	
	public void stop() {
		try {
			queue.put(()-> { stop = true; });
		} catch (InterruptedException e) {}
	}
		
}
