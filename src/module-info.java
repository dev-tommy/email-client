module EmailClientApp {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;
    requires java.desktop;

    opens pl.devtommy;
    opens pl.devtommy.view;
    opens pl.devtommy.controller;
    opens pl.devtommy.model;
}