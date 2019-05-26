
import java.util.Random;

public class StreamTest {

	public static void main(String[] args) {
		test1();
	}
	
	public static void test1() {
		Stream<Integer> s = new Stream<>();
		new Thread(() -> {
			for (int i = 0; i < 20; i++) {
				s.add(new Random().nextInt(201) - 100);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
			s.close();     // adding end to the end of the queue
		}).start();
		
		s.filter(x -> x > 0).filter(x -> x % 2 == 0)
		.map(x -> "value: " + x)
		.forEach(System.out::println);
		
		System.out.println("main is dead");
	}

	
}
