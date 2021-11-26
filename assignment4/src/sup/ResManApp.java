package sup;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


/**
 * Yusuf Mert Köseoğlu
 *
 */
public class ResManApp {
    public static void menu(){
        System.out.println("\nMENU\n1:addCustomer:\n2:deleteCustomer\n3:addBooking\n4:listCustomerDetails\n5:displayCustomerLastBooking\n6:listCustomer\n-1:exit!");
    }

    /**
     * add customers to Customers array list i the main by creating a new customer and getting its values. Finally it adds to the Customers
     * @param Customers Customers array list that I created in main
     */
    public static void addCustomer(ArrayList<Customer> Customers) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter name, ID, gender, dateOfBirth(dd/MM/yyyy) , registration Date(dd/MM/yyyy), creditCardDetails: ");
        String name = myObj.nextLine();
        int ID = myObj.nextInt();
        char gender = myObj.next().charAt(0);
        String dateOfBirth = myObj.next();
        String registrationDate = myObj.next();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        Date date2 = null;
        try{
            date = dateFormat.parse(dateOfBirth);
            date2 = dateFormat.parse(registrationDate);

        } catch (ParseException e){
            e.printStackTrace();
        }
        String card = myObj.next();

        String[] values = dateOfBirth.split("/");      // to change 12/12/2020 (string) to 12122020
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);
        int date1 = day*1000000+ month*10000+year;

        String[] values1 = registrationDate.split("/");
        int day2 = Integer.parseInt(values1[0]);
        int month2 = Integer.parseInt(values1[1]);
        int year2 = Integer.parseInt(values1[2]);
        int date3 = day2*1000000+ month2*10000+year2;

        try {
        Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?&useLegacyDatetimeCode=false&serverTimezone=UTC", "cng443user", String.valueOf(1234));
        Statement statement = (Statement) connection.createStatement();
        String SQL = "INSERT INTO hms.person (id, name, gender, date_of_birth) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setInt(1, ID);
        ps.setString(2, name);
        ps.setString(3, String.valueOf(gender));
        ps.setInt(4, date1);
        ps.executeUpdate();
        ps.close();

        String SQL1 = "INSERT INTO hms.customer (customer_id, creditCardDetails, registrationDate) VALUES (?, ?, ?) ";
        PreparedStatement ps1 = connection.prepareStatement(SQL1);
        ps1.setInt(1, ID);
        ps1.setString(2, card);
        ps1.setInt(3, date3);
        ps1.executeUpdate();
        ps1.close();

        connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        Customer customer = new Customer();
        customer.setGender(gender);
        customer.setName(name);
        customer.setID(ID);
        customer.setDateOfBirth(date);
        customer.setRegistrationDate(date2);
        customer.setCreditCardDetails(card);
        Customers.add(customer);
    }


    /**
     * Deletes customer from Customers arraylist by taking ID
     * @param ID to be able to find the customer in Customers
     * @param Customers Customers array list that I created in main
     */

    public static void deleteCustomer(int ID, ArrayList<Customer> Customers){
        int i, index = -1;
        for (i = 0; i< Customers.size(); i++) {
            if (Customers.get(i).getID() == ID){
                index = i;
                break;
            }
        }

        if (index == -1)
            System.out.println("There is no customer with given ID number");
        else{
            try{
                index++;
                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?&useLegacyDatetimeCode=false&serverTimezone=UTC", "cng443user", String.valueOf(1234));
                Statement statement = (Statement) connection.createStatement();
                String SQL = "DELETE FROM hms.customer where customer_id = ?";
                String SQL2 = "DELETE FROM hms.person where id = ?";
                PreparedStatement ps = connection.prepareStatement(SQL);
                PreparedStatement ps1 = connection.prepareStatement(SQL2);
                ps.setInt(1, index);
                ps1.setInt(1, index);
                ps.executeUpdate();
                ps1.executeUpdate();
                ps1.close();
                ps.close();
                System.out.println("Customer successfully deleted");
                Customers.remove(--index);
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * shows the details of customer by taking ssn to detect which Customers it belongs
     * @param Customers Customers array list that I created in main
     * @param ID to be able to find the customer in Customers
     */
    public static void listCustomerDetails(int ID, ArrayList<Customer> Customers){
        int i, index = -1;
        for (i = 0; i< Customers.size(); i++) {
            if (Customers.get(i).getID() == ID){
                index = i;
                break;
            }
        }


        if (index == -1)
            System.out.println("There is no customer with given ID number");
        else{
            try {
                index++;
                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?&useLegacyDatetimeCode=false&serverTimezone=UTC", "cng443user", String.valueOf(1234));
                Statement statement = (Statement) connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select *, FROM_UNIXTIME(registrationDate/1000), FROM_UNIXTIME(date_of_birth/1000)  from person p inner join customer c ON c.customer_id = p.id where p.id = "+index+" ");
                resultSet.next();
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Gender: " + (resultSet.getString("gender")).charAt(0));
                System.out.println("Date of birth: " + resultSet.getDate("FROM_UNIXTIME(date_of_birth/1000)"));
                System.out.println("Registration date: " + resultSet.getDate("FROM_UNIXTIME(registrationDate/1000)"));
                System.out.println("Credit card details: " + resultSet.getString("creditCardDetails"));
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * by taking ID it finds which customer it belongs and adds booking to that customer
     * @param ID to be able to find the customer in Customers
     * @param Customers Customers array list that I created in main
     */
    public static void addBooking(int ID, ArrayList<Customer> Customers){
        int i, index = -1;
        for (i = 0; i< Customers.size(); i++) {
            if (Customers.get(i).getID() == ID){
                index = i;
                break;
            }
        }
        if (index == -1)
            System.out.println("There is no customer with given ID number");
        else{
            try {
                index++;
                Scanner myObj = new Scanner(System.in);
                System.out.println("Please enter details for booking\n Booking date(dd/MM/yyyy)");
                String bookingDate = myObj.next();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date3 = null;
                try{
                    date3 = dateFormat.parse(bookingDate);
                } catch (ParseException e){
                    e.printStackTrace();
                }

                String[] values = bookingDate.split("/");      // to change 12/12/2020 (string) to 12122020
                int day = Integer.parseInt(values[0]);
                int month = Integer.parseInt(values[1]);
                int year = Integer.parseInt(values[2]);
                int date1 = day*1000000+ month*10000+year;

                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?&useLegacyDatetimeCode=false&serverTimezone=UTC", "cng443user", String.valueOf(1234));
                Statement statement = (Statement) connection.createStatement();
                String SQL = "INSERT INTO hms.booking (customer_id, bookingDate) VALUES (?, ?)";
                PreparedStatement ps = connection.prepareStatement(SQL);
                ps.setInt(1,index);
                ps.setLong(2,date1);
                ps.executeUpdate();
                ps.close();

                Booking c_booking = new Booking();
                c_booking.setBookingDate(date3);
                Customers.get(index).makeBooking(c_booking);

                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * Finds the customers last booking by getting the max(bookingID) that customer has
     * @param ID customers id
     * @param Customers Customers array list that I created in main
     */
    public static void displayCustomerLastBooking(int ID, ArrayList<Customer> Customers) {
        int i, index = -1, count = 0;
        for (i = 0; i< Customers.size(); i++) {
            if (Customers.get(i).getID() == ID){
                index = i;
                break;
            }
        }

        if (index == -1)
            System.out.println("There is no customer with given ID number");
        else{
            index++;
            try{
                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?&useLegacyDatetimeCode=false&serverTimezone=UTC", "cng443user", String.valueOf(1234));
                Statement statement = (Statement) connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select MAX(b.bookingID),FROM_UNIXTIME(bookingDate/1000) from booking b inner join customer c ON c.customer_id = b.customer_id where b.customer_id = "+index+"");
                resultSet.next();
                System.out.println("Last booking date: " + resultSet.getDate("FROM_UNIXTIME(bookingDate/1000)"));
                System.out.println("Last booking ID: " + resultSet.getInt("MAX(b.bookingID)"));
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * List all the customers details
     * @param Customers Customers array list that I created in main
     */
    public static void listCustomer(ArrayList<Customer> Customers){
        int i = 0;
        if (Customers.size() == 0)
            System.out.println("There is no registered Customer ");
        else{
            try{
                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?&useLegacyDatetimeCode=false&serverTimezone=UTC", "cng443user", String.valueOf(1234));
                Statement statement = (Statement) connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select *,FROM_UNIXTIME(date_of_birth/1000), FROM_UNIXTIME(registrationDate/1000)  from person p inner join customer c ON c.customer_id = p.id");
                while(resultSet.next()){
                    System.out.println((i+1) + ". Customer's ID: " + resultSet.getInt("id"));
                    System.out.println((i+1) + ". Customer's Name: " + resultSet.getString("name"));
                    System.out.println((i+1) + ". Customer's Gender: " + (resultSet.getString("gender")).charAt(0));
                    System.out.println((i+1) + ". Customer's Date of birth: " + resultSet.getDate("FROM_UNIXTIME(date_of_birth/1000)"));
                    System.out.println((i+1) + ". Customer's Registration date: " + resultSet.getDate("FROM_UNIXTIME(registrationDate/1000)"));
                    System.out.println((i+1) + ". Customer's Credit Card Details: " + resultSet.getString("creditCardDetails"));
                    i++;
                }
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * To serialize the customers
     * @param Customers main array list that holds the customer class
     */
    public static void serialize (ArrayList<Customer> Customers){
        int i;
        String filename = "file.dat";
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            for (i = 0; i < Customers.size(); i++){
                out.writeObject(Customers.get(i));
            }
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * to create md5 file
     */
    public static void md5 () throws IOException, NoSuchAlgorithmException {
        String filename = "file.dat";
        FileInputStream fis = new FileInputStream (filename);
        BufferedInputStream bis = new BufferedInputStream (fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int ch;
        while ((ch = bis.read()) != -1) {
            baos.write (ch);
        }
        byte buffer[] = baos.toByteArray();
        MessageDigest algorithm = MessageDigest.getInstance ("MD5");
        algorithm.reset();

        algorithm.update (buffer);

        byte digest[] = algorithm.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<digest.length;i++) {
            hexString.append (
                    Integer.toHexString(0xFF & digest[i]));
            hexString.append (" ");
        }

        FileOutputStream fa = new FileOutputStream("M5.dat");
        fa.write(digest);
    }


    /**
     * Reads/regenerates the MD5 for the serialized objects in external file and check if it
     * is the same with the MD5 that stored when the application was closed
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void check () throws IOException, NoSuchAlgorithmException {
        String filename = "file.dat";
        FileInputStream fis = new FileInputStream (filename);                        //Reads/regenerates the MD5 for the serialized objects in external file
        BufferedInputStream bis = new BufferedInputStream (fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int ch;
        while ((ch = bis.read()) != -1) {
            baos.write (ch);
        }
        byte buffer[] = baos.toByteArray();
        MessageDigest algorithm = MessageDigest.getInstance ("MD5");
        algorithm.reset();

        algorithm.update (buffer);

        byte digest[] = algorithm.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<digest.length;i++) {
            hexString.append (
                    Integer.toHexString(0xFF & digest[i]));
            hexString.append (" ");
        }
        System.out.println(hexString.toString());




        byte rd[]= Files.readAllBytes(Paths.get("M5.dat"));                          // MD5 that stored to check with
        StringBuffer hexM5 = new StringBuffer();
        for (int i = 0; i < rd.length; i++){
            hexM5.append(Integer.toHexString(0xFF & rd[i]));
            hexM5.append(" ");
        }

        if (hexString.toString().equals(hexM5.toString())){
            System.out.println("Data has not been modified !! ");
        }
        else{
            System.out.println("Data has been modified !! ");
        }

    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException{
        ArrayList<Customer> Customers = new ArrayList<Customer>();
        try {
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?&useLegacyDatetimeCode=false&serverTimezone=UTC", "cng443user", String.valueOf(1234));
            Statement statement = (Statement) connection.createStatement();
            Statement statement2 =  (Statement) connection.createStatement();


            ResultSet result = statement.executeQuery("select *, FROM_UNIXTIME(date_of_birth/1000), FROM_UNIXTIME(registrationDate/1000)  from hms.person p inner join hms.customer c ON p.id = c.customer_id");
            int i = 0;
            while (result.next()){
                Customer customer = new Customer();
                Customers.add(customer);                                                                        // gets the customer details from database and stores it inside Customer <ArrayList>
                Customers.get(i).setID(result.getInt("id"));
                Customers.get(i).setName(result.getString("name"));
                Customers.get(i).setGender((result.getString("gender")).charAt(0));
                Customers.get(i).setCreditCardDetails(result.getString("creditCardDetails"));
                Customers.get(i).setDateOfBirth(result.getDate("FROM_UNIXTIME(date_of_birth/1000)"));
                Customers.get(i).setRegistrationDate(result.getDate("FROM_UNIXTIME(registrationDate/1000)"));
                i++;
            }

            ResultSet result2 = statement2.executeQuery("select bookingID, FROM_UNIXTIME(bookingDate/1000), customer_id from booking order by customer_id asc");
            i=0;
            int hold = -1;
            result2.next();
            Booking booking1 = new Booking();
            booking1.setBookingID(result2.getInt("bookingID"));
            booking1.setBookingDate(result2.getDate("FROM_UNIXTIME(bookingDate/1000)"));
            hold = result2.getInt("customer_id");                                          // holds the last customer_id to check in loop
            Customers.get(i).makeBooking(booking1);
            while (result2.next()){
                Booking booking = new Booking();
                if (result2.getInt("customer_id") == hold ){                               // to get more than one booking for one person. Checks if the last customer_id == current one we dont increase i
                    booking.setBookingID(result2.getInt("bookingID"));
                    booking.setBookingDate(result2.getDate("FROM_UNIXTIME(bookingDate/1000)"));
                    hold = result2.getInt("customer_id");
                    Customers.get(i).makeBooking(booking);
                }
                else{
                    booking.setBookingID(result2.getInt("bookingID"));
                    booking.setBookingDate(result2.getDate("FROM_UNIXTIME(bookingDate/1000)"));
                    hold = result2.getInt("customer_id");
                    Customers.get(i).makeBooking(booking);
                    i++;
                }
            }

            serialize(Customers);             //serialize the customer class
            check();                          //checks if the data has been changed


            Scanner obj = new Scanner(System.in);


            do{
                menu();
                System.out.println("Please choose a number to continue:");
                i = obj.nextInt();
                switch (i){
                    case 1:{
                        addCustomer(Customers);
                        break;
                    }
                    case 2:{
                        System.out.println("ID:");
                        int id = obj.nextInt();
                        deleteCustomer(id,Customers);
                        break;
                    }
                    case 3:{
                        System.out.println("ID:");
                        int id = obj.nextInt();
                        addBooking(id,Customers);
                        break;
                    }
                    case 4:{
                        System.out.println("ID:");
                        int id = obj.nextInt();
                        listCustomerDetails(id,Customers);
                        break;
                    }
                    case 5:{
                        System.out.println("ID:");
                        int id = obj.nextInt();
                        displayCustomerLastBooking(id,Customers);
                        break;
                    }
                    case 6:{
                        listCustomer(Customers);
                        break;
                    }
                    case -1:{
                        break;
                    }
                }
            }while(i!=-1);
            md5();                     // creates the md5 file for serialized customer
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
