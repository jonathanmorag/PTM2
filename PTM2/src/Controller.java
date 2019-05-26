import java.util.concurrent.Callable;
// import java.util.function.Consumer;

public class Controller {
	

	public interface Applier<Param,Result>{
		public Result apply(Param p);
	}
	
	public interface Acceptable<Param> {
		public void accept(Param p);
	}
	
	public static class Future<V> {
		
		V v;                             			// Object: wait(), notifyAll()
		Runnable r;
		
		public void set(V v) {
			this.v = v;
			r.run();
		}
		
		public V get() { return v; }
		
		public <Result> Future<Result> thenApply(Applier<V,Result> a) {
			Future<Result> f = new Future<>();
			r = ()->f.set(a.apply(v));
			return f;
		}
		
		public void thenAccept(Acceptable<V> c) {
			r = ()->c.accept(v);	
		}
	}
	
	public <V> V syncExecute(Callable<V> c) throws Exception {
		return c.call();
	}
	
	public <V> Future<V> aSyncExecute(Callable<V> c) throws Exception {
		Future<V> f = new Future<>();
		new Thread(()-> {
			try {
				f.set(c.call());
			} catch (Exception e) {}
		}).start();
		 
		return f;
} 
	
	public static void main(String[] args) throws Exception {
		Controller c = new Controller();
		System.out.println("this is done in parallel");
		Future<Worker> f = c.aSyncExecute(()-> {
			try {
				Thread.sleep(4000);
				return new Worker("Jonathan", 1000000);
			} catch (InterruptedException e) {}
			return null;
		});

		// System.out.println(f.get());
		
		// CompletableFuture 
		// We want to get Worker's salary, multiple it by 2, and print to the screen.
		
		 f.thenApply(Worker::getSalary)                
			.thenApply(d -> d * 2)                                     
			.thenApply(d -> {
				return new StringBuilder().append("The Salary Is: " + d).toString();
		   }).thenAccept(System.out::println);
		 
	}
}