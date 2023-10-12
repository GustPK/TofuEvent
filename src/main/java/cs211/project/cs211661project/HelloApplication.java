package cs211.project.cs211661project;

import cs211.project.services.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        configRoute();
        FXRouter.bind(this, stage, "CS211 661 Project");
        FXRouter.goTo("login");
    }

    private static void configRoute() {
        String resourcesPath = "cs211/project/views/";
        FXRouter.when("login", resourcesPath + "login-view.fxml");
        FXRouter.when("register", resourcesPath + "register-view.fxml");
        FXRouter.when("developer", resourcesPath + "dev-view.fxml");
        FXRouter.when("main", resourcesPath + "main-view.fxml");
        FXRouter.when("profile", resourcesPath + "profile-view.fxml");
        FXRouter.when("manage", resourcesPath + "manage.fxml");
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
        FXRouter.when("creatorEventList", resourcesPath + "creatorEventList.fxml");
        FXRouter.when("joinHistory", resourcesPath + "join-history-view.fxml");
        FXRouter.when("createParticipants", resourcesPath + "create-participants-view.fxml");
        FXRouter.when("createTeam", resourcesPath + "create-team-view.fxml");
        FXRouter.when("des", resourcesPath + "eventDes.fxml");
        FXRouter.when("eventAttended",resourcesPath + "eventsAttended.fxml");
        FXRouter.when("editSchedule",resourcesPath + "EditSchedule-view.fxml");

    }


    public static void main(String[] args) {
        launch();

    }
}
