package cs211.project.controllers.creator;

import cs211.project.models.account.AccountList;
import cs211.project.models.event.Eva;
import cs211.project.services.AccountDataSource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class ManageInfoController {

    @FXML Label nameEva;
    @FXML Label dateEva;

    private AccountList accountsList;
    private Datasource<AccountList> accountListDataSource;
    @FXML
    void ClickToGoManu() throws IOException {
        FXRouter.goTo("main");


    }
    @FXML
    void ClickGoToTeamSetting() throws IOException {
        FXRouter.goTo("staffMange");
    }
    @FXML
    public void initialize() {
        accountListDataSource = new AccountDataSource();
        accountsList = accountListDataSource.readData();

        Eva nameEvent = (Eva) FXRouter.getData();
        nameEva.setText(nameEvent.getName());
        dateEva.setText(nameEvent.getDate());

    }



}
