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
        FXRouter.when("developer", resourcesPath + "Dev-views.fxml");
        FXRouter.when("dev", resourcesPath + "Dev-view.fxml");
        FXRouter.when("main", resourcesPath + "main-view.fxml");
        FXRouter.when("profile", resourcesPath + "profile-view.fxml");
        FXRouter.when("changePassword", resourcesPath + "changepassword-view.fxml");
        FXRouter.when("admin", resourcesPath + "admin-view.fxml");
    }


    public static void main(String[] args) {
        launch();
    }
}
