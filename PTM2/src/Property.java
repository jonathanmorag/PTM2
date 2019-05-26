import java.util.Observable;
import java.util.Observer;

public class Property<V> extends Observable implements Observer {
	
	V v;
	
	public void set(V v) {
		this.v = v;
		setChanged();
		notifyObservers();
	}
	public V get() { return v; }
	
	public void bindTo(Property<V> p) {
		p.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		@SuppressWarnings("unchecked")
		Property<V> other = (Property<V>)o;
		if(this.v != other.v && other.v != null)
			this.set(other.v);
	}
	
	public static void main(String[] args) {
		Property<Integer> p1 = new Property<>();
		Property<Integer> p2 = new Property<>();
		// Property<Integer> p3 = new Property<>();
		
		p2.bindTo(p1);
		p1.bindTo(p2);
		p1.set(5);
		System.out.println(p2.get());       // should be 5
	}
}
