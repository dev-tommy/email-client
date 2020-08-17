package pl.devtommy.model;

import javax.mail.Store;
import java.util.Properties;

public class EmailAccount {


    private String address;
    private String password;
    private Properties properties;
    private Store store;

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return address;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public EmailAccount(String address, String password) {
        this.address = address;
        this.password = password;
        properties = new Properties();

        properties.put("incomingHost", "s71.linuxpl.com");
        properties.put("mail.store.protocol", "imaps");

        properties.put("mail.transfer.protocol", "smtps");
        properties.put("mail.smtps.host", "s71.linuxpl.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "s71.linuxpl.com");

    }
}
