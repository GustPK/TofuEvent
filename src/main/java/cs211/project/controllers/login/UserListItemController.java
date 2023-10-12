package cs211.project.controllers.login;

import cs211.project.models.account.Account;
import cs211.project.models.collections.AccountList;
import cs211.project.models.event.Participant;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class UserListItemController {
    private Account selectedAccount;
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView profilePic;
    @FXML
    private Label statusLabel;
    @FXML
    private Label usernameLabel;

    public void setData(Account account) {
        this.selectedAccount = account;
        nameLabel.setText(account.getName());
        String imagePath = "data/images/" + selectedAccount.getImage();
        File imageFile = new File(imagePath);
        Image profileImage = new Image(imageFile.toURI().toString());
        profilePic.setImage(profileImage);
        statusLabel.setText(account.getStatus());
        usernameLabel.setText(account.getUsername());
    }
    private Datasource<AccountList> datasource;
    private AccountList accounts;

    public void setData(Participant account) {
        datasource = new AccountListDatasource();
        accounts = datasource.readData();
        for(Account account1 : accounts.getAccounts())
            if (account.getUsername().equals(account1.getUsername()))
                selectedAccount = account1;
        nameLabel.setText(" ");
        String imagePath = "data/images/" + selectedAccount.getImage();
        File imageFile = new File(imagePath);
        Image profileImage = new Image(imageFile.toURI().toString());
        profilePic.setImage(profileImage);
        statusLabel.setText("");
        usernameLabel.setText(account.getUsername());
    }
}
