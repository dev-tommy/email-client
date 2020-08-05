package pl.devtommy.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginWindowController {
    @FXML
    private TextField emailAddressField;

    @FXML
    private TextField passwordFileld;

    @FXML
    private Label errorLabel;

    @FXML
    void loginButtonAction() {
        System.out.println("Button click!");
    }
}
