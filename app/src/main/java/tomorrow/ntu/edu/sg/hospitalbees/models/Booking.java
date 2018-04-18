package tomorrow.ntu.edu.sg.hospitalbees.models;

import java.util.Date;

import tomorrow.ntu.edu.sg.hospitalbees.utilities.TIDParser;

/**
 * The type Booking.
 */
public class Booking {
    private final String tid;
    private final String status;
    private final String hospitalName;

    /**
     * Instantiates a new Booking.
     *
     * @param tid          the tid
     * @param status       the status
     * @param hospitalName the hospital name
     */
    public Booking(String tid, String status, String hospitalName) {
        this.tid = tid;
        this.status = status;
        this.hospitalName = hospitalName;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets hospital name.
     *
     * @return the hospital name
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * Gets booking time.
     *
     * @return the booking time
     */
    public Date getBookingTime() {
        return TIDParser.getBookingDate(tid);
    }
}
