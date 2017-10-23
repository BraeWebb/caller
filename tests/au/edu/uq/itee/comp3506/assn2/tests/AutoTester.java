package au.edu.uq.itee.comp3506.assn2.tests;

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import au.edu.uq.itee.comp3506.assn2.api.TestAPI;
import au.edu.uq.itee.comp3506.assn2.collections.*;
import au.edu.uq.itee.comp3506.assn2.collections.LinkedList;
import au.edu.uq.itee.comp3506.assn2.entities.*;
import au.edu.uq.itee.comp3506.assn2.nodes.TreeNode;

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
	private AVLTree<Long, CallRecord> diallers;
	private AVLTree<Long, CallRecord> receivers;
	private AVLTree<LocalDateTime, CallRecord> times;
	private AVLTree<LocalDateTime, Integer> switchTimes;

	public AutoTester() {
		this("data/call-records.txt");
	}

	public AutoTester(String file) {
		LinkedList<CallRecord> records = RecordReader.read(file);
		System.out.println(records.size());

		diallers = new AVLTree<>();
		receivers = new AVLTree<>();
		times = new AVLTree<>();
		switchTimes = new AVLTree<>();

		for (CallRecord record : records) {
			diallers.insert(record.getDialler(), record);
			receivers.insert(record.getReceiver(), record);
			times.insert(record.getTimeStamp(), record);
			for (Integer s : record.getConnectionPath()) {
				switchTimes.insert(record.getTimeStamp(), s);
			}
		}
	}

	private <T> List<T> filter(au.edu.uq.itee.comp3506.assn2.collections.List<CallRecord> records, Filter filter, Retriever retriever, Object... extra) {
		ArrayList<T> list = new ArrayList<>();

		if (records == null) {
			return list;
		}

		for (CallRecord record : records) {
			if (filter.canAdd(record, extra)) {
				list.add((T) retriever.getResult(record));
			}
		}

		return list;
	}

	@Override
	public List<Long> called(long dialler) {
		return filter(diallers.find(dialler), (record, extra) -> true, CallRecord::getReceiver);
	}

	@Override
	public List<Long> called(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		return filter(diallers.find(dialler), (record, extra) -> record.getTimeStamp().compareTo(startTime) >= 0
				&& record.getTimeStamp().compareTo(endTime) <= 0, CallRecord::getReceiver);
	}

	@Override
	public List<Long> callers(long receiver) {
		return filter(receivers.find(receiver), (record, extra) -> true, CallRecord::getDialler);
	}

	@Override
	public List<Long> callers(long receiver, LocalDateTime startTime, LocalDateTime endTime) {
		return filter(receivers.find(receiver), (record, extra) -> record.getTimeStamp().compareTo(startTime) >= 0
				&& record.getTimeStamp().compareTo(endTime) <= 0, CallRecord::getDialler);
	}

	@Override
	public List<Integer> findConnectionFault(long dialler) {
		return filter(diallers.find(dialler), (record, extra) -> record.findFault() != -1, CallRecord::findFault);
	}

	@Override
	public List<Integer> findConnectionFault(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		return filter(diallers.find(dialler), (record, extra) -> record.findFault() != -1
				&& record.getTimeStamp().compareTo(startTime) >= 0
				&& record.getTimeStamp().compareTo(endTime) <= 0, CallRecord::findFault);
	}

	@Override
	public List<Integer> findReceivingFault(long receiver) {
		return filter(receivers.find(receiver), (record, extra) -> record.findFault() != -1, CallRecord::findFault);
	}

	@Override
	public List<Integer> findReceivingFault(long receiver, LocalDateTime startTime, LocalDateTime endTime) {
		return filter(receivers.find(receiver), (record, extra) -> record.findFault() != -1
				&& record.getTimeStamp().compareTo(startTime) >= 0
				&& record.getTimeStamp().compareTo(endTime) <= 0, CallRecord::findFault);
	}

	private AVLTree<Integer, Integer> getSwitchCount(LocalDateTime startTime, LocalDateTime endTime) {
		au.edu.uq.itee.comp3506.assn2.collections.List<Integer> integers = switchTimes.range(startTime, endTime);
		AVLTree<Integer, Integer> counts = new AVLTree<>();
		for (Integer integer : integers) {
			au.edu.uq.itee.comp3506.assn2.collections.List<Integer> numbers = counts.find(integer);

			if (numbers.isEmpty()) {
				counts.insert(integer, 1);
			} else {
				counts.replace(integer, numbers.getFirst() + 1);
			}
		}
		return counts;
	}

	@Override
	public int maxConnections() {
		return maxConnections(LocalDateTime.MIN, LocalDateTime.MAX);
	}

	@Override
	public int maxConnections(LocalDateTime startTime, LocalDateTime endTime) {
		AVLTree<Integer, Integer> counts = getSwitchCount(startTime, endTime);

		int key = 0;
		int maxValue = 0;
		for (TreeNode<Integer, Integer> entry : counts.getNodes()) {
			if (entry.getValues().getFirst() > maxValue) {
				key = entry.getKey();
				maxValue = entry.getValues().getFirst();
			} else if (entry.getValues().getFirst() == maxValue) {
				if (key > entry.getKey()) {
					key = entry.getKey();
					maxValue = entry.getValues().getFirst();
				}
			}
		}
		return key;
	}

	@Override
	public int minConnections() {
		return minConnections(LocalDateTime.MIN, LocalDateTime.MAX);
	}

	@Override
	public int minConnections(LocalDateTime startTime, LocalDateTime endTime) {
		AVLTree<Integer, Integer> counts = getSwitchCount(startTime, endTime);

		int key = 0;
		int minValue = Integer.MAX_VALUE;
		for (TreeNode<Integer, Integer> entry : counts.getNodes()) {
			if (entry.getValues().getFirst() < minValue) {
				key = entry.getKey();
				minValue = entry.getValues().getFirst();
			} else if (entry.getValues().getFirst() == minValue) {
				if (key > entry.getKey()) {
					key = entry.getKey();
					minValue = entry.getValues().getFirst();
				}
			}
		}
		return key;
	}

	@Override
	public List<CallRecord> callsMade(LocalDateTime startTime, LocalDateTime endTime) {
		return filter(times.range(startTime, endTime), (record, extra) -> true, (record -> record));
	}

	public static void main(String[] args) {
//		AutoTester test = new AutoTester();
//		System.out.println(test.diallers);
	}

//	public static void main(String[] args) {
//		AutoTester test = new AutoTester();
//		long start = System.currentTimeMillis();
//		LocalDateTime startTime = LocalDateTime.parse("2017-09-12T14:45:01.992");
//		LocalDateTime endTime = LocalDateTime.parse("2017-09-17T03:38:21.914");
//		test.maxConnections(startTime, endTime);
//		System.out.println(System.currentTimeMillis() - start);
//	}

//		LocalDateTime startTime = LocalDateTime.parse("2017-09-08T07:19:23.317");
//		LocalDateTime endTime = LocalDateTime.parse("2017-09-19T23:56:32.160");
//		System.out.println(test.callsMade(startTime, endTime));

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
//		System.out.println(test.diallers.find(3497915596L));
//	}
//	public static void main(String[] args) {
//
////		AutoTester test = new AutoTester();
////		List<Long> keys = test.diallers.getKeys();
//		Runtime run = Runtime.getRuntime();
//
//		run.gc();
//		long memStart = run.totalMemory() - run.freeMemory();
//
//		long start = System.nanoTime();
//
////		for (Long dialler : keys) {
////			test.called(dialler);
////		}
//		AutoTester test = new AutoTester();
////		List<CallRecord> records = RecordReader.read("data/call-records.txt");
//
//		long stop = System.nanoTime();
//
//		run.gc();
//		long memStop = run.totalMemory() - run.freeMemory();
//
//		long diff = stop - start;
//		long secs = diff / 1_000_000_000;
//		long millis = (diff / 1_000_000) % 1_000;
//		long micros = (diff / 1_000) % 1_000;
//		long nanos = diff % 1_000;
//		System.out.println("File load time: " +
//				String.format("%d_%03d.%03d_%03dms", secs, millis, micros, nanos));
//
//		long memDiff = memStop - memStart;
//		long gigs = memDiff / 1_000_000_000;
//		long megs = (memDiff / 1_000_000) % 1_000;
//		long kilos = (memDiff / 1000) % 1_000;
//		long bytes = memDiff % 1_000;
//		System.out.println("Memory usage: " +
//				String.format("%dG %dM %dK %d", gigs, megs, kilos, bytes));
//	}
}

interface Filter {
	boolean canAdd(CallRecord record, Object... extra);
}

interface Retriever {
	Object getResult(CallRecord record);
}