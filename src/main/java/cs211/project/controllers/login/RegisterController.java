package cs211.project.controllers.login;

import cs211.project.models.account.Account;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.AccountList;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class RegisterController {
    @FXML
    private Circle imageCircle;
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    private Datasource<AccountList> datasource;
    private AccountList accountList;
    private String imgSrc;
    private String status;

    @FXML
    private void initialize() {
        datasource = new AccountListDatasource();
        accountList = datasource.readData();

        // ตั้งค่ารูปภาพเริ่มต้นสำหรับ imageCircle
        File defaultImageFile = new File("data/images/default-pfp.jpg"); // เปลี่ยน path ไปยังรูปภาพเริ่มต้นของคุณ
        String defaultImagePath = "file:///" + defaultImageFile.getAbsolutePath();
        Image defaultImage = new Image(defaultImagePath);
        imageCircle.setFill(new ImagePattern(defaultImage));
    }

    @FXML
    public void clickBackToLogin() throws IOException {
        FXRouter.goTo("login");
    }

    @FXML
    public void handleUploadButton(MouseEvent event) {
        String username = usernameField.getText();
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("data/images");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                //เปลี่ยนชื่อรูปภาพ
                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                imgSrc = filename;
                Image image = new Image(target.toUri().toString());
                imageCircle.setFill(new ImagePattern(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            imgSrc = "default-pfp.jpg";
            Image image = new Image(getClass().getResourceAsStream("/data/images/default-pfp.jpg"));
            imageCircle.setFill(new ImagePattern(image));
        }
    }

    @FXML
    public void onSignUp() throws IOException {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // ตั้งค่าค่า status เป็น "not banned" และ imgSrc เป็น "default-pfp.jpg" เมื่อไม่มีการอัปโหลดรูปภาพ
        if (imgSrc == null || imgSrc.isEmpty()) {
            imgSrc = "default-pfp.jpg"; // รูปภาพ default-pfp.jpg จะต้องอยู่ในโฟลเดอร์ data/images
        }

        status = "not banned";

        // ตรวจสอบว่า username ไม่ซ้ำในรายการบัญชี
        boolean isUsernameUnique = isUsernameUnique(username);

        if (isUsernameUnique && password.equals(confirmPassword)) {
            accountList.addAccount(new Account(name, username, password, imgSrc, status));
            datasource.writeData(accountList);
            FXRouter.goTo("login");
        } else {
            // แสดง Alert ถ้า username ซ้ำหรือรหัสผ่านไม่ตรงกัน
            if (!isUsernameUnique) {
                showAlert("Duplicate Username", "This username is already taken.");
            }
            if (!password.equals(confirmPassword)) {
                showAlert("Password Mismatch", "Please double-check your password.");
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isUsernameUnique(String username) {
        for (Account account : accountList.getAccounts()) {
            if (account.getUsername().equals(username)) {
                return false; // username ซ้ำ
            }
        }
        return true; // username ไม่ซ้ำ
    }

}