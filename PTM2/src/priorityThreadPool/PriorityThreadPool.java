package priorityThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityThreadPool extends ThreadPoolExecutor {
	
	public PriorityThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			PriorityBlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	private class Helper<V> extends FutureTask<V> implements Comparable<Helper<V>> {
		int priority;
		
		public Helper(Runnable r, V result, int priority) {
			super(r, result);
			this.priority = priority;
		}
		
		public Helper(Callable<V> c, int priority) {
			super(c);
			this.priority = priority;
		}

		@Override
		public int compareTo(Helper<V> h) {
			return Integer.compare(priority, h.priority);
		}
	}
	

	
	public <V> void execute(Runnable r,V result,int priority) {		
		RunnableFuture<V> rf = new Helper<V>(r, result, priority);
		execute(rf);
	}
	
	public <V> Future<V> submit(Callable<V> c, int priority){
		RunnableFuture<V> rf = new Helper<>(c, priority);
		execute(rf);
		return rf;
	}

}
