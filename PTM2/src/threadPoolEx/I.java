package threadPoolEx;

public interface I {
	void run();
	default int getPriority() {
		return 0;
	}
}
