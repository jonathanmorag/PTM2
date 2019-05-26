
public class Foo {

	public static class Helper {

		@Override
		public String toString() {
			return "ID: " + hashCode();
		}
	}

	private volatile Helper helper; // int x = 5, int y = 3, volatile int z = x + y;

	public Helper getHelper() {
		if (helper == null)
			synchronized (this) {
				if (helper == null)
					helper = new Helper();
			}
		return helper;
	}

	public static void main(String[] args) throws Exception {
		Foo foo = new Foo();
		Thread t1 = new Thread(() -> System.out.println(foo.getHelper()));
		Thread t2 = new Thread(() -> System.out.println(foo.getHelper()));
		Thread t3 = new Thread(() -> System.out.println(foo.getHelper()));
		Thread t4 = new Thread(() -> System.out.println(foo.getHelper()));
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t1.join();
		t2.join();
		t3.join();
		t4.join();
	}
}
