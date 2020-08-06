module EmailClientApp {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;

    opens pl.devtommy;
    opens pl.devtommy.view;
    opens pl.devtommy.controller;
}