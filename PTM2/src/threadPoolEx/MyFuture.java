package threadPoolEx;

public class MyFuture<V> {
	
	V v;
	
	public synchronized void set(V v) {
		this.v = v;
		notifyAll();
	}
	
	public V get() {
		if(v == null) {
			synchronized (this) {
				if(v == null)
					try {
						wait();
					} catch (InterruptedException e) {}
			}
		}
		return v;
	}
	
	
}
