import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<E> {
	
	BlockingQueue<E> q;
	volatile boolean stop;
	static int id;
	Thread t;
	
	public Stream() {
		q = new LinkedBlockingQueue<>();
		stop = false;
	}
	
	public void add(E e) {
		try {
			q.put(e);
		} catch (InterruptedException ie) {}
	}
	
	public Stream<E> filter(Predicate<E> p) {
		Stream<E> s = new Stream<>();
		t = new Thread(() -> {
			while (!stop) {
				try {
					E e = q.take();
					if (p.test(e))
						s.add(e);
				} catch (InterruptedException ie) {}
			}
		}); t.start();

		return s;
	}
	
	public <R> Stream<R> map(Function<E, R> f) {
		Stream<R> s = new Stream<>();
		t = new Thread(() -> {
			while (!stop) {
				try {
					E e = q.take();
					s.add(f.apply(e));
				} catch (InterruptedException ie) {}
			}
		}); t.start();
		return s;
	}
	
	public void forEach(Consumer<E> c) {
		t = new Thread(() -> {
			while (!stop) {
				try {
					E e = q.take();
					c.accept(e);
					
				} catch (InterruptedException ie) {}
			}
		}); t.start();
	}
	
	
	public void close() {
		t.interrupt();
		stop = true;
	}
}
