package tomorrow.ntu.edu.sg.hospitalbees.utilities;

import java.time.Instant;
import java.util.Date;

public class TIDParser {

    private static final String ONLINE_PREFIX = "HB";

    public static String getClinicId(String tid) {
        return tid.substring(0, 4);
    }

    public static String getQueueNumber(String tid) {
        return ONLINE_PREFIX + tid.substring(tid.length() - 4, tid.length());
    }


}
