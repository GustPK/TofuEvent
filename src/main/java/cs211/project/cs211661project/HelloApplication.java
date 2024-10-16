package cs211.project.cs211661project;

import cs211.project.services.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "TOFU");
        stage.setWidth(850);
        stage.setHeight(630);
        configRoute();
        FXRouter.goTo("login");
        stage.setResizable(false);
    }

    private static void configRoute() {
        String resourcesPath = "cs211/project/views/";
        FXRouter.when("login", resourcesPath + "login-view.fxml");
        FXRouter.when("register", resourcesPath + "register-view.fxml");
        FXRouter.when("developer", resourcesPath + "dev-view.fxml");
        FXRouter.when("main", resourcesPath + "main-view.fxml");
        FXRouter.when("profile", resourcesPath + "profile-view.fxml");
        FXRouter.when("manage", resourcesPath + "manage-event-view.fxml");
        FXRouter.when("staffSchedule", resourcesPath + "staffSchedule-view.fxml");
        FXRouter.when("staffMange", resourcesPath + "staffMange-view.fxml");
        FXRouter.when("manageInfo", resourcesPath + "manageInfo-view.fxml");
        FXRouter.when("changePassword", resourcesPath + "changepassword-view.fxml");
        FXRouter.when("admin", resourcesPath + "admin-view.fxml");
        FXRouter.when("create", resourcesPath + "create-view.fxml");
        FXRouter.when("info", resourcesPath + "info-view.fxml");
        FXRouter.when("ongoing", resourcesPath + "ongoing-view.fxml");
        FXRouter.when("team", resourcesPath + "team-view.fxml");
        FXRouter.when("teamChat", resourcesPath + "teamchat-view.fxml");
        FXRouter.when("profileSetting", resourcesPath + "profile-setting-view.fxml");
        FXRouter.when("myteam", resourcesPath + "myteam-view.fxml");
        FXRouter.when("eventInfo", resourcesPath + "team-view.fxml");
        FXRouter.when("ban", resourcesPath + "ban-view.fxml");
        FXRouter.when("creatorEventList", resourcesPath + "edit-event-list-view.fxml");
        FXRouter.when("createParticipants", resourcesPath + "create-participants-view.fxml");
        FXRouter.when("createTeam", resourcesPath + "create-team-view.fxml");
        FXRouter.when("des", resourcesPath + "event-info-view.fxml");
        FXRouter.when("eventAttended",resourcesPath + "events-attended-view.fxml");
        FXRouter.when("editSchedule",resourcesPath + "edit-Schedule-view.fxml");
        FXRouter.when("scheduleActivity",resourcesPath + "schedule-activity-view.fxml");
        FXRouter.when("instruction", resourcesPath + "instruction-view.fxml");
    }


    public static void main(String[] args) {
        launch();

    }
}
