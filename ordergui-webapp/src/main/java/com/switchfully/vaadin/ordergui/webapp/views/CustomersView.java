package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.Address;
import com.switchfully.vaadin.ordergui.interfaces.Email;
import com.switchfully.vaadin.ordergui.interfaces.PhoneNumber;
import com.switchfully.vaadin.ordergui.interfaces.customers.Customer;
import com.switchfully.vaadin.ordergui.interfaces.customers.CustomerResource;
import com.switchfully.vaadin.ordergui.webapp.CustomerEditWindow;
import com.vaadin.data.Item;
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

import java.util.Objects;

public class CustomersView extends CustomComponent implements View {

    private CustomerResource customerResource;
    private VerticalLayout mainLayout = new VerticalLayout();
    private Grid customerGrid;
    private BeanItemContainer<Customer> customerContainer;

    @Autowired
    public CustomersView(CustomerResource customerResource) {
        this.customerResource = customerResource;

        setWidth("100%");
        TopMenu topMenu = new TopMenu();

        Label title = new Label("Customers");
        title.setStyleName(ValoTheme.LABEL_H1);

        TextField filterField = new TextField();
        filterField.setInputPrompt("Filter");
        Button filterButton = new Button("Filter");
        filterButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        filterButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        HorizontalLayout filterLayout = new HorizontalLayout(filterField, filterButton);

        Button newCustomerButton = new Button("New Customer");
        filterButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        newCustomerButton.addClickListener(event1 -> addEditWindow(new String(""), customerResource));

        HorizontalLayout header = new HorizontalLayout(title, filterLayout, newCustomerButton);
        header.setWidth("100%");
        header.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        header.setComponentAlignment(filterLayout, Alignment.MIDDLE_CENTER);
        header.setComponentAlignment(newCustomerButton, Alignment.MIDDLE_RIGHT);



        customerGrid = new Grid();
        setGridData();
        filterButton.addClickListener(event1 -> {
            customerContainer.removeAllContainerFilters();
            if (!filterField.getValue().isEmpty()) {
                customerContainer.addContainerFilter(
                        new SimpleStringFilter("name", filterField.getValue(), true, false)
                );
            }
        });

        customerGrid.setWidth("100%");
        customerGrid.getColumn("edit").setRenderer(new ButtonRenderer(event ->
                /*getUI().getNavigator().navigateTo("customers"edit" + "/" + ((Item)event.getItemId()).getId()*/
                addEditWindow(((Customer) event.getItemId()).getId(), customerResource)
        ));
        customerGrid.setColumns("firstName","lastName", "streetAddress", "phoneNumber", "email.complete", "edit");
        customerGrid.getColumn("email.complete").setHeaderCaption("Email");
        customerGrid.getColumn("streetAddress").setHeaderCaption("Address");
        customerGrid.getColumn("edit").setMaximumWidth(95);
        mainLayout.setMargin(true);
        mainLayout.addComponents(topMenu, header, customerGrid);
        setCompositionRoot(mainLayout);

    }

    private void addEditWindow(String id, CustomerResource customerResource) {
        Customer customer;
        if(!id.equals("")){
            customer = customerResource.getItemById(id);
        }
        else {
            customer = new Customer();
            customer.setAddress(new Address());
            customer.setEmail(new Email());
            customer.setPhoneNumber(new PhoneNumber());
        }

        CustomerEditWindow editWindow = new CustomerEditWindow(customer, customerResource);

        getUI().addWindow(editWindow);
        editWindow.center();
        editWindow.setModal(true);
        editWindow.addCloseListener(event -> setGridData());
    }

    private void setGridData() {
        customerContainer = new BeanItemContainer<>(Customer.class, customerResource.getCustomers());
        customerContainer.addNestedContainerProperty("email.complete");

        GeneratedPropertyContainer generatedPropertyContainer = new GeneratedPropertyContainer(customerContainer);
        generateEditColumn(generatedPropertyContainer);
        generateStreetAddressColumn(generatedPropertyContainer);
        generatePhoneNumberColumn(generatedPropertyContainer);

        customerGrid.setContainerDataSource(generatedPropertyContainer);
    }

    private void generatePhoneNumberColumn(GeneratedPropertyContainer generatedPropertyContainer) {
        generatedPropertyContainer.addGeneratedProperty("phoneNumber", new PropertyValueGenerator<String>() {
            @Override
            public String getValue(Item item, Object itemId, Object propertyId) {
                PhoneNumber phoneNumber = (PhoneNumber) item.getItemProperty("phoneNumber").getValue();
                return phoneNumber.getCountryCallingCode().concat(" ")
                        .concat(phoneNumber.getNumber());
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
    }

    private void generateStreetAddressColumn(GeneratedPropertyContainer generatedPropertyContainer) {
        generatedPropertyContainer.addGeneratedProperty("streetAddress", new PropertyValueGenerator<String>() {
            @Override
            public String getValue(Item item, Object itemId, Object propertyId) {
                Address address = (Address) item.getItemProperty("address").getValue();
                return address.getHouseNumber().concat(", ")
                        .concat(address.getStreetName()).concat(", ")
                        .concat(address.getPostalCode()).concat(", ")
                        .concat(address.getCountry());
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
    }

    private void generateEditColumn(GeneratedPropertyContainer generatedPropertyContainer) {
        generatedPropertyContainer.addGeneratedProperty("edit", new PropertyValueGenerator<String>() {


            @Override
            public String getValue(Item item, Object itemId, Object propertyId) {
                return "Edit";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.setGridData();
    }
}
