package cs211.project.controllers.creator;

import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.EventList;
import cs211.project.models.event.Event;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateEventController {

    @FXML
    private Rectangle imageRec;
    @FXML
    private TextField nameField;
    @FXML
    private TextField joinField;
    @FXML
    private TextArea descArea;
    @FXML
    private DatePicker datePickerStart;
    @FXML
    private DatePicker datePickerEnd;
    @FXML
    private Spinner<Integer> hourSpinnerStart;
    @FXML
    private Spinner<Integer> minuteSpinnerStart;
    @FXML
    private Spinner<Integer> hourSpinnerEnd;
    @FXML
    private Spinner<Integer> minuteSpinnerEnd;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private String imageSource;

    @FXML
    public void clickBackToMain() throws IOException {
        FXRouter.goTo("main");
    }

    @FXML
    private void initialize() {
        eventListDatasource = new EventListDatasource();
        eventList = eventListDatasource.readData();

        File defaultImageFile = new File("data/images/Image.jpg");
        String defaultImagePath = "file:///" + defaultImageFile.getAbsolutePath();
        Image defaultImage = new Image(defaultImagePath);
        imageRec.setFill(new ImagePattern(defaultImage));

        SpinnerValueFactory<Integer> hourStartValueFactory = createSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> minuteStartValueFactory = createSpinnerValueFactory(0, 59, 0);
        SpinnerValueFactory<Integer> hourEndValueFactory = createSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> minuteEndValueFactory = createSpinnerValueFactory(0, 59, 0);

        hourSpinnerStart.setValueFactory(hourStartValueFactory);
        minuteSpinnerStart.setValueFactory(minuteStartValueFactory);
        hourSpinnerEnd.setValueFactory(hourEndValueFactory);
        minuteSpinnerEnd.setValueFactory(minuteEndValueFactory);
    }

    private SpinnerValueFactory<Integer> createSpinnerValueFactory(int min, int max, int initialValue) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue);
        valueFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                return 0;
            }
        });
        return valueFactory;
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
                imageSource = filename;
                Image image = new Image(target.toUri().toString());
                imageRec.setFill(new ImagePattern(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            imageSource = "default-pfp.jpg";
            Image image = new Image(getClass().getResourceAsStream("/data/images/default-pfp.jpg"));
            imageRec.setFill(new ImagePattern(image));
        }
    }

    @FXML
    public void clickNext() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String name = nameField.getText();
        String desc = descArea.getText();
        String joinFieldText = joinField.getText();
        LocalDate startDate = datePickerStart.getValue();
        LocalDate endDate = datePickerEnd.getValue();
        int startHour = hourSpinnerStart.getValue();
        int startMinute = minuteSpinnerStart.getValue();
        int endHour = hourSpinnerEnd.getValue();
        int endMinute = minuteSpinnerEnd.getValue();

        if (name.isEmpty() || joinFieldText.isEmpty() || desc.isEmpty() || startDate == null || endDate == null || startHour < 0 || startHour > 23
                || startMinute < 0 || startMinute > 59 || endHour < 0 || endHour > 23 || endMinute < 0 || endMinute > 59) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Please fill in all required fields.");
            alert.showAndWait();
        } else if (!isNumeric(joinFieldText)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Please enter Max join count correctly.");
            alert.showAndWait();
        } else if (eventList.isEventNameDuplicate(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Event name already exists. Please choose a different name.");
            alert.showAndWait();
        } else {
            String startDateString = startDate.format(formatter);
            String endDateString = endDate.format(formatter);
            String startTimeString = String.format("%02d:%02d", startHour, startMinute);
            String endTimeString = String.format("%02d:%02d", endHour, endMinute);

            if (imageSource == null || imageSource.isEmpty()) {
                imageSource = "default-pfp.jpg";
            } else {
                String[] fileSplit = imageSource.split("\\.");
                String extension = fileSplit[fileSplit.length - 1];
                String newFileName =  name +"_pic" + "." + extension;

                File oldFile = new File("data/images/" + imageSource);
                File newFile = new File("data/images/" + newFileName);

                if (oldFile.renameTo(newFile)) {
                    imageSource = newFileName;
                }
            }

            String status = "UNDONE";

            eventList.addEvent(new Event(LoggedInAccount.getInstance().getAccount().getUsername(), name, startDateString, endDateString, startTimeString, endTimeString, desc, joinFieldText, "0", status, imageSource));
            eventListDatasource.writeData(eventList);
            int lastIndex = eventList.getEvents().size() - 1;
            FXRouter.goTo("createParticipants", eventList.getEvents().get(lastIndex));
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
