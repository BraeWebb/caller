package au.edu.uq.itee.comp3506.assn2.tests;

import au.edu.uq.itee.comp3506.assn2.entities.CallRecord;
import au.edu.uq.itee.comp3506.assn2.entities.RecordReader;
import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * RecordReaderTest
 * Created 13/10/2017
 *
 * @author Brae Webb
 */
public class RecordReaderTest {
    @Test
    public void testReadLine() {
        RecordReader recordReader = new RecordReader();

        CallRecord record = recordReader.readLine("7340395059 15143 15143 62478 42118 93297 61977 24332 71309 71309 8414841992 2017-09-01T02:11:46.990");
        assertEquals(record.toString(), "CallRecord [dialler=7340395059, receiver=8414841992, diallerSwitch=15143, receiverSwitch=71309, connectionPath=[15143, 15143, 62478, 42118, 93297, 61977, 24332, 71309, 71309], timeStamp=2017-09-01T02:11:46.990]");

        record = recordReader.readLine("734095059 15143 15143 62478 42118 93297 61977 24332 71309 71309 8414841992 2017-09-01T02:11:46.990");
        assertNull(record);

        record = recordReader.readLine("7340395059 8414841992 2017-09-01T02:11:46.990");
        assertNull(record);

        record = recordReader.readLine("7340395059 15143 15143 628 42118 93297 61977 24332 71309 71309 8414841992 2017-09-01T02:11:46.990");
        assertNull(record);

        record = recordReader.readLine("7340395059 15143 15143 62478 42118 93297 61977 5624332 71309 71309 8414841992 2017-09-01T02:11:46.990");
        assertNull(record);

        record = recordReader.readLine("7340395059 15143 15143 62478 42118 93297 61977 24332 71309 71309 841481992 2017-09-01T02:11:46.990");
        assertNull(record);

        record = recordReader.readLine("7340395059 15143 15143 62478 8414841992 93297 61977 24332 71309 71309 8414841992 2017-09-01T02:11:46.990");
        assertNull(record);

        record = recordReader.readLine("7340395059 15143 8414841992 2017-09-01T02:11:46.990");
        assertEquals(record.toString(), "CallRecord [dialler=7340395059, receiver=8414841992, diallerSwitch=15143, receiverSwitch=15143, connectionPath=[15143], timeStamp=2017-09-01T02:11:46.990]");
    }
}