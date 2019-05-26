package priorityThreadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MainTrain {

	public static void main(String[] args) {

		PriorityBlockingQueue<Runnable> workQueue=new PriorityBlockingQueue<>();
		PriorityThreadPool tp=new PriorityThreadPool(1, 1, 1, TimeUnit.MINUTES, workQueue);
		
		// execute a long task
		tp.execute(()->{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		// execute 50 prioritised runnables, priority is reversed
		for(int i=0;i<50;i++){
			final int j=i;
			tp.execute(()->{}, j, 100-j);
		}
		
		// submit 50 prioritised callables, priority is reversed
		List<Future<Integer>> list=new LinkedList<>(); 
		for(int i=50;i<100;i++){
			final int j=i;
			list.add(tp.submit(()->j, 100-j));
		}
		
		int i=50;
		for(Future<Integer> f : list){
			try {
				if(f.get()!=i)
					System.out.println("wrong implementation (-2)");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			i++;
		}
		
		int stam=0;
		tp.execute(()->System.out.println("done"), stam, 1000); // last task
		tp.shutdown();
		// if you add to line 29: tp.execute(()->System.out.println(j), j, 100-j);
		// and to line 36: list.add(tp.submit(()->{System.out.println(j); return j;}, 100-j));
		// then the
		// expected output:
		// 99
		// 98
		// ...
		// 1
		// 0
		// done
		
	}

}
