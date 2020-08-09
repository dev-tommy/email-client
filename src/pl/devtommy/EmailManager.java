package pl.devtommy;

import javafx.scene.control.TreeItem;
import pl.devtommy.controller.services.FetchFoldersService;
import pl.devtommy.model.EmailAccount;
import pl.devtommy.model.EmailTreeItem;

public class EmailManager {
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
