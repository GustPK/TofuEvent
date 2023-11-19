package cs211.project.controllers.event;

import cs211.project.models.event.Team;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.IOException;

public class EventInfoController {
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
    private TextFlow descArea;
    @FXML
    ComboBox selectTeam;
    @FXML
    private ImageView imageClock1;
    @FXML
    private ImageView imageClock2;
    @FXML
    private ImageView imagePerson;
    @FXML
    private Label showTeamCount;
    @FXML
    private void initialize() {
        event = (Event) FXRouter.getData();
        participantList = new ParticipantList();
        participantDatasource = new ParticipantListDatasource();
        participantList = participantDatasource.readData();
        teamList = new TeamList();
        teamListDatasource = new TeamListDatasource();
        teamList = teamListDatasource.readData();
        datasource = new EventListDatasource();
        eventList = datasource.readData();

        Text text = new Text(event.getName());
        text.setFill(Color.WHITE);
        nameField.getChildren().add(text);
        text = new Text(event.getDesc());
        descArea.getChildren().add(text);

        startLabel.setText(event.getDateStart()+" "+event.getStartTime());
        endLabel.setText(event.getDateEnd()+" "+event.getEndTime());
        joinLabel.setText(event.getMaximum());




        int data7 = Integer.parseInt(event.getMaximum());
        int data8 = Integer.parseInt(event.getJoinedText());
        int countValue = data7 - data8;

        count.setText(String.valueOf(countValue));

        String imagePath = "data/images/" + event.getImgEvent();
        File imageFile = new File(imagePath);
        Image profileImage = new Image(imageFile.toURI().toString());
        imageEvent.setFill(new ImagePattern(profileImage));

        selectTeam.getItems().clear();

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

        selectTeam.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int x = (Integer.parseInt(teamList.findByTeamName(newValue.toString()).getJoinFieldText())-
                         Integer.parseInt(teamList.findByTeamName(newValue.toString()).getJoinedText()));
                showTeamCount.setText(x+" person left");

            }
        });
    }

    @FXML
    private void clickJoinEvent() {
        if (!buttonClicked) {
            String username = LoggedInAccount.getInstance().getAccount().getUsername();
            boolean isAlreadyJoined = participantList.getParticipants().stream()
                    .anyMatch(p -> p.getUsername().equals(username) && p.getEvent().equals(event.getName()) && p.getTeamName().equals("join"));

            if (event.getOrganizer().equals(username)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("You are the owner of this event");
                alert.showAndWait();
            } else if (isAlreadyJoined) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Already Joined");
                alert.setContentText("You've already joined the event.");
                alert.showAndWait();
            } else if (event.getJoinedText().equals(event.getMaximum())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Event has reached its maximum capacity");
                alert.setContentText("No more participants can join.");
                alert.showAndWait();
            } else {
                participantList.addParticipant(new Participant(username, event.getName(), "join","unbanned"));
                participantDatasource.writeData(participantList);
                Event current = eventList.findByEventName(event.getName());
                current.addJoin();
                datasource.writeData(eventList);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Joined");
                alert.setContentText("You have joined the event.");
                alert.showAndWait();

                buttonClicked = true;

                joinButton.setDisable(true);
            }
        }
    }
    @FXML
    private void clickJoinTeam() {
        String selectedTeam = (String) selectTeam.getValue();

        if (selectedTeam == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Team Selection Error");
            alert.setContentText("Please select a team from the dropdown.");
            alert.showAndWait();
        } else {
            String username = LoggedInAccount.getInstance().getAccount().getUsername();

            if (event.getOrganizer().equals(username)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("You are the owner of this event");
                alert.showAndWait();
            }else if (participantList.isAlreadyJoined(username,event.getName(),selectedTeam)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Already Joined");
                alert.setContentText("You've already joined the team for this event.");
                alert.showAndWait();
            }else if(teamList.findByTeamName(selectedTeam).getJoinedText().equals(teamList.findByTeamName(selectedTeam).getJoinFieldText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Event has reached its maximum capacity");
                alert.setContentText("No more team can join.");
                alert.showAndWait();

            } else {
                Team currentTeam = teamList.findByTeamName(selectedTeam);

                if (currentTeam == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Team Not Found");
                    alert.setContentText("The selected team does not exist.");
                    alert.showAndWait();
                } else {
                    participantList.addParticipant(new Participant(username, event.getName(), selectedTeam, "unbanned"));
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