package pl.devtommy;

import javafx.scene.control.TreeItem;
import pl.devtommy.model.EmailAccount;

public class EmailManager {
    private TreeItem<String> foldersRoot = new TreeItem<String>("");

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        TreeItem<String> treeItem = new TreeItem<String>(emailAccount.getAddress());
        treeItem.setExpanded(true);
            treeItem.getChildren().add(new TreeItem<String>("Inbox"));
        foldersRoot.getChildren().add(treeItem);
    }
}
