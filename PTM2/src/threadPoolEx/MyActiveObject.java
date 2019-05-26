package threadPoolEx;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class MyActiveObject {
	
	BlockingQueue<I> q;
	volatile boolean stop;
	Thread t;
	
	public MyActiveObject() {
		q = new PriorityBlockingQueue<>(10, (i1,i2)-> i1.getPriority() - i2.getPriority());
		stop = false;
	}
	
	public void execute(I i) {
		try {
			q.put(i);
		} catch (InterruptedException e) {}
	}
	
	public void start() {
		t = new Thread(()-> {
			while(!stop) {
				try {
					q.take().run();
				} catch (InterruptedException e) {}
			}
		});
		t.start();
	}
	
	public void stop() {
		stop = true;
		t.interrupt();
	}
	
	public int getTasksCount() {
		return q.size();
	}
	
	
}
