package cs211.project.controllers.login;

import cs211.project.models.account.Account;
import cs211.project.models.collections.AccountList;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class AdminController {
    @FXML
    private TableView<Account> AccoutsTableView;
    private Datasource<AccountList> datasource;
    private AccountList accounts;

    @FXML
    private void onChangePasswordHyperlinkClick() throws IOException {
        FXRouter.goTo("changePassword");
    }

    @FXML
    private void BackButton() throws IOException {
        FXRouter.goTo("login");
    }

    @FXML
    public void initialize() {
        datasource = new AccountListDatasource();
        accounts = datasource.readData();

        // Create columns
        TableColumn<Account, String> userColumn = new TableColumn<>("User");
        TableColumn<Account, String> passColumn = new TableColumn<>("Password");
        TableColumn<Account, String> statusColumn = new TableColumn<>("Status"); // เพิ่มคอลัมน์ "Status"

        // Set cell value factories using getter methods
        userColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status")); // เซ็ตคอลัมน์ "Status" ให้ใช้ getter ของ status

        userColumn.setMinWidth(200);
        passColumn.setMinWidth(200);
        statusColumn.setMinWidth(200); // ปรับขนาดของคอลัมน์ "Status"

        // Add columns to TableView
        AccoutsTableView.getColumns().addAll(userColumn, passColumn, statusColumn);

        for (Account acc : accounts.getAccounts()) {
            AccoutsTableView.getItems().add(acc);
        }

        AccoutsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                FXRouter.goTo("ban", newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Use an ObservableList as the data source
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList(accounts.getAccounts());

        // Set the items of the TableView to the ObservableList
        AccoutsTableView.setItems(accountObservableList);
    }
}