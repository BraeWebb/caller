package au.edu.uq.itee.comp3506.assn2.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RecordReader
 * Created 13/10/2017
 *
 * @author Brae Webb
 */
public class RecordReader {

    private static final Logger LOGGER = Logger.getLogger(RecordReader.class.getName());

    public void readLine(String line) {
        String[] lineArray = line.split(" ");

        if (lineArray[0].length() != 10) {
            LOGGER.log(Level.INFO, "Invalid Phone Number Length");
            return;
        }

        List<Integer> switches = new ArrayList<>();

        for (int i = 1; i < lineArray.length; i++) {
            String phoneSwitch = lineArray[i];
            if (phoneSwitch.length() == 5) {
                switches.add(Integer.parseInt(phoneSwitch));
            } else if (phoneSwitch.length() == 10) {
                break;
            } else {
                LOGGER.log(Level.INFO, "Invalid Switch Length");
                return;
            }
        }

        Long caller = Long.parseLong(lineArray[0]);
        Long receiver = Long.parseLong(lineArray[switches.size() + 1]);
        int callerSwitch = switches.get(0);
        int receiverSwitch = switches.get(switches.size() - 1);

        CallRecord callRecord = new CallRecord(
                caller,
                receiver,
                callerSwitch,
                receiverSwitch,
                switches,
                LocalDateTime.parse(lineArray[lineArray.length - 1])
        );

        LOGGER.info(callRecord.toString());
    }

    public void read(String file) {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            while (bufferedReader.ready()) {
                readLine(bufferedReader.readLine());
            }

            bufferedReader.close();
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Exception occurred trying to read call records.");
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException exception) {
                LOGGER.log(Level.SEVERE, "Failed to close a file.");
            }
        }
    }

    public static void main(String[] args) {
        RecordReader recordReader = new RecordReader();

        recordReader.read("data/call-records-short.txt");
    }
}