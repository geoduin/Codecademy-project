package test.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class emailExistence {

    /**
     * @desc Checks if email exist within arrayList
     * 
     * @BeforeClass retrieves email from HashMap
     * 
     * 
     * @Subcontract value exist{
     * 
     * @requires value present within the ArrayList
     * @ensures return true if exist
     *          }
     * 
     * @Subcontract value does not exist{
     * @requires value not present within the ArrayList
     * @ensures return false
     *          }
     * 
     */
    // This method checks if the email exist and gives a boolean value
    public boolean emailExist(String checkedMail) {
        Map<String, String> mapEmail = new HashMap<>();
        List<String> email = new ArrayList<>();
        mapEmail.put("Xin", "Xin20Wang@outlook.com");
        mapEmail.put("Henriette", "AnnettePenrose@gmail.com");
        mapEmail.put("Xi Jin Ping", "XinJinPingz@China.ch");

        for (String address : mapEmail.values()) {
            email.add(address);
        }
        return email.contains(checkedMail);
    }

}
