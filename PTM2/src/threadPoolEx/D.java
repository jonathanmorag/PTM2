package threadPoolEx;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class D implements I {
	
	I i;
	BlockingQueue<Runnable> q;
	volatile boolean stop;
	
	public D(I i) {
		this.i = i;
		q = new LinkedBlockingQueue<>();
		stop = false;
		new Thread(() -> {
			while (!stop) {
				try {
					q.take().run();
				} catch (InterruptedException e) {}
			}
		}).start();
	}
	
	public void stop() {
		try {
			q.put(()->stop = true);
		} catch (InterruptedException e) {}
	}

	@Override
	public void run() {
		try {
			q.put(()-> i.run());
		} catch (InterruptedException e) {}
	}
	
	
}
