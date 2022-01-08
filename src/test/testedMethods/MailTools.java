package test.testedMethods;

public class MailTools {

    public static void main(String[] args) {
        System.out.println(validateMailAddress("henk.butsers@gmail.nl"));
    }

    public static boolean validateMailAddress(String mailAddress) {
        return mailAddress.toLowerCase().matches("^[a-z0-9._%+-]+@[a-z0-9]+\\.[^\\.]+");
    }
}
