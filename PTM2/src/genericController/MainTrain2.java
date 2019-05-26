package genericController;

import java.util.HashMap;

// import genericController.MyGenericController.Command;
import genericController.ControllerExecutor.Command;

public class MainTrain2 {

	public static StringBuffer sb=new StringBuffer();

	public static class MyCommand implements Command {

		private String text;
		private int p;

		public MyCommand(String text,int p) {
			this.text=text;
			this.p=p;
		}
		
		@Override
		public void doCommand() {
			sb.append(text);
		}

		@Override
		public int getPriority() {
			return p;
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		HashMap<String, Command> map=new HashMap<>();
		map.put("A", new MyCommand("hello ", 1));
		map.put("B", new MyCommand("world", 2));
		
		// MyGenericController mgc=new MyGenericController(map);
		ControllerExecutor mgc=new ControllerExecutor(map);
		
		mgc.submit("B");
		mgc.submit("A");
		
		mgc.start(10);
		if(Thread.activeCount()!=11) // 11 = 10 + main
			System.out.println("wrong number of open threads (-10)");
		
		// in parallel the commands print "hello world" to the String buffer
		Thread.sleep(2000); // plenty of time to finish both tasks

		if(!sb.toString().equals("hello world"))
			System.out.println("wrong implementation of priority or task execution (-20)");
		
		mgc.stop();
		Thread.sleep(100);
		
		if(Thread.activeCount()!=1) // 1 = main
			System.out.println("you didn't close all the threads (-10)");
		
		System.out.println("done");
	}

}
