package cs211.project.controllers.event;

import cs211.project.models.collections.EventList;
import cs211.project.models.event.Event;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreateController {

    @FXML
    private Rectangle imageRec;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker datePicker;
    private Datasource<EventList> datasource;
    private EventList eventList;
    private String imgSrc;

    @FXML
    public void clickBackToMain() throws IOException {
        FXRouter.goTo("main");
    }

    @FXML
    public void clickToManage() throws IOException {
        FXRouter.goTo("manage");
    }

    @FXML
    private void initialize() {
        datasource = new EventListDatasource();
        eventList =datasource.readData();

        File defaultImageFile = new File("data/images/Image.jpg"); // เปลี่ยน path ไปยังรูปภาพเริ่มต้นของคุณ
        String defaultImagePath = "file:///" + defaultImageFile.getAbsolutePath();
        Image defaultImage = new Image(defaultImagePath);
        imageRec.setFill(new ImagePattern(defaultImage));
    }
    @FXML
    public void handleUploadButton(MouseEvent event) {
//        String username = usernameField.getText();
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
                imageRec.setFill(new ImagePattern(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            imgSrc = "default-pfp.jpg";
            Image image = new Image(getClass().getResourceAsStream("/data/images/default-pfp.jpg"));
            imageRec.setFill(new ImagePattern(image));
        }
    }
    @FXML
    public void clickSubmit() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String name = nameField.getText();
        LocalDate selectedDate = datePicker.getValue();
        String dateString = selectedDate.format(formatter);
;
        // ตั้งค่าค่า status เป็น "not banned" และ imgSrc เป็น "default-pfp.jpg" เมื่อไม่มีการอัปโหลดรูปภาพ
        if (imgSrc == null || imgSrc.isEmpty()) {
            imgSrc = "default-pfp.jpg"; // รูปภาพ default-pfp.jpg จะต้องอยู่ในโฟลเดอร์ data/images
        }


            eventList.addEvent(new Event(name,dateString,imgSrc));
            datasource.writeData(eventList);
            int lastIndex = eventList.getEvents().size()-1;
            FXRouter.goTo("manageInfo",eventList.getEvents().get(lastIndex));

    }
}
