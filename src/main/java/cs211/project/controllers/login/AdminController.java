package cs211.project.controllers.login;

import cs211.project.models.account.Account;
import cs211.project.models.collections.AccountList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


import java.io.IOException;

public class AdminController {
    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scrollPane;

    private Datasource<AccountList> accountListDatasource;
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
        accountListDatasource = new AccountListDatasource();
        accounts = accountListDatasource.readData();

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

                anchorPane.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        try {
                            goToBanPage(account);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                gridPane.add(anchorPane, column, row++);
                GridPane.setMargin(anchorPane, new Insets(3));
            }
            scrollPane.setPannable(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToBanPage(Account account) throws IOException {
        FXRouter.goTo("ban", account);
    }

}
