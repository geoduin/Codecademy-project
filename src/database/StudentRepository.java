package database;

import java.lang.Thread.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import domain.Gender;
import domain.Student;

public class StudentRepository extends Repository<Student> {

    @Override
    public void insert(Student student) {
        // TODO Auto-generated method stub
        String studentEmail = student.getEmail();
        String studentName = student.getStudentName();
        LocalDate dateOfBirth = student.getDateOfBirth();
        Gender gender = student.getGender();
        String street = student.getstreet();
        String city = student.getCity();
        String country = student.getCountry();
        int houseNr = student.getHouseNumber();
        String postalCode = student.getPostalCode();

        try {
            // Format tabel student is email, name, date of birth, gender, street, city,
            // country, housenumber and postalcode
            Statement statement = this.connection.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO Student VALUES('" + studentEmail + "','" + studentName + "', '"
                    + dateOfBirth + "','" + gender + "','" + street + "','" + city + "','" + country + "', " + houseNr
                    + ", '" + postalCode + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Student student) {
        // TODO Auto-generated method stub
        String studentEmail = student.getEmail();
        String studentName = student.getStudentName();
        LocalDate dateOfBirth = student.getDateOfBirth();
        Gender gender = student.getGender();
        String street = student.getstreet();
        String city = student.getCity();
        String country = student.getCountry();
        int houseNr = student.getHouseNumber();
        String postalCode = student.getPostalCode();
        try {
            Statement statement = this.connection.getConnection().createStatement();
            statement.executeUpdate("UPDATE Student SET Name = '" + studentName + "', Birthdate = '" + dateOfBirth
                    + "', Gender = '" + gender + "', Street ='" + street + "', City = '" + city + "', Country = '"
                    + country + "', Housenumber = " + houseNr + ", Postalcode = '" + postalCode + "' WHERE Email = '"
                    + studentEmail + "'");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Student student) {
        // TODO Auto-generated method stub
        try {
            String email = student.getEmail();
            Statement statement = this.connection.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM Student WHERE Email = '" + email + "'");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Deleting student has failed");
        }
    }

    @Override
    public ArrayList<Student> retrieve() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<String, String> retrieveNameByEmail() {
        Map<String, String> nameList = new HashMap<>();
        try {
            int i = 1;
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Name, Email FROM Student");
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                nameList.put("(" + Integer.toString(i) + ") " + name, email);
                i++;
            }

            return nameList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in retrieving data.");
            return null;
        }
    }

    public Student searchForStudent(String email) {
        try {
            Student student = null;
            Statement state = this.connection.getConnection().createStatement();
            ResultSet result = state.executeQuery("SELECT TOP 1 * FROM Student WHERE Email = '" + email + "'");
            while (result.next()) {
                String name = result.getString("Name");
                String emailaddress = result.getString("Email");
                Gender gender = Gender.valueOf(result.getString("Gender"));
                LocalDate birthday = LocalDate.parse(result.getString("Birthdate"));
                String street = result.getString("Street");
                int houseNumber = Integer.parseInt(result.getString("Housenumber"));
                String postalCode = result.getString("Postalcode");
                String country = result.getString("Country");
                String city = result.getString("City");

                student = new Student(name, gender, emailaddress, birthday, street, houseNumber, postalCode, country,
                        city);
            }
            return student;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Retrieval failed");
            return null;
        }
    }

}
