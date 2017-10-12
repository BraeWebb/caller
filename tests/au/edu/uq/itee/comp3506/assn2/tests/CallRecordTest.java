package au.edu.uq.itee.comp3506.assn2.tests;

import au.edu.uq.itee.comp3506.assn2.entities.CallRecord;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CallRecordTest {

    private CallRecord emptyCallRecord;
    private CallRecord onePathCallRecord;
    private CallRecord multiplePathCallRecord;
    private LocalDateTime time;
    private List<Integer> emptyPath;
    private List<Integer> onePath;
    private List<Integer> multiplePath;

    @Before
    public void setUP() {
        time = LocalDateTime.parse("2017-02-15T10:49:06.193");
        emptyPath = new ArrayList<>();
        onePath = new ArrayList<>();
        onePath.add(30);
        multiplePath = new ArrayList<>();
        multiplePath.add(2);
        multiplePath.add(100);
        multiplePath.add(-34);
        multiplePath.add(9999999);
        multiplePath.add(40);

        emptyCallRecord = new CallRecord(1234567890L, 9876543210L, 1, 10000, emptyPath, time);
        onePathCallRecord = new CallRecord(1234567890L, 9876543210L, 1, 10000, onePath, time);
        multiplePathCallRecord = new CallRecord(1234567890L, 9876543210L, 1, 10000, multiplePath, time);
    }

    @Test
    public void testGetters() {
        assertEquals(emptyCallRecord.getDialler(), 1234567890L);
        assertEquals(emptyCallRecord.getReceiver(), 9876543210L);
        assertEquals(emptyCallRecord.getDiallerSwitch(), 1);
        assertEquals(emptyCallRecord.getReceiverSwitch(), 10000);
        assertEquals(emptyCallRecord.getConnectionPath(), emptyPath);
        assertEquals(emptyCallRecord.getTimeStamp(), time);

        assertEquals(onePathCallRecord.getConnectionPath(), onePath);
        assertEquals(multiplePathCallRecord.getConnectionPath(), multiplePath);
    }

    @Test
    public void testString() {
        assertEquals(emptyCallRecord.toString(), "CallRecord [dialler=1234567890, receiver=9876543210, diallerSwitch=1, receiverSwitch=10000, connectionPath=[], timeStamp=2017-02-15T10:49:06.193]");
        assertEquals(onePathCallRecord.toString(), "CallRecord [dialler=1234567890, receiver=9876543210, diallerSwitch=1, receiverSwitch=10000, connectionPath=[30], timeStamp=2017-02-15T10:49:06.193]");
        assertEquals(multiplePathCallRecord.toString(), "CallRecord [dialler=1234567890, receiver=9876543210, diallerSwitch=1, receiverSwitch=10000, connectionPath=[2, 100, -34, 9999999, 40], timeStamp=2017-02-15T10:49:06.193]");
    }

    @Test
    public void testHasSwitch() {
        assertFalse(emptyCallRecord.hasSwitch(4));
        assertFalse(emptyCallRecord.hasSwitch(-4));
        
        assertTrue(onePathCallRecord.hasSwitch(30));
        assertFalse(onePathCallRecord.hasSwitch(50));

        assertTrue(multiplePathCallRecord.hasSwitch(2));
        assertTrue(multiplePathCallRecord.hasSwitch(100));
        assertTrue(multiplePathCallRecord.hasSwitch(-34));
        assertTrue(multiplePathCallRecord.hasSwitch(9999999));
        assertTrue(multiplePathCallRecord.hasSwitch(40));

        assertFalse(multiplePathCallRecord.hasSwitch(200));
        assertFalse(multiplePathCallRecord.hasSwitch(-23));
        assertFalse(multiplePathCallRecord.hasSwitch(55));
        assertFalse(multiplePathCallRecord.hasSwitch(4));
        assertFalse(multiplePathCallRecord.hasSwitch(5000));
    }
}