package pl.devtommy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.devtommy.controller.persistence.PersistenceAccess;
import pl.devtommy.controller.persistence.ValidAccount;
import pl.devtommy.model.EmailAccount;
import pl.devtommy.view.ViewFactory;

import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {

    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ViewFactory viewFactory = new ViewFactory(emailManager);
        viewFactory.showLoginWindow();
    }

    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for (EmailAccount emailAccount: emailManager.getEmailAccounts()) {
            validAccountList.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword()));
        }
        persistenceAccess.saveToPersistence(validAccountList);
    }
}
