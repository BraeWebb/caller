package au.edu.uq.itee.comp3506.assn2.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import au.edu.uq.itee.comp3506.assn2.api.TestAPI;
import au.edu.uq.itee.comp3506.assn2.collections.*;
import au.edu.uq.itee.comp3506.assn2.collections.LinkedList;
import au.edu.uq.itee.comp3506.assn2.entities.*;
import au.edu.uq.itee.comp3506.assn2.nodes.TreeNode;

/**
 * Hook class used by automated testing tool.
 * The testing tool will instantiate an object of this class to test the functionality of your assignment.
 * You must implement the method and constructor stubs below so that they call the necessary code in your application.
 * 
 * @author Brae Webb <s4435400@student.uq.edu.au>
 */
public final class AutoTester implements TestAPI {
	private AVLTree<Long, CallRecord> diallers;
	private AVLTree<Long, CallRecord> receivers;
	private AVLTree<LocalDateTime, CallRecord> times;

	public AutoTester() {
		this("data/call-records.txt", "data/switches.txt");
	}

	public AutoTester(String file, String switches) {
		LinkedList<CallRecord> records = read(file, readSwitches(switches));

		diallers = new AVLTree<>();
		receivers = new AVLTree<>();
		times = new AVLTree<>();

		for (CallRecord record : records) {
			diallers.insert(record.getDialler(), record);
			receivers.insert(record.getReceiver(), record);
			times.insert(record.getTimeStamp(), record);
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
		au.edu.uq.itee.comp3506.assn2.collections.List<CallRecord> records = times.range(startTime, endTime);

		AVLTree<Integer, Integer> counts = new AVLTree<>();

		for (CallRecord record : records) {
			for (Integer integer : record.getConnectionPath()) {
				au.edu.uq.itee.comp3506.assn2.collections.List<Integer> numbers = counts.find(integer);

				if (numbers.isEmpty()) {
					counts.insert(integer, 1);
				} else {
					counts.replace(integer, numbers.getFirst() + 1);
				}
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

	private static CallRecord readLine(String line, AVLTree<Integer, CallRecord> switchTree) {
		String[] lineArray = line.split("\\s+");

		if (lineArray[0].length() != 10) {
			return null;
		}

		if (lineArray[1].length() != 5) {
			return null;
		}

		List<Integer> switches = new ArrayList<>();

		for (int i = 1; i < lineArray.length; i++) {
			String phoneSwitch = lineArray[i];
			if (phoneSwitch.length() == 5) {
				try {
					Integer switchNumber = Integer.parseInt(phoneSwitch);
					if (switchTree.find(switchNumber).isEmpty()) {
						return null;
					}
					switches.add(switchNumber);
				} catch (NumberFormatException exception) {
					return null;
				}

			} else if (phoneSwitch.length() == 10) {
				break;
			} else {
				return null;
			}
		}

		if (switches.size() != lineArray.length - 3) {
			return null;
		}

		Long caller = Long.parseLong(lineArray[0]);
		Long receiver = Long.parseLong(lineArray[switches.size() + 1]);
		int callerSwitch = Integer.parseInt(lineArray[1]);
		int receiverSwitch = Integer.parseInt(lineArray[lineArray.length - 3]);

		switches.remove(0);
		if (switches.size() <= 0) {
			return null;
		}
		switches.remove(switches.size() - 1);

		if (!switches.isEmpty()) {
			if (callerSwitch != switches.get(0)) {
				return null;
			}
		}

		return new CallRecord(
				caller,
				receiver,
				callerSwitch,
				receiverSwitch,
				switches,
				LocalDateTime.parse(lineArray[lineArray.length - 1])
		);
	}

	private static LinkedList<CallRecord> read(String file, AVLTree<Integer, CallRecord> switches) {


		LinkedList<CallRecord> records = new LinkedList<>();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			while (bufferedReader.ready()) {
				CallRecord record = readLine(bufferedReader.readLine(), switches);
				if (record != null) {
					records.add(record);
				}
			}

			bufferedReader.close();
		} catch (IOException exception) {
			return null;
		}
		return records;
	}

	private static AVLTree<Integer, CallRecord> readSwitches(String file) {
		AVLTree<Integer, CallRecord> tree = new AVLTree<>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			bufferedReader.readLine();
			while (bufferedReader.ready()) {
				tree.insert(Integer.parseInt(bufferedReader.readLine()), null);
			}
			bufferedReader.close();
		} catch (IOException exception) {
			return null;
		}

		return tree;
	}
}

interface Filter {
	boolean canAdd(CallRecord record, Object... extra);
}

interface Retriever {
	Object getResult(CallRecord record);
}