package au.edu.uq.itee.comp3506.assn2.tests;

import java.time.LocalDateTime;
import java.util.List;

import au.edu.uq.itee.comp3506.assn2.api.TestAPI;
import au.edu.uq.itee.comp3506.assn2.entities.CallRecord;

/**
 * Hook class used by automated testing tool.
 * The testing tool will instantiate an object of this class to test the functionality of your assignment.
 * You must implement the method and constructor stubs below so that they call the necessary code in your application.
 * 
 * @author 
 */
public final class AutoTester implements TestAPI {
	// TODO Provide any data members required for the methods below to work correctly with your application.

	public AutoTester() {
		// TODO Create and initialise any objects required by the methods below.
	}
	
	@Override
	public List<Long> called(long dialler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> called(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> callers(long receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> callers(long receiver, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> findConnectionFault(long dialler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> findConnectionFault(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> findReceivingFault(long reciever) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> findReceivingFault(long reciever, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
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
	
	public static void main(String[] args) {
		AutoTester test = new AutoTester();
		
		System.out.println("AutoTester Stub");
	}
}