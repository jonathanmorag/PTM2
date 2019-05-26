package test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

public class Q2 {

	public static <T> int parallelCountIf(List<T> list, Predicate<T> p, int numOfThreads) throws InterruptedException {
		
		int t_size = list.size() / numOfThreads;
		int count = 0;
		ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
		for (int i = 0; i < numOfThreads; i++) {
			try {
				count += executor.submit(new SubTask<T>(list.subList(i * t_size, (i + 1) * t_size), p)).get();
			} catch (ExecutionException e) {}
		}
		executor.shutdown();
		return count;
		
	}
	
	public static class SubTask<T> implements Callable<Long> {
		
		List<T> list;
		Predicate<T> p;
		
		public SubTask(List<T> list, Predicate<T> p) {
			this.list = list;
			this.p = p;
		}
		
		@Override
		public Long call() throws Exception {
			return list.stream().filter(p).count();
		}
		
	}
		/************************************************************************/
		
		/* AtomicInteger count = new AtomicInteger(0);
		Thread[] threads = new Thread[numOfThreads];
		int t_size = list.size() / numOfThreads;
		for(int i = 0; i < numOfThreads; i++) {
			List<T> partial = list.subList(i * t_size, (i+1) * t_size);
			threads[i] = new Thread(()-> {
				partial.forEach(x -> {
					if(p.test(x))
						count.incrementAndGet();
				});
			});
			threads[i].start();
		}
		for(Thread t : threads)
			t.join();
	
		return count.get();
		*/
		

	

}
