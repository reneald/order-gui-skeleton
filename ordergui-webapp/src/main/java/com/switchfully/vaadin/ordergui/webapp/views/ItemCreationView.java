package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ItemCreationView extends CustomComponent implements View {

    private VerticalLayout mainLayout = new VerticalLayout();
    private TextField name;
    private TextArea description;
    private TextField price;
    private TextField amountOfStock;
    private BeanFieldGroup<Item> fieldGroup;
    private Item item;

    public ItemCreationView(ItemResource resource) {
        this.resource = resource;
    }

    private ItemResource resource;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        name = createNameField();
        description = createDescriptionField();
        price = createPriceField();
        amountOfStock = createAmountOfStockField();

        bindFields();

        setWidth("100%");
        TopMenu topMenu = new TopMenu();
        Label newItem = new Label("New Item");
        newItem.setStyleName(ValoTheme.LABEL_H1);

        name.setWidth("50%");

        description.setWidth("50%");

        Label euro = new Label();
        euro.setIcon(FontAwesome.EURO);

        HorizontalLayout euroAndPrice = new HorizontalLayout(euro, price);
        euroAndPrice.setCaption("Price");

        HorizontalLayout priceAndStock = new HorizontalLayout(euroAndPrice, amountOfStock);
        priceAndStock.setSpacing(true);

        Button createButton = new Button("Create");
        createButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        createButton.addClickListener(event1 -> {
            try {
                fieldGroup.commit();
                resource.save(item);
                getUI().getNavigator().navigateTo("items");

            } catch (FieldGroup.CommitException ex) {
                Notification.show(ex.getMessage());
            }

        });

        Button cancelButton = new Button("Cancel");

        HorizontalLayout buttons = new HorizontalLayout(createButton, cancelButton);
        buttons.setSpacing(true);

        mainLayout.addComponents(topMenu, newItem, name, description, priceAndStock, buttons);
        mainLayout.setMargin(true);
        mainLayout.setWidth("100%");
        setCompositionRoot(mainLayout);

    }

    private TextField createAmountOfStockField() {
        TextField amountOfStock = new TextField("Amount of Stock");
        amountOfStock.setNullRepresentation("");
        amountOfStock.setRequired(true);
        amountOfStock.setRequiredError("Please provide an amount of stock.");
        return amountOfStock;
    }

    private void bindFields() {
        item = new Item();
        fieldGroup = BeanFieldGroup.bindFieldsBuffered(this.item, this);
    }

    private TextField createNameField() {
        TextField name = new TextField("Name");
        name.setNullRepresentation("");
        name.setRequired(true);
        name.setRequiredError("Please provide a name.");
        return name;
    }

    private TextArea createDescriptionField() {
        TextArea description = new TextArea("Description");
        description.setNullRepresentation("");
        description.setRequired(true);
        description.setRequiredError("Please provide a description.");
        return description;
    }

    private TextField createPriceField() {
        TextField price = new TextField();
        price.setNullRepresentation("0.00");
        price.setRequired(true);
        price.setRequiredError("Please provide a price.");
        return price;
    }
}
