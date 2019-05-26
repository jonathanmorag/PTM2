package genericController;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class ControllerExecutor {
	// DO NOT CHANGE
	public interface Command{
		void doCommand();
		int getPriority();
	}
	//---------------
	
	// you can add code here
	
	HashMap<String, Command> map;
	PriorityBlockingQueue<Command> commands;
	ExecutorService executor;
	
	public ControllerExecutor(HashMap<String, Command> map) {
		this.map = map;
		commands = new PriorityBlockingQueue<>(10, (c1, c2)-> Integer.compare(c1.getPriority(),c2.getPriority()));
	}
	
	public void submit(String command){
		Command c = map.get(command);
		if(c != null)
			commands.put(c);
	}
	
	public void start(int numOfThreads){
		executor = Executors.newFixedThreadPool(numOfThreads);
		for(int i = 0; i < numOfThreads; i++) {
			executor.execute(()-> {
				try {
					commands.take().doCommand();
				} catch (InterruptedException e) {}
			});
		}
	}
	
	public void stop(){
		executor.shutdownNow();
	}
	
	
}