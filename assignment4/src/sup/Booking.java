package sup;

import java.io.Serializable;
import java.util.Date;


/**
 * booking class that defines the how a booking is implemented
 */
public class Booking implements Serializable {
    private Date bookingDate;
    private int bookingID;

    /**
     * Empty constructor
     */
    public Booking(){
        bookingDate = new Date();
    }

    /**
     * Constructor
     * @param bookingDate
     */
    public Booking(Date bookingDate){
        this.bookingDate = bookingDate;
    }

    /**
     * Getter and setters for variables
     * @return
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }
}
