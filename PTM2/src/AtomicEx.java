import java.util.concurrent.locks.ReentrantLock;

// import java.util.concurrent.atomic.AtomicInteger;

public class AtomicEx {

	// static AtomicInteger ai = new AtomicInteger(0);
	static ReentrantLock l = new ReentrantLock();
	
	public static class Count {
		int c;
		public Count(int c) { this.c = c; }
		
		public void update() {
			c++;
		}
		
		public int get() { return c; }
	
	}
	
	public static void main(String[] args) throws Exception {

//		Thread t1 = new Thread(() -> {
//			for (int i = 0; i < 10000000; i++)
//				ai.incrementAndGet();
//		});
//		Thread t2 = new Thread(() -> {
//			for (int i = 0; i < 10000000; i++)
//				ai.incrementAndGet();
//		});
//		t1.start();
//		t2.start();
//		t1.join();
//		t2.join();
//		System.out.println("Value of AtomicInteger is: " + ai.get());
		
		Count c = new Count(0);
		
		Thread t1 = new Thread(()-> {
			l.lock();
			for(int i = 0; i < 10000000; i++)
				c.update();
			l.unlock();
		});
		Thread t2 = new Thread(()-> {
			l.lock();
			for(int i = 0; i < 10000000; i++)
				c.update();
			l.unlock();
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println("Value of Integer is: " + c.get());
	}
}
