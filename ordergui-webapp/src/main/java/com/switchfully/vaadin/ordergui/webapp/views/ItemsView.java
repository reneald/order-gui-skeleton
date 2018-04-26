package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemsView extends CustomComponent implements View {

    private ItemResource itemResource;
    private VerticalLayout mainLayout = new VerticalLayout();

    @Autowired
    public ItemsView(ItemResource itemResource) {
        this.itemResource = itemResource;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setWidth("100%");

        TopMenu topMenu = new TopMenu();
        BeanItemContainer<Item> itemContainer = new BeanItemContainer<>(Item.class, itemResource.getItems());
        Label title = new Label("Items");
        title.setStyleName(ValoTheme.LABEL_H1);
//        title.setWidth("200");

        TextField filterField = new TextField();
        filterField.setInputPrompt("Filter");
        Button filterButton = new Button("Filter");
        filterButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        filterButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        Button newItemButton = new Button("New Item");
        newItemButton.addClickListener(event1 -> getUI().getNavigator().navigateTo("items/new"));
        newItemButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        HorizontalLayout filterLayout = new HorizontalLayout(filterField, filterButton, newItemButton);

        HorizontalLayout header = new HorizontalLayout(title, filterLayout);
        header.setWidth("100%");
        header.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        header.setComponentAlignment(filterLayout, Alignment.MIDDLE_RIGHT);

        Grid itemGrid = new Grid();
        setGridData(itemContainer, itemGrid);

        filterButton.addClickListener(event1 -> {
            itemContainer.removeAllContainerFilters();
            if (!filterField.getValue().isEmpty()) {
                itemContainer.addContainerFilter(
                        new SimpleStringFilter("name", filterField.getValue(), true, false)
                );
            }
        });

        itemGrid.setWidth("100%");
        itemGrid.setColumns("name","description", "price", "amountOfStock");
        mainLayout.setMargin(true);
        mainLayout.addComponents(topMenu, header, itemGrid);
        setCompositionRoot(mainLayout);
    }

    private void setGridData(BeanItemContainer<Item> itemContainer, Grid itemGrid) {
        itemGrid.setContainerDataSource(itemContainer);
    }
}
