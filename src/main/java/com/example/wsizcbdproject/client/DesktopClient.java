package com.example.wsizcbdproject.client;

import com.example.wsizcbdproject.Entity.Item;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DesktopClient extends Application {

    private TableView<Item> tableView;
    private final ClientRestHelper clientRestHelper = new ClientRestHelper();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(sceneSetUp());
        stage.show();
    }

    /**
     * Setups scene for window
     *
     * @return Scene
     */
    private Scene sceneSetUp() {
        // Main Vertical Box
        VBox vBox = new VBox();

        Button buttonRefresh = new Button("Refresh");
        Button buttonDelete = new Button("Delete");
        Button buttonUpdate = new Button("Update");
        Button buttonAdd = new Button("Add");

        // Box with top right horizontal controls
        HBox topRightControls = new HBox();
        topRightControls.setAlignment(Pos.BOTTOM_RIGHT);
        HBox.setHgrow(topRightControls, Priority.ALWAYS);
        topRightControls
                .getChildren()
                .addAll(buttonRefresh,
                        buttonUpdate,
                        buttonDelete);

        // Box with add button ant top right horizontal controls
        HBox topControls = new HBox();
        topControls
                .getChildren()
                .addAll(buttonAdd,
                        topRightControls);
        VBox.setMargin(topControls, new Insets(8.0d));

        // Table View with our data
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setMargin(tableView, new Insets(0.0d, 8.0d, 8.0d, 8.0d));
        TableColumn<Item, String> columnName = new TableColumn<>("Product Name");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Float> columnPrice = new TableColumn<>("Price");
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(columnName, columnPrice);
        tableView.getItems().addAll(clientRestHelper.fetchItems());

        // Adding elements to Main Vertical Box
        vBox.getChildren().addAll(topControls, tableView);

        // Setup listener for refresh button
        buttonRefresh.setOnAction(new OnRefreshClickListener());
        buttonDelete.setOnAction(new OnDeleteClickListener());

        // Creating scene and adding our Main Vertical Box
        Scene scene = new Scene(vBox);
        return scene;
    }

    private void refreshItems() {
        tableView.getItems().clear();
        tableView.getItems().addAll(clientRestHelper.fetchItems());
    }

    public void launchClient(String... args) {
        launch(args);
    }

    private boolean deleteConfirmDialog(Item item) {
        if (item == null)
            return false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("You are about to delete item");
        alert.setContentText("Are you sure you want to delete: " + item.toString());

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Prints fetched data to console.
     *
     * @return this DesktopClient.
     */
    public DesktopClient printItemList() {
        String uri = "http://localhost:8080/api/items";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Item[]> result = restTemplate.getForEntity(uri, Item[].class);
        Item[] items = result.getBody();
        List<Item> itemList = new ArrayList<>();
        if (items != null) {
            itemList.addAll(Arrays.asList(items));

            for (Item item :
                    itemList) {
                System.out.println(item.toString());
            }
        } else {
            System.out.println("Didn't get items.");
        }

        return this;
    }

    /**
     * Inner class for handling click events on refresh button.
     */
    private class OnRefreshClickListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            refreshItems();
        }
    }

    /**
     * Inner class for handling click events on delete button.
     */
    private class OnDeleteClickListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            Item item = tableView.getSelectionModel().getSelectedItem();
            // Check if deletion confirmed
            if (deleteConfirmDialog(item)) {
                String msg = clientRestHelper.deleteItem(item);
                System.out.println(msg);
                refreshItems();
            }
        }
    }
}
