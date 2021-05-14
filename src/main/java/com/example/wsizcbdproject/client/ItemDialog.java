package com.example.wsizcbdproject.client;

import com.example.wsizcbdproject.Entity.Item;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ItemDialog extends Dialog<Item> {

    private final TextField tfName = new TextField();
    private final TextField tfPrice = new TextField();
    private int id = 0;

    public ItemDialog() {
        baseSetup();
    }

    public ItemDialog(Item item) {
        baseSetup();
        init(item);
    }

    private void baseSetup() {
        Label nameLabel = new Label("Name");
        Label priceLabel = new Label("Price");

        VBox vBox = new VBox(
                nameLabel,
                tfName,
                priceLabel,
                tfPrice);

        vBox.setSpacing(10.0d);
        vBox.setPadding(new Insets(40.0d));

        DialogPane dialogPane = getDialogPane();
        setTitle("Item specifics");
        setResultConverter(this::formResult);

        ButtonType buttonSave = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(buttonSave, ButtonType.CANCEL);
        dialogPane.setContent(vBox);
    }

    private void init(Item item) {
        tfName.setText(item.getName());
        tfPrice.setText(String.valueOf(item.getPrice()));
        this.id = item.getId();
    }

    private Item formResult(ButtonType buttonType) {

        Item returnItem = null;
        if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            try {
                returnItem = new Item(
                        id,
                        tfName.getText(),
                        Float.parseFloat(tfPrice.getText()));
            } catch (Exception e) {
                System.out.println("Invalid input. Item not inserted.");
            }
        }
        return returnItem;
    }
}
