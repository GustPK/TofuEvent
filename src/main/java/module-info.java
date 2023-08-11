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
<<<<<<< HEAD
    exports cs211.project.controllers.creator;
    opens cs211.project.controllers.creator to javafx.fxml;
=======

    exports cs211.project.controllers.team;
    opens cs211.project.controllers.team to javafx.fxml;
>>>>>>> 697dc2208b081a2797103bca01e6734539074228
}