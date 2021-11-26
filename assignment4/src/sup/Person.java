package sup;

import java.io.Serializable;
import java.util.Date;

/**
 * Abstract class for customer class. Also can be serialazible
 */
public abstract class Person implements Serializable {
    protected int ID;
    protected String name;
    protected char gender;
    protected Date dateOfBirth;

    /**
     * Empty Constructor
     */
    public Person(){
        dateOfBirth = new Date(0,0,0);
    }

    /**
     * Constructor with persons variables
     * @param ID
     * @param name
     * @param gender
     * @param dateOfBirth
     */
    public Person(int ID, String name, char gender, Date dateOfBirth){
        this.ID = ID;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * getters and setters
     * @return
     */
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public java.util.Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(java.util.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
