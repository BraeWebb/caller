package au.edu.uq.itee.comp3506.assn2.tests;

import org.junit.Test;

public class EfficiencyTest {
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

    @Test
    public void testConstruction() {
        startTimer();
        AutoTester tester = new AutoTester("data/call-records.txt", "data/switches.txt");
        stopTimer("Construction");
    }
}
