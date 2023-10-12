package cs211.project.controllers.event;

import cs211.project.models.Team;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.ParticipantList;
import cs211.project.models.collections.TeamList;
import cs211.project.models.event.Event;
import cs211.project.models.event.Participant;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Des {
    @FXML
    private Label count;
    @FXML private TextFlow nameField;
    @FXML private Rectangle imageEvent;
    @FXML
    private Button joinButton;
    private boolean buttonClicked = false;
    private Datasource<EventList> datasource;
    private EventList eventList;
    private Datasource<ParticipantList> participantDatasource;
    private Datasource<TeamList> teamListDatasource;
    private ParticipantList participantList;
    private TeamList teamList;
    private Event event;
    @FXML
    private Label startLabel;
    @FXML
    private Label endLabel;
    @FXML
    private Label joinLabel;
    @FXML
    private TextArea descArea;
    @FXML
    ComboBox selectTeam;
    @FXML
    private ImageView imageClock1;
    @FXML
    private ImageView imageClock2;
    @FXML
    private ImageView imagePerson;
    @FXML
    private void initialize() {
        event = (Event) FXRouter.getData();
        participantList = new ParticipantList();
        participantDatasource = new ParticipantListDatasource();
        participantList = participantDatasource.readData();
        teamList = new TeamList();
        teamListDatasource = new TeamListDatasource("data","TeamList.csv");
        teamList = teamListDatasource.readData();
        datasource = new EventListDatasource();
        eventList = datasource.readData();

        Text text = new Text(event.getName());
        text.setFill(Color.WHITE);

        startLabel.setText(event.getDateStart()+" "+event.getStartTime());
        endLabel.setText(event.getDateEnd()+" "+event.getEndTime());
        joinLabel.setText(event.getJoinFieldText());
        descArea.setText(event.getDesc());

        nameField.getChildren().add(text);

        int data7 = Integer.parseInt(event.getJoinFieldText()); // Assuming data[7] is an integer in the CSV
        int data8 = Integer.parseInt(event.getJoinedText()); // Assuming data[8] is an integer in the CSV
        int countValue = data7 - data8;

        // Convert the count value to a String and set it to the label
        count.setText(String.valueOf(countValue));

        String imagePath = "data/images/" + event.getImgEvent();
        File imageFile = new File(imagePath);
        Image profileImage = new Image(imageFile.toURI().toString());
        imageEvent.setFill(new ImagePattern(profileImage));

        // Populate the ComboBox with team names
        selectTeam.getItems().clear(); // Clear existing items (if any)

        for (Team team : teamList.getTeams()) {
            if (team.getEventName().equals(event.getName()))
                selectTeam.getItems().add(team.getTeamName());
        }

        String clockImagePath = "file:data/images/clock.png";
        Image clockImage = new Image(clockImagePath);
        imageClock1.setImage(clockImage);
        imageClock2.setImage(clockImage);

        String personImagePath = "file:data/images/person.png";
        Image personImage = new Image(personImagePath);
        imagePerson.setImage(personImage);
    }

    @FXML
    private void clickJoinEvent() {
        if (!buttonClicked) {
            // Check if the participant is already in the list
            String username = LoggedInAccount.getInstance().getAccount().getUsername();
            boolean isAlreadyJoined = participantList.getParticipants().stream()
                    .anyMatch(p -> p.getUsername().equals(username) && p.getEvent().equals(event.getName()));

            if (isAlreadyJoined) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Already Joined");
                alert.setContentText("You've already joined the event.");
                alert.showAndWait();
            } else {
                // Add the participant to the list
                participantList.addParticipant(new Participant(username, event.getName(), "join"));
                participantDatasource.writeData(participantList);
                Event current = eventList.findByEventName(event.getName());
                current.addJoin();
                datasource.writeData(eventList);

                // Show a popup indicating that the user has joined
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Joined");
                alert.setContentText("You have joined the event.");
                alert.showAndWait();

                buttonClicked = true;

                // Optionally, you can disable the button to prevent further clicks
                joinButton.setDisable(true);
            }
        }
    }
    @FXML
    private void clickJoinTeam() {
        String selectedTeam = (String) selectTeam.getValue();

        if (selectedTeam == null) {
            // No team selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Team Selection Error");
            alert.setContentText("Please select a team from the dropdown.");
            alert.showAndWait();
        } else {
            String username = LoggedInAccount.getInstance().getAccount().getUsername();

            // Check if the user is already in the participant list for the selected team
            boolean isAlreadyJoined = participantList.getParticipants().stream()
                    .anyMatch(p -> p.getUsername().equals(username) && p.getEvent().equals(event.getName()) && p.getTeamName().equals(selectedTeam));

            if (isAlreadyJoined) {
                // User is already in the list for the selected team
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Already Joined");
                alert.setContentText("You've already joined the team for this event.");
                alert.showAndWait();
            } else {
                // Find the team in teamList
                Team currentTeam = teamList.findByTeamName(selectedTeam);

                if (currentTeam == null) {
                    // Handle the case where the team doesn't exist
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Team Not Found");
                    alert.setContentText("The selected team does not exist.");
                    alert.showAndWait();
                } else {
                    // Add the participant with the selected team to the list
                    participantList.addParticipant(new Participant(username, event.getName(), selectedTeam));
                    participantDatasource.writeData(participantList);

                    currentTeam.addJoin();
                    teamListDatasource.writeData(teamList);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Joined Team");
                    alert.setContentText("You have joined the team for this event.");
                    alert.showAndWait();
                }
            }
        }
    }


    @FXML
    protected void onBackButtonClick() throws IOException {
        FXRouter.goTo("main");
    }
}