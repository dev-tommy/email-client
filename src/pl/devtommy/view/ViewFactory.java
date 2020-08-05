package pl.devtommy.view;

import pl.devtommy.EmailManager;

public class ViewFactory {
    private EmailManager emailManager;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void showLoginWindow()
    {
        System.out.println("Show login window");
    }
}
