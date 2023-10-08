package cs211.project.controllers.login;

import cs211.project.models.account.Account;
import cs211.project.models.collections.AccountList;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.*;
import java.util.Optional;


public class BanController {
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label statusLabel;
    private Account account;
    @FXML
    private Circle profilePic;

    @FXML
    private void initialize() {
        account = (Account) FXRouter.getData();
        usernameLabel.setText(account.getName());
        passwordLabel.setText(account.getPassword());
        statusLabel.setText(account.getStatus());

        String imagePath = "data/images/" + account.getImage();
        File imageFile = new File(imagePath);
        Image profileImage = new Image(imageFile.toURI().toString());
        profilePic.setFill(new ImagePattern(profileImage));
    }

    @FXML
    private void onBack() throws IOException {
        FXRouter.goTo("admin");
    }

    @FXML
    private void onBan() {
        String username = account.getName();
        String confirmTitle = "Confirm Ban";
        String confirmHeaderText = "Ban user";
        String confirmMessage = "Are you sure you want to ban the " + username + "?"; // ข้อความยืนยัน

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(confirmTitle);
        confirmAlert.setHeaderText(confirmHeaderText);
        confirmAlert.setContentText(confirmMessage);

        Optional<ButtonType> result = confirmAlert.showAndWait();

        // ตรวจสอบผลลัพธ์จากการคลิกปุ่มใน Confirm Alert
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // ถ้าผู้ใช้กด OK
            if (account.getStatus().equals("not banned")) {
                updateAccountStatus("banned");
                String alertTitle = "Ban";
                String alertHeaderText = "Ban user";
                String alertMessage = "The account has been banned.";

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertHeaderText);
                alert.setContentText(alertMessage);
                alert.showAndWait();
            } else {
                String alertTitle = "Ban";
                String alertHeaderText = "Ban";
                String alertMessage = "The account is already banned.";

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(alertMessage);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertHeaderText);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onUnban() {
        String username = account.getName();
        String confirmTitle = "Confirm Unban";
        String confirmHeaderText = "Unban user";
        String confirmMessage = "Are you sure you want to unban the " + username + "?"; // ข้อความยืนยัน

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(confirmTitle);
        confirmAlert.setHeaderText(confirmHeaderText);
        confirmAlert.setContentText(confirmMessage);

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // ถ้าผู้ใช้กด OK
            if (account.getStatus().equals("banned")) {
                updateAccountStatus("not banned");
                String alertTitle = "Unban";
                String alertHeaderText = "Unban user";
                String alertMessage = "The account has been unbanned.";

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertHeaderText);
                alert.setContentText(alertMessage);
                alert.showAndWait();
            } else {
                String alertTitle = "Unban";
                String alertHeaderText = "Unban";
                String alertMessage = "The account is not banned.";

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(alertMessage);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertHeaderText);
                alert.showAndWait();
            }
        }
    }

    private void updateAccountStatus(String newStatus) {
        // Set the new status
        account.setStatus(newStatus);

        // Load the account list from the CSV file
        AccountList accountList = loadAccountListFromFile();

        // Find and update the account in the list
        for (Account account : accountList.getAccounts()) {
            if (account.getUsername().equals(this.account.getUsername())) {
                account.setStatus(newStatus);
                break; // Assuming usernames are unique, exit the loop once found
            }
        }

        // Save the updated account list back to the CSV file
        saveAccountListToFile(accountList);

        // Update the status label immediately
        statusLabel.setText(newStatus);
    }

    private AccountList loadAccountListFromFile() {
        AccountListDatasource datasource = new AccountListDatasource();
        return datasource.readData();
    }

    private void saveAccountListToFile(AccountList accountList) {
        AccountListDatasource datasource = new AccountListDatasource();
        datasource.writeData(accountList);
    }
}
