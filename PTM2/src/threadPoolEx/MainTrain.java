package threadPoolEx;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

public class MainTrain {

	static String test="";
	static ArrayList<Integer> test2=new ArrayList<>();
	volatile static int i=0;
	private static class TestI implements I{
		int p;
		public TestI() {
			p=(new Random()).nextInt(100);
		}

		@Override
		public void run() {
			try {Thread.sleep(100);	} catch (InterruptedException e) {}
			i++;
			test+=i+",";
			test2.add(p);
		}
		
		public int getPriority(){
			return p;
		}
		
	}
	
	public static void test1(){
		D d=new D(new TestI());
		
		long t0=System.currentTimeMillis();
		d.run();
		d.run();
		d.run();
		
		if(System.currentTimeMillis()-t0>150)
			System.out.println("D does not run the task in another thread(-10)");

		while(i<3);

		if(!test.equals("1,2,3,"))
			System.out.println("D does not run the tasks according to a queue(-5)");
		
		d.stop();
		
		try {Thread.sleep(5);} catch (InterruptedException e) {} // give time to the thread of the active object to close
		
		if(java.lang.Thread.activeCount()>1)
			System.out.println("D does not close its thread(s) correctly(-10)");
	}

	
	public static void test2(){
		test2=new ArrayList<>();
		MyActiveObject activeObject=new MyActiveObject();
		
		for(int i=0;i<100;i++)
			activeObject.execute(new TestI());
		
		activeObject.start();
		
		
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		
		activeObject.stop();
		
		try {Thread.sleep(5);} catch (InterruptedException e) {} // give time to the thread of the active object to close
		
		if(java.lang.Thread.activeCount()>1)
			System.out.println("MyActiveObject does not close its thread(s) correctly(-10)");
		
		for(int i=1;i<test2.size();i++){
			if(test2.get(i-1)>test2.get(i)){
				System.out.println("MyActiveObject does not prioritise(-10)");
				break;
			}
		}
	}
	
	public static int queued=0;
	public static void test3(){
		MyThreadPool tp=new MyThreadPool(3);
		Object lock=new Object();
		
		Runnable r=new Runnable() {			
			@Override
			public void run() {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						queued++;
					}
				}
			}
		};
		
		tp.execute(r);
		boolean fail=false;
		if(tp.getActiveThreadsCount()!=1)
			fail=true;
		tp.execute(r);
		if(tp.getActiveThreadsCount()!=2)
			fail=true;
		tp.execute(r);
		if(tp.getActiveThreadsCount()!=3)
			fail=true;
		tp.execute(r);
		if(tp.getActiveThreadsCount()!=3)
			fail=true;
		tp.execute(r);
		if(tp.getActiveThreadsCount()!=3)
			fail=true;
		
		if(fail)
			System.out.println("wrong count of active threads(-10)");
		
		if((tp.pool.get(0).getTasksCount()!=1) || (tp.pool.get(1).getTasksCount()!=1) || (tp.pool.get(2).getTasksCount()!=0))
			System.out.println("wrong allocation of tasks(-10)");
		
		
		synchronized (lock) {
			lock.notifyAll();
		}
		
		try {Thread.sleep(5);} catch (InterruptedException e) {}
		
		tp.stop();
		
		try {Thread.sleep(5);} catch (InterruptedException e) {} // give time to the thread of the active object to close
		
		if(java.lang.Thread.activeCount()>1)
			System.out.println("MyThreadPool does not close its thread(s) correctly(-10)");
		
		if(queued!=2)
			System.out.println("wrong count of queued tasks in the thread pool(-10)");
		
	}
	
	public static void test4(){
		MyThreadPool2 tp=new MyThreadPool2(1);
		long t0=System.currentTimeMillis();
		int a = tp.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(200);
				return 42;
			}
		}).get();
		if(System.currentTimeMillis()-t0<=200)
			System.out.println("wrong implementation of guarded suspension (-10)");
		if(a!=42)
			System.out.println("the future holds a wrong value (-5)");
		tp.stop();
	}
	
	public static void main(String[] args) {		
		test1();
		test2();
		// test3();
		// test4();
		System.out.println("done");
	}

}
