package com.example.wsizcbdproject;

import com.example.wsizcbdproject.Entity.Item;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DesktopClient extends Application {

    private TableView<Item> tableView;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(sceneSetUp());
        stage.show();
    }

    private Scene sceneSetUp() {
        // Main Vertical Box
        VBox vBox = new VBox();

        Button buttonRefresh = new Button("Refresh");

        // Box with top horizontal controls
        HBox topControls = new HBox();
        topControls.getChildren().add(buttonRefresh);
        VBox.setMargin(topControls, new Insets(8.0d));

        // Table View with our data
        tableView = new TableView<>();
        VBox.setMargin(tableView, new Insets(0.0d, 8.0d, 8.0d, 8.0d));
        TableColumn<Item, String> columnName = new TableColumn<>("Product Name");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Float> columnPrice = new TableColumn<>("Price");
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(columnName, columnPrice);
        tableView.getItems().addAll(fetchItems());

        // Adding elements to Main Vertical Box
        vBox.getChildren().addAll(topControls, tableView);

        // Setup listener for refresh button
        buttonRefresh.setOnAction(new OnRefreshClickListener());

        // Creating scene and adding our Main Vertical Box
        Scene scene = new Scene(vBox);
        return scene;
    }

    private Item[] fetchItems() {
        String uri = "http://localhost:8080/api/items";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Item[]> result = restTemplate.getForEntity(uri, Item[].class);
        return result.getBody();
    }

    private void refreshItems() {
        tableView.getItems().clear();
        tableView.getItems().addAll(fetchItems());
    }

    public void launchWindow(String... args) {
        launch(args);
    }

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

    class OnRefreshClickListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            refreshItems();
        }
    }
}
