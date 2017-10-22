package au.edu.uq.itee.comp3506.assn2.tests;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.edu.uq.itee.comp3506.assn2.api.TestAPI;
import au.edu.uq.itee.comp3506.assn2.entities.AVLTree;
import au.edu.uq.itee.comp3506.assn2.entities.CallRecord;
import au.edu.uq.itee.comp3506.assn2.entities.LinkedList;
import au.edu.uq.itee.comp3506.assn2.entities.RecordReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Hook class used by automated testing tool.
 * The testing tool will instantiate an object of this class to test the functionality of your assignment.
 * You must implement the method and constructor stubs below so that they call the necessary code in your application.
 * 
 * @author 
 */
public final class AutoTester implements TestAPI {
	// TODO Provide any data members required for the methods below to work correctly with your application.
	private AVLTree<Long, LinkedList> diallers;
	private AVLTree<Long, LinkedList> receivers;

	public AutoTester() {
		List<CallRecord> records = RecordReader.read("data/call-records.txt");

		diallers = new AVLTree<>();
		receivers = new AVLTree<>();

		for (CallRecord record : records) {
			LinkedList<Long> list = diallers.find(record.getDialler());
			if (list == null) {
				list = new LinkedList<>();
				diallers.insert(record.getDialler(), list);
			}
			list.addToEnd(record.getReceiver());

			list = receivers.find(record.getReceiver());
			if (list == null) {
				list = new LinkedList<>();
				receivers.insert(record.getReceiver(), list);
			}
			list.addToEnd(record.getDialler());
		}
	}
	
	@Override
	public List<Long> called(long dialler) {
		LinkedList<Long> result = diallers.find(dialler);
		if (result != null) {
			Long[] list = (Long[]) result.toArray(Long.class);
			return Arrays.asList(list);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Long> called(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		return null;
	}

	@Override
	public List<Long> callers(long receiver) {
		LinkedList<Long> result = receivers.find(receiver);
		if (result != null) {
			Long[] list = result.toArray(Long.class);
			return Arrays.asList(list);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Long> callers(long receiver, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		// AVL TREE THEN FILTER
		return null;
	}

	@Override
	public List<Integer> findConnectionFault(long dialler) {
		// TODO Auto-generated method stub
		// AVL TREE THEN FILTER WITH IS FAULTY
		return null;
	}

	@Override
	public List<Integer> findConnectionFault(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		// AVL TREE THEN FILTER WITH IS FAULTY AND TIME
		return null;
	}

	@Override
	public List<Integer> findReceivingFault(long reciever) {
		// TODO Auto-generated method stub
		// AVL TREE THEN FILTER WITH IS FAULTY
		return null;
	}

	@Override
	public List<Integer> findReceivingFault(long reciever, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		// AVL TREE THEN FILTER WITH IS FAULTY AND TIME
		return null;
	}

	@Override
		public int maxConnections() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int maxConnections(LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int minConnections() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int minConnections(LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CallRecord> callsMade(LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	public static void main(String[] args) {
//		AutoTester test = new AutoTester();
//
//		double total1 = 0L;
//		double total2 = 0L;
//		int count = 0;
//
//		for (Long dialler : test.diallers.getKeys()) {
//			long start = System.currentTimeMillis();
//			System.out.println(test.diallers.find(dialler));
//			total1 += System.currentTimeMillis() - start;
//
//			start = System.currentTimeMillis();
//			System.out.println(test.called(dialler));
//			total2 += System.currentTimeMillis() - start;
//
//			System.out.println();
//			count++;
//		}
//
//		System.out.println(total1/count);
//		System.out.println(total2/count);
//
//		System.out.println("AutoTester Stub");
//	}
	public static void main(String[] args) {

//		AutoTester test = new AutoTester();
//		List<Long> keys = test.diallers.getKeys();
		Runtime run = Runtime.getRuntime();

		run.gc();
		long memStart = run.totalMemory() - run.freeMemory();

		long start = System.nanoTime();

//		for (Long dialler : keys) {
//			test.called(dialler);
//		}
		AutoTester test = new AutoTester();

		long stop = System.nanoTime();

		run.gc();
		long memStop = run.totalMemory() - run.freeMemory();

		long diff = stop - start;
		long secs = diff / 1_000_000_000;
		long millis = (diff / 1_000_000) % 1_000;
		long micros = (diff / 1_000) % 1_000;
		long nanos = diff % 1_000;
		System.out.println("File load time: " +
				String.format("%d_%03d.%03d_%03dms", secs, millis, micros, nanos));

		long memDiff = memStop - memStart;
		long gigs = memDiff / 1_000_000_000;
		long megs = (memDiff / 1_000_000) % 1_000;
		long kilos = (memDiff / 1000) % 1_000;
		long bytes = memDiff % 1_000;
		System.out.println("Memory usage: " +
				String.format("%dG %dM %dK %d", gigs, megs, kilos, bytes));
	}
}