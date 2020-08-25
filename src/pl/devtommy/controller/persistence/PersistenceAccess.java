package pl.devtommy.controller.persistence;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess {

    private String VALID_ACCOUNTS_LOCATION = System.getenv("APPDATA") + "\\validAccounts.ser";

    public List<ValidAccount> loadFromPersistence(){
        List<ValidAccount> resultList = new ArrayList<ValidAccount>();
        try {
            FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNTS_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<ValidAccount> persistedList = (List<ValidAccount>) objectInputStream.readObject();
            resultList.addAll(persistedList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

}
