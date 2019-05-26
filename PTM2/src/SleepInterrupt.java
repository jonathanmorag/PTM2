
public class SleepInterrupt {

	public static void main(String[] args) {
		Thread t = new Thread(() -> {
			try {
				System.out.println("in run() - sleep for 7 seconds");
				Thread.sleep(7000);
				System.out.println("in run() - woke up");
			} catch (InterruptedException e) {
				System.out.println("in run() - interrupted while sleeping");
			}
		});
		System.out.println("in run() - leaving normally");
		t.start();

		// Be sure that the new thread gets a chance to
		// run for a while.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		System.out.println("in main() - interrupting other thread");
		t.interrupt();
		System.out.println("in main() - leaving");
	}
}
