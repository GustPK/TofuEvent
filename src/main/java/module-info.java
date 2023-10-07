module cs211.project.cs211661project {
    requires javafx.controls;
    requires javafx.fxml;


    opens cs211.project.cs211661project to javafx.fxml;
    exports cs211.project.cs211661project;

    exports cs211.project.controllers.login;
    opens cs211.project.controllers.login to javafx.fxml;

    exports cs211.project.controllers.main;
    opens cs211.project.controllers.main to javafx.fxml;

    exports cs211.project.controllers.event;
    opens cs211.project.controllers.event to javafx.fxml;

    exports cs211.project.controllers.creator;
    opens cs211.project.controllers.creator to javafx.fxml;

    exports cs211.project.controllers.team;
    opens cs211.project.controllers.team to javafx.fxml;

    exports cs211.project.models.account;
    opens cs211.project.models.account to javafx.fxml;

    exports cs211.project.models;
    opens cs211.project.models to javafx.fxml;

    exports cs211.project.models.collections;
    opens cs211.project.models.collections to javafx.fxml;
}