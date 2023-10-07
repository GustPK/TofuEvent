package cs211.project.controllers.main;

import cs211.project.models.account.Account;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.AccountList;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProfileSettingController {
    @FXML
    private Label accountName;
    @FXML
    private Label username;
    @FXML
    private Circle profilePic;
    @FXML
    private TextField accountNameChangeField;
    @FXML
    private Account currentAccount;
    private AccountList accountList;
    private Datasource <AccountList> datasource;
    @FXML
    private void initialize(){
        currentAccount = (Account) FXRouter.getData();
        File file = new File("data/images", LoggedInAccount.getInstance().getAccount().getImage());
        String path = "file:///" + file.getAbsolutePath();
        Image image = new Image(path);
        profilePic.setFill(new ImagePattern(image));
        accountName.setText(LoggedInAccount.getInstance().getAccount().getName());
        username.setText(LoggedInAccount.getInstance().getAccount().getUsername());
        datasource = new AccountListDatasource();
        accountList = datasource.readData();
    }
    @FXML
    private void onBackButtonClick() throws IOException {
        FXRouter.goTo("profile");
    }
    @FXML
    private void onChangePasswordButtonClick() throws IOException {
        FXRouter.goTo("changePassword", "fromPSC");
    }

    @FXML
    private void onChangeProileButtonClick() throws IOException {
        FileChooser fileChooser = new FileChooser();

        // Set the title of the dialog
        fileChooser.setTitle("Select Profile Picture");

        // Set the file extension filter to restrict selection to image files
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        // Show the dialog and get the selected file
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Load and set the selected image as the profile picture
            Image image = new Image(selectedFile.toURI().toString());
            profilePic.setFill(new ImagePattern(image));

            // Optionally, you can save the selected image file path to the user's account for future reference
            currentAccount.setImage(selectedFile.getName());

            // Save the updated account information to your data source
            datasource.writeData(accountList);
        }
    }
}
