package sup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * Customer class which is has a abstract class called person and can be serialazied
 */
public class Customer extends Person implements Serializable {
    private Date registrationDate;
    private ArrayList<Booking> bookings;
    private String creditCardDetails;

    /**
     * Empty Constructor
     */
    public Customer(){
        this.registrationDate = new Date();
        this.bookings = new ArrayList<Booking>();
    }

    /**
     * Constructor with variables of Customer
     * @param ID
     * @param name
     * @param gender
     * @param dateOfBirth
     * @param registrationDate
     * @param creditCardDetails
     */
    public Customer(int ID, String name, char gender, Date dateOfBirth, Date registrationDate, String creditCardDetails){
        this.bookings = new ArrayList<Booking>();
        this.registrationDate = registrationDate;
        this.creditCardDetails = creditCardDetails;
        this.ID = ID;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Getter and setters for variables
     * @return
     */

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(String creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }

    /**
     * To make a booking
     * @param booking booking class to add it to arraylist
     */
    public void makeBooking(Booking booking){
        this.bookings.add(booking);
    }

    /**
     * To make a booking
     * @param order order class to add it to arraylist
     */

}
