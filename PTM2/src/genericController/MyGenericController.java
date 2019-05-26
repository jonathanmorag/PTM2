package genericController;

import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class MyGenericController {
	// DO NOT CHANGE
	public interface Command{
		void doCommand();
		int getPriority();
	}
	//---------------
	
	// you can add code here
	
	HashMap<String, Command> map;
	PriorityBlockingQueue<Command> commands;
	Thread[] threads;
	volatile boolean stop;
	
	public MyGenericController(HashMap<String, Command> map) {
		this.map = map;
		commands = new PriorityBlockingQueue<>(10, (c1, c2)-> Integer.compare(c1.getPriority(),c2.getPriority()));
		stop = false;
	}
	
	public void submit(String command){
		Command c = map.get(command);
		if(c != null)
			commands.put(c);
	}
	
	public void start(int numOfThreads){
		threads = new Thread[numOfThreads];
		for (int i = 0; i < numOfThreads; i++) {
			threads[i] = new Thread(() -> {
				while (!stop) {
					try {
						commands.take().doCommand();
					} catch (InterruptedException e) {}
				}
			});
			threads[i].start();
		}
	}
	
	public void stop(){
		stop = true;
		for(Thread t : threads)
			t.interrupt();
	}
	
	
}
