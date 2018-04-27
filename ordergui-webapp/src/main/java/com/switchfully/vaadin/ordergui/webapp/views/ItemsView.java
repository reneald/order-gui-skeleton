package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemsView extends CustomComponent implements View {

    private ItemResource itemResource;
    private VerticalLayout mainLayout = new VerticalLayout();
    private Grid itemGrid;
    private BeanItemContainer<Item> itemContainer;

    @Autowired
    public ItemsView(ItemResource itemResource) {
        this.itemResource = itemResource;

        setWidth("100%");
        TopMenu topMenu = new TopMenu();

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

        itemGrid = new Grid();
        setGridData();


        filterButton.addClickListener(event1 -> {
            itemContainer.removeAllContainerFilters();
            if (!filterField.getValue().isEmpty()) {
                itemContainer.addContainerFilter(
                        new SimpleStringFilter("name", filterField.getValue(), true, false)
                );
            }
        });

        itemGrid.setWidth("100%");
        itemGrid.getColumn("edit").setRenderer(new ButtonRenderer(event ->
            getUI().getNavigator().navigateTo("item/edit" + event.getItemId())));
        itemGrid.setColumns("name","description","price","amountOfStock", "edit");
        itemGrid.getColumn("edit").setMaximumWidth(95);
        mainLayout.setMargin(true);
        mainLayout.addComponents(topMenu, header, itemGrid);
        setCompositionRoot(mainLayout);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setGridData();
    }

    private void setGridData() {
        itemContainer = new BeanItemContainer<>(Item.class, itemResource.getItems());
        GeneratedPropertyContainer generatedPropertyContainer = new GeneratedPropertyContainer(itemContainer);
        generatedPropertyContainer.addGeneratedProperty("edit", new PropertyValueGenerator<String>() {


            @Override
            public String getValue(com.vaadin.data.Item item, Object itemId, Object propertyId) {
                return "Edit";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
        itemGrid.setContainerDataSource(generatedPropertyContainer);
    }
}
