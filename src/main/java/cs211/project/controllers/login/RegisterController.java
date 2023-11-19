package cs211.project.controllers.login;

import cs211.project.models.account.Account;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                File destDir = new File("data/images");
                if (!destDir.exists()) destDir.mkdirs();
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                imgSrc = filename;
                Image image = new Image(target.toUri().toString());
                imageCircle.setFill(new ImagePattern(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            imgSrc = "default-pfp.jpg";
        }
    }

    @FXML
    public void onSignUp() throws IOException {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (imgSrc == null || imgSrc.isEmpty()) {
            imgSrc = "default-pfp.jpg";
        } else {
            String[] fileSplit = imgSrc.split("\\.");
            String extension = fileSplit[fileSplit.length - 1];
            String newFileName = username +"_profile" + "." + extension;

            File oldFile = new File("data/images/" + imgSrc);
            File newFile = new File("data/images/" + newFileName);

            if (oldFile.renameTo(newFile)) {
                imgSrc = newFileName;
            }
        }

        status = "not banned";



        if (accountList.isUsernameUnique(username) && password.equals(confirmPassword)) {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            accountList.addAccount(new Account(name, username, password, imgSrc, status, formattedTime));
            datasource.writeData(accountList);


            FXRouter.goTo("login");
        } else {
            if (!accountList.isUsernameUnique(username)) {
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


}