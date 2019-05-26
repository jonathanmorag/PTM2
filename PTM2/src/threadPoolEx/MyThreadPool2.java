package threadPoolEx;

import java.util.concurrent.Callable;

public class MyThreadPool2 extends MyThreadPool {

	public MyThreadPool2(int numOfThreads) {
		super(numOfThreads);
	}
	
	public <V> MyFuture<V> submit(Callable<V> c) {
		MyFuture<V> f = new MyFuture<>();
		new Thread(()->{
			try {
				f.set(c.call());
			} catch (Exception e) {}
		}).start();
		return f;
	}

}
