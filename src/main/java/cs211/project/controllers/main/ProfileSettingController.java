package cs211.project.controllers.main;

import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.AccountList;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
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

public class ProfileSettingController {
    @FXML
    private Label accountName;
    @FXML
    private Label username;
    @FXML
    private Circle profilePic;
    private AccountList accountList;
    private Datasource <AccountList> accountListDatasource;

    @FXML
    private void initialize(){
        File file = new File("data/images", LoggedInAccount.getInstance().getAccount().getImage());
        String path = "file:///" + file.getAbsolutePath();
        Image image = new Image(path);
        profilePic.setFill(new ImagePattern(image));
        accountName.setText(LoggedInAccount.getInstance().getAccount().getName());
        username.setText(LoggedInAccount.getInstance().getAccount().getUsername());
        accountListDatasource = new AccountListDatasource();
        accountList = accountListDatasource.readData();
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
    private void onChangeProfileButtonClick(MouseEvent event) {
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
                Image newImage = new Image(target.toUri().toString());
                profilePic.setFill(new ImagePattern(newImage));

                String imgSrc = filename;
                LoggedInAccount.getInstance().getAccount().setImage(imgSrc);

                accountList.findByUsername(LoggedInAccount.getInstance().getAccount().getUsername()).setImage(imgSrc);
                accountListDatasource.writeData(accountList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}