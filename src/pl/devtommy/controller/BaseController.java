package pl.devtommy.controller;

import pl.devtommy.EmailManager;
import pl.devtommy.view.ViewFactory;

public abstract class AbstractController {

    private EmailManager emailManager;
    private ViewFactory viewFactory;
    private String fxmlName;

    public AbstractController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }
}
