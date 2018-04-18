package tomorrow.ntu.edu.sg.hospitalbees.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class TIDParser {

    private static final String ONLINE_PREFIX = "HB";

    public static String getClinicId(String tid) {
        return tid.substring(0, 4);
    }

    public static String getQueueNumber(String tid) {
        return ONLINE_PREFIX + tid.substring(tid.length() - 4, tid.length());
    }

    public static Date getBookingDate(String tid) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String bookingTime = tid.substring(4, tid.length() - 4);
            return formatter.parse(bookingTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
