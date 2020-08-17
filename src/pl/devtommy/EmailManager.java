package pl.devtommy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.devtommy.controller.services.FetchFoldersService;
import pl.devtommy.controller.services.FolderUpdaterService;
import pl.devtommy.model.EmailAccount;
import pl.devtommy.model.EmailMessage;
import pl.devtommy.model.EmailTreeItem;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {
    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;
    private ObservableList<EmailAccount> emailAccounts = FXCollections.observableArrayList();

    public ObservableList<EmailAccount> getEmailAccounts() {
        return emailAccounts;
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    private FolderUpdaterService folderUpdaterService;
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    private List<Folder> folderList = new ArrayList<Folder>();
    public List<Folder> getFolderList(){
        return this.folderList;
    }

    public EmailManager(){
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public void addEmailAccount(EmailAccount emailAccount){
        emailAccounts.add(emailAccount);
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }

    public void setRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessageCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnRead() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementMessageCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        try {
            copySelectedMessageToTrash();

            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
            selectedMessage.getMessage().getFolder().expunge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copySelectedMessageToTrash() throws MessagingException {
        Folder currentFolder = selectedMessage.getMessage().getFolder();
        Folder trashFolder = getTrashFolder();
        Message[] messagesToCopy = {selectedMessage.getMessage()};

        if (!isTrashFolder(currentFolder)) {
            currentFolder.copyMessages(messagesToCopy, trashFolder);
        }
    }

    private boolean isTrashFolder(Folder folder) throws MessagingException {
        return folder.getName().equals("Trash");
    }

    private Folder getTrashFolder() throws MessagingException {
        Folder currentFolder = selectedMessage.getMessage().getFolder();
        Folder trashFolder;
        if (selectedMessage.getMessage().getFolder().getName().equals("INBOX") ) {
            trashFolder = currentFolder.listSubscribed("Trash")[0];
        } else {
            trashFolder = currentFolder.getParent().listSubscribed("Trash")[0];
        }
        return trashFolder;
    }


}
