package au.edu.uq.itee.comp3506.assn2.tests;

import au.edu.uq.itee.comp3506.assn2.entities.CallRecord;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestTime {
    Runtime run;
    long memStart;
    long timeStart;
    static AutoTester tester;

    public void startTimer() {
        run = Runtime.getRuntime();

        run.gc();
        memStart = run.totalMemory() - run.freeMemory();

        timeStart = System.nanoTime();
    }

    public void stopTimer(String label) {
        long stop = System.nanoTime();

		run.gc();
		long memStop = run.totalMemory() - run.freeMemory();

		System.out.println(label);
		long diff = stop - timeStart;
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
		System.out.println("\n");
    }

//    @BeforeClass
//    public static void makeTester() {
//        tester = new AutoTester("out/call-records.txt");
//    }

    @Test
    public void testConstruction() {
        startTimer();
//        ArrayList<Integer> integers = new ArrayList<>();
//        integers.add(62111);
//        integers.add(35980);
//        integers.add(18261);
//        integers.add(71625);
//        integers.add(15543);
//        integers.add(11038);
//        CallRecord record = new CallRecord(1500457383L, 4553133765L, 62111, 11038, integers, LocalDateTime.parse("2017-09-08T17:56:51.279"));
//        AutoTester tester = new AutoTester("out/call-records.txt");
        stopTimer("Construction");
    }

//    @Test
//    public void testAllCalled() {
//        startTimer();
//        tester.called(1959159656L);
//        stopTimer("Find all called");
//    }
}
