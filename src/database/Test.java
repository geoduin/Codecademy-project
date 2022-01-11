package database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        
        StudentRepository repo = new StudentRepository();
        Map<String, String> map = repo.retrieveNameByEmail();
        ArrayList<String> list = new ArrayList<>();

        for (String key : map.keySet()) {
            list.add(key + " Email: " + map.get(key));
        }
        Collections.sort(list);
        System.out.println(list);

    }
    
}
