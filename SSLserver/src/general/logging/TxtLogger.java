package general.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TxtLogger {

    private PrintWriter outFile;

    public TxtLogger() {
        File f = new File("logger.txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
                outFile = new PrintWriter(new FileOutputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                outFile = new PrintWriter(new FileOutputStream(f, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void log(String msg) {
        Date date = new Date();
        date.toString();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println(timeStamp + " - " + msg);
        outFile.append(timeStamp + " - " + msg + "\r\n");
        outFile.flush();
    }
}
