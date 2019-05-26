// import java.util.ArrayList;
// import java.util.List;

public class Q1 {

//	public static ___ List<_> parallelPredicateMapper(List<T> list, int numOfThreads, ____ f, ____ p) {
//		// implement
//	}
//	
//	// you may add some helpers here (classes/methods)
//	
//	
//	
//	
//		
//	
//	// !----- DO NOT CHANGE FROM HERE ----- !
//	
//	public static void test1() {
//		List<Integer> list = new ArrayList<>();
//		for(int i = 0; i < 50; i++)
//			list.add(i+1);
//		
//		List<String> ret = parallelPredicateMapper(list, 5, x -> "Num is "+x, x->x % 2==0);
//		if(Thread.activeCount() != 6)                                   // 1 Main + 5 Threads
//			System.out.println("Wrong number of open Threads (-10)");  
//		
//		try { Thread.sleep(100); } catch (InterruptedException e) {}
//						
//		boolean[] failed = {false};
//		
//		for(int i = 0; i < ret.size(); i++) {
//			if(!ret.contains("Num is "+(i+1)*2))
//				failed[0] = true;
//		}
//		
//		if(ret.size() != 25) failed[0] = true;
//		
//		if(failed[0])
//			System.out.println("Wrong output from parallelPredicateMapper method (-15)");	
//		
//		if(Thread.activeCount() != 1)              // 1 Main
//			System.out.println("You did not close your Threads correctly (-5)");
//	}
//	
//	public static class A {
//		int x;
//		public A(int x) {
//			this.x = x;
//		}
//		public int getX() {return x;}
//		
//		@Override
//		public boolean equals(Object obj) {
//			A a = (A)obj;
//			return x == a.x;
//		}
//	}
//	
//	public static void test2() {
//		List<Integer> list = new ArrayList<>();
//		for(int i = 0; i < 33; i++)
//			list.add(i+1);
//		
//		List<A> ret = parallelPredicateMapper(list, 3, x -> new A(x), x->x>10);
//		
//		if(Thread.activeCount() != 4)                                 // 1 Main + 3 Threads
//			System.out.println("Wrong number of open Threads (-10)");    
//		
//		try { Thread.sleep(100); } catch (InterruptedException e) {}
//						
//		
//		boolean[] failed = {false};
//		
//		
//		for(int i = 0; i < ret.size(); i++) {
//			if(!ret.contains(new A(i+11)))
//				failed[0] = true;
//		}
//		
//		if(ret.size() != 23) failed[0] = true;
//		
//		if(failed[0])
//			System.out.println("Wrong output from parallelPredicateMapper method (-15)");
//		
//		if(Thread.activeCount() != 1)              // 1 Main
//			System.out.println("You did not close your Threads correctly (-5)");
//		
//	}
//	
//	public static void main(String[] args) {
//		test1();
//		test2();
//		System.out.println("done");
//	}
}
