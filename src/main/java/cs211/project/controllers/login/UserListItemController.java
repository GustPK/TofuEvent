package cs211.project.controllers.login;

import cs211.project.models.account.Account;
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
}
