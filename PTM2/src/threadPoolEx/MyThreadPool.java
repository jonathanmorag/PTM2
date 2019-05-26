package threadPoolEx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MyThreadPool implements Executor {

	int maxThreads;
	public List<MyActiveObject> pool;
	
	public MyThreadPool(int numOfThreads) {
		this.maxThreads = numOfThreads;
		pool = new ArrayList<>();
	}
	
	@Override
	public void execute(Runnable r) {
		if(pool.size() < maxThreads) {
			MyActiveObject o = new MyActiveObject();
			o.execute(()-> r.run());
			o.start();
			pool.add(o);
		}
		else {
			int min = 100000;
			MyActiveObject temp_o = null;
			for(MyActiveObject o : pool) {
				if(o.getTasksCount() < min) {
					min = o.getTasksCount();
					temp_o = o;
				}
			}
			temp_o.execute(()-> r.run());
		}
	}
	
	public int getActiveThreadsCount() {
		return pool.size();
	}
	
	public void stop() {
		pool.forEach(o -> o.stop());
	}

}
