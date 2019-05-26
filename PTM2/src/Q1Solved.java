import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Q1Solved {
	
	public static <T,R> List<R> parallelPredicateMapper(List<T> list, int numOfThreads, Function<T,R> f, Predicate<T> p) {
		List<R> ret = new ArrayList<>();
		int t_size = list.size() / numOfThreads;
		ExecutorService e = Executors.newFixedThreadPool(numOfThreads);
		for(int i = 0; i < numOfThreads; i++)
			try {
				ret.addAll(e.submit(new MiniMapper<T,R>(list.subList(i * t_size, (i+1)*t_size), f, p)).get());
			} catch (InterruptedException | ExecutionException e1) {}
		e.shutdown();
		return ret;
	}
	
	public static class MiniMapper<T,R> implements Callable<List<R>> {
		
		List<T> list;
		Function<T, R> f;
		Predicate<T> p;
		
		public MiniMapper(List<T> list, Function<T, R> f, Predicate<T> p) {
			this.list = list;
			this.f = f;
			this.p = p;
		}

		@Override
		public List<R> call() throws Exception {
			return list.stream().filter(p).map(f).collect(Collectors.toList());
		}
		
	}
	
//		Royi's solution:
	
//	public static <T,R> List<R> parallelPredicateMapper(List<T> list, int numOfThreads, Function<T,R> f, Predicate<T> p) {
//		List<R> ret = new ArrayList<>();
//		int t_size = list.size() / numOfThreads;
//		ExecutorService e = Executors.newFixedThreadPool(numOfThreads);
//		for(int i = 0; i < numOfThreads; i++) {
//			List<T> splitted = list.subList(i * t_size, (i+1)*t_size);
//			e.execute(()-> { 
//				synchronized (ret) {
//					splitted.stream().filter(p).map(f).forEach(r -> temp.add(r));					
//				}
//			});		
//		}
//		e.shutdown();
//		return ret;
//	}
	
	public static void test1() {
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < 50; i++)
			list.add(i+1);
		
		List<String> ret = parallelPredicateMapper(list, 5, x -> "Num is "+x, x->x % 2==0);
		if(Thread.activeCount() != 6)                                   // 1 Main + 5 Threads
			System.out.println("Wrong number of open Threads (-10)");  
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
						
		boolean[] failed = {false};
		
		for(int i = 0; i < ret.size(); i++) {
			if(!ret.contains("Num is "+(i+1)*2))
				failed[0] = true;
		}
		
		if(ret.size() != 25) failed[0] = true;
		
		if(failed[0])
			System.out.println("Wrong output from parallelPredicateMapper method (-15)");	
		
		if(Thread.activeCount() != 1)              // 1 Main
			System.out.println("You did not close your Threads correctly (-5)");
	}
	
	public static class A {
		int x;
		public A(int x) {
			this.x = x;
		}
		public int getX() {return x;}
		
		@Override
		public boolean equals(Object obj) {
			A a = (A)obj;
			return x == a.x;
		}
	}
	
	public static void test2() {
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < 33; i++)
			list.add(i+1);
		
		List<A> ret = parallelPredicateMapper(list, 3, x -> new A(x), x->x>10);
		
		if(Thread.activeCount() != 4)                                 // 1 Main + 3 Threads
			System.out.println("Wrong number of open Threads (-10)");    
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
						
		
		boolean[] failed = {false};
		
		
		for(int i = 0; i < ret.size(); i++) {
			if(!ret.contains(new A(i+11)))
				failed[0] = true;
		}
		
		if(ret.size() != 23) failed[0] = true;
		
		if(failed[0])
			System.out.println("Wrong output from parallelPredicateMapper method (-15)");
		
		if(Thread.activeCount() != 1)              // 1 Main
			System.out.println("You did not close your Threads correctly (-5)");
		
	}
	
	public static void main(String[] args) {
		test1();
		test2();
		System.out.println("done");
	}
}
