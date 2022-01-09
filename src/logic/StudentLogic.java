package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.ModuleRepository;
import database.StudentRepository;
import database.WebcastRepository;
import domain.Gender;
import domain.Student;
import domain.Webcast;
import domain.Module;

public class StudentLogic {
    private StudentRepository studentRepo;
    private List<String> emails;

    public StudentLogic() {
        this.studentRepo = new StudentRepository();
        this.emails = new ArrayList<>();
    }

    // Creates a new student and sends it to the studentrepository
    public void newStudent(String name, String email, String GenderValue, String day, String month, String year,
            String street, String houseNumber, String postalCode, String country, String city) {
        LocalDate date = formatDate(year, month, day);
        Gender gender = convertToGender(GenderValue);
        int houseNr = Integer.parseInt(houseNumber);
        String formattedPostalCode = formatPostalCode(postalCode);
        Student createdStudent = new Student(name, gender, email, date, street, houseNr, formattedPostalCode, country,
                city);
        this.studentRepo.insert(createdStudent);
    }

    // Receives the attributes from the StudentGUI
    public void updateStudent(Student student, String name, String year,
            String month, String day, String gender, String street, String city, String country, String houseNr,
            String postalCode) {
        student.setStudentName(name);
        LocalDate newDate = formatDate(year, month, day);
        student.setDateOfBirth(newDate);
        Gender newGender = convertToGender(gender);
        student.setGender(newGender);
        student.setstreet(street);
        student.setCountry(country);
        student.setCity(city);
        student.setHouseNumber(Integer.parseInt(houseNr));
        student.setPostalCode(postalCode);
        this.studentRepo.update(student);
    }

    // It receives a hashmap from the student repository and returns this hashmap to
    // the StudentManagement view
    public Map<String, String> getNameAndEmail() {
        return this.studentRepo.retrieveNameByEmail();
    }

    // This method checks if the email exist and gives a boolean value
    public boolean emailExist(String checkedMail) {
        for (String email : this.studentRepo.retrieveNameByEmail().values()) {
            this.emails.add(email);
        }
        return this.emails.contains(checkedMail);
    }

    // This method receives the email string from the combobox and sends it to the
    // student repository
    // It will get the student back and sends it to the Student GUI
    public Student getStudentByEmail(String email) {
        return this.studentRepo.searchForForStudent(email);
    }

    // Based on the student email, it will search for the student, than it will
    // delete this student from the DB
    public void deleteStudentByEmail(String email) {
        Student studentAboutToDelete = this.studentRepo.searchForForStudent(email);
        this.studentRepo.delete(studentAboutToDelete);
    }

    // This method checks if the text is not blank. If the text is not blank, than
    // it will give a true value, if the text is blank then it will give false back
    public boolean fieldIsNotEmpty(String textFromField) {
        return !(textFromField.isBlank());
    }

    // This method checks if the stringInput is a decimal. If it is, than true.
    // Otherwise false
    public boolean areNumbers(String numberValue) {
        return (numberValue.matches("\\d+"));
    }

    // This method receives the postalcode
    // It returns a postalcode format <PostalCode(length 4)> <Letters(length =2)>
    public String formatPostalCode(String postalCode) {
        if (postalCode == null) {
            throw new NullPointerException();
            // return "Invalid";
        }

        Integer wordValue = Integer.valueOf(postalCode.trim().substring(0, 4));
        Integer twoLetterLength = postalCode.trim().substring(4).trim().length();
        if ((wordValue > 999 && wordValue <= 9999) && twoLetterLength == 2
                && postalCode.trim().substring(4).trim().toUpperCase().charAt(0) >= 'A'
                && postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z') {
            return postalCode.trim().substring(0, 4) + " " + postalCode.trim().substring(4).trim().toUpperCase();
        }

        else {
            throw new IllegalArgumentException();
        }
    }

    // Checks if the emailaddress has the right email format
    // If it doesn't contain a @, false
    // It has have the following minimum, <UserName(minimum 1 character)><Contains
    // '@'><subdomain(minimum 1 character)><.><tld (minimum 1 character)>
    //
    public boolean validateMailAddress(String mailAddress) {
        if (!mailAddress.contains("@") || (mailAddress.split("@")[0].length() < 1)) {
            return false;
        }

        // Checks if the email does not have more than 2 parts after the split '.'
        if (!mailAddress.contains("@") || (mailAddress.split("@")[1].split("\\.").length > 2)) {
            return false;
        }

        if (!mailAddress.contains("@") || (mailAddress.split("@")[1].split("\\.")[0].length() < 1)) {
            return false;
        }
        // If the tld is blank, than it will be false
        if (!mailAddress.contains("@") || (mailAddress.split("@")[1].split("\\.")[1].isBlank())) {
            return false;
        }

        // If it stil is valid
        return true;
    }

    // Formats the individual parts day, month and year in LocalDate format
    // yyyy-mm-dd
    public LocalDate formatDate(String year, String month, String day) {
        if (!(year.matches("//d") || !month.matches("//d") || !day.matches("//d"))) {
            throw new NumberFormatException();
        }

        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }

        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }

        if (Integer.parseInt(year) < 1000) {
            year = "0" + year;
        }

        return LocalDate.parse(year + "-" + month + "-" + day);
    }

    // Converts the string value to one of the Gender enumaration
    public Gender convertToGender(String Value) {
        if (Value.equals("O")) {
            return Gender.O;
        } else if (Value.equals("F")) {
            return Gender.F;
        } else {
            return Gender.M;
        }
    }

    // READY TO BE REMOVED
    public boolean addresIsValid(String street, String houseNr, String postalCode) {
        boolean addressIsFilled = (fieldIsNotEmpty(street) && fieldIsNotEmpty(postalCode) && fieldIsNotEmpty(houseNr));
        boolean houseNumberIsNumber = areNumbers(houseNr);
        boolean postalCodeIsRight = postalCodeHasTheRightFormat(postalCode);
        return (addressIsFilled && houseNumberIsNumber && postalCodeIsRight);
    }

    // Checks if the postalcode has the right format.
    public boolean postalCodeHasTheRightFormat(String postalCode) {
        try {
            String post = formatPostalCode(postalCode);
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    // Checks if the birthdate is earlier than now
    public boolean dateIsEarlierThanNow(String day, String month, String year) {
        LocalDate inputDate = null;
        try {
            inputDate = formatDate(year, month, day);
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return inputDate.isBefore(LocalDate.now()) || inputDate.isEqual(LocalDate.now());
    }

    // Checks if the date is valid, it accounts for leap years
    public boolean validateDate(int day, int month, int year) {
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10
                || month == 12) && (day >= 1) && (day <= 31)) {
            return true;
        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day >= 1 && day <= 30) {
            return true;
        } else if (month == 2 && day >= 1 && day <= 29 && year % 4 == 0 && year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if ((month == 2 && day >= 1 && day <= 29 && year % 4 == 0 && year % 100 != 0)) {
            return true;
        } else {
            return false;
        }
    }

    // A method where it checks if everything with date of birth is valid, with help
    // of the other methods
    // AreNumbers() DateIsEarlierThan() validateDate() and fieldIsNotEmpty
    // If all those methods return true, the current method will return a true
    // value.
    // READY TO BE REMOVED
    public boolean dateOfBirthIsValid(String day, String month, String year) {
        boolean valuesAreNumbers = areNumbers(day) && areNumbers(month) && areNumbers(year);
        boolean dateIsNotNow = dateIsEarlierThanNow(day, month, year);
        boolean dateIsCorrect = validateDate(Integer.parseInt(day), Integer.parseInt(month),
                Integer.parseInt(year));
        boolean dateIsFilled = fieldIsNotEmpty(day) && fieldIsNotEmpty(month) && fieldIsNotEmpty(year);
        return (valuesAreNumbers && dateIsNotNow && dateIsCorrect && dateIsFilled);

    }

    // Relay between GUI and Repo to receive the progression a Student has within
    // Modules. See StudentRepo for more details
    public Map<Module, Integer> receiveModuleProgressForStudent(Student student) {
        return this.studentRepo.retrieveAllModuleProgressOfStudent(student);
    }

    // Relay between GUI and Repo to update the progression a Student has within a
    // Module or webcast (ContentItem). See StudentRepo for more details
    public void updateProgressContentItem(int contentID, Student student, int newAmount) {
        this.studentRepo.updateProgressOfContentItem(contentID, student.getEmail(), newAmount);
    }

    // Overloaded variant for Webcast, where firstly the Webcast ID is retrieved
    public void updateProgressContentItem(String nameOfSelectedWebcast, Student student, int newAmount) {
        updateProgressContentItem(new WebcastRepository().retrieveByTitle(nameOfSelectedWebcast).getID(), student,
                newAmount);
    }

    // Relay between GUI and Repo to receive the progression a Student has within
    // Webcasts. In this case the key is the Webcast title, because contrary to a
    // module, a title for a webcast is always unique
    public Map<String, Integer> receiveWebcastProgressForStudent(Student student) {
        return this.studentRepo.retrieveAllWebcastProgressOfStudent(student.getEmail());

    }

    // Retrieving webcasts were the Student (given as argument) has no relation with
    // yet (Student isn't watching that webcast yet). Using the Student email as key
    // for the repo call
    public ArrayList<String> getWebcastsNotYetLinkedWithStudent(Student student) {
        return this.studentRepo.getWebcastTitlesForStudentThatHaveNoProgressRelation(student.getEmail());
    }

    // Make Student start having progress in a Webcast
    public void studentStartsWatching(String webcastName, Student student) {
        this.studentRepo.newProgressRecord(new WebcastRepository().retrieveByTitle(webcastName).getID(),
                student.getEmail());
    }
}
