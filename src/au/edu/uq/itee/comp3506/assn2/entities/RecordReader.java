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

    public static void main(String[] args) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data/call-records-short.txt"));

            while (bufferedReader.ready()) {
                String[] line = bufferedReader.readLine().split(" ");

                if (line[0].length() != 10) {
                    LOGGER.log(Level.INFO, "Invalid Phone Number Length");
                    continue;
                }

                List<Integer> switches = new ArrayList<>();

                for (int i = 1; i < line.length; i++) {
                    String phoneSwitch = line[i];
                    if (phoneSwitch.length() == 5) {
                        switches.add(Integer.parseInt(phoneSwitch));
                    } else if (phoneSwitch.length() == 10) {
                        break;
                    } else {
                        LOGGER.log(Level.INFO, "Invalid Switch Length");
                    }
                }

                Long caller = Long.parseLong(line[0]);
                Long receiver = Long.parseLong(line[switches.size() + 1]);
                int callerSwitch = switches.get(0);
                int receiverSwitch = switches.get(switches.size() - 1);

                CallRecord callRecord = new CallRecord(
                        caller,
                        receiver,
                        callerSwitch,
                        receiverSwitch,
                        switches,
                        LocalDateTime.parse(line[line.length - 1])
                );

                System.out.println(callRecord.toString());
            }

            bufferedReader.close();
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Exception occurred trying to read call records.");
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
        }
    }
}