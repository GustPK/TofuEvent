package cs211.project.controllers.login;

import cs211.project.models.account.Account;
import cs211.project.models.collections.AccountList;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


import java.io.IOException;

public class AdminController {
    @FXML
    private GridPane gridPane; // Reference to the GridPane in the FXML

    private Datasource<AccountList> datasource;
    private AccountList accounts;

    @FXML
    private void onChangePasswordHyperlinkClick() throws IOException {
        FXRouter.goTo("changePassword");
    }

    @FXML
    private void backButton() throws IOException {
        FXRouter.goTo("login");
    }

    @FXML
    private void initialize() {
        datasource = new AccountListDatasource();
        accounts = datasource.readData();

        accounts.sort(new LatestSortComparator());

        int column = 0;
        int row = 0;

        try {
            for (Account account : accounts.getAccounts()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/userlist-item-view.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                UserListItemController userListItemController = fxmlLoader.getController();
                userListItemController.setData(account);

                // Add an event handler to the AnchorPane to navigate to the "ban" page
                anchorPane.setOnMouseClicked(event -> {
                    try {
                        goToBanPage(account); // Create a method to navigate to the "ban" page
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                gridPane.add(anchorPane, column, row++);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToBanPage(Account account) throws IOException {
        // Pass the account information to the "ban" page controller if needed
        // You can use FXRouter to navigate to the "ban" page
        FXRouter.goTo("ban", account);
    }

}
