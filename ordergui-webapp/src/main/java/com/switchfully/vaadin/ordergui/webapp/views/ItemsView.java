package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemsView extends VerticalLayout implements View {

    private ItemResource itemResource;

    @Autowired
    public ItemsView(ItemResource itemResource) {
        this.itemResource = itemResource;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        BeanItemContainer<Item> itemContainer = new BeanItemContainer<>(Item.class, itemResource.getItems());
        Grid itemGrid = new Grid("Items", itemContainer);
        itemGrid.setColumns("name","description");
        addComponent(itemGrid);
    }
}
