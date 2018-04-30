package com.switchfully.vaadin.ordergui.webapp;


import com.switchfully.vaadin.ordergui.interfaces.customers.Customer;
import com.switchfully.vaadin.ordergui.interfaces.customers.CustomerResource;
import com.switchfully.vaadin.ordergui.webapp.views.TopMenu;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class CustomerEditWindow extends Window {

    private VerticalLayout mainLayout = new VerticalLayout();
    private TopMenu topMenu = new TopMenu();
    private BeanFieldGroup<Customer> fieldGroup;
    private Customer customer;
    private CustomerResource resource;

    private HorizontalLayout buttons;
    private Button updateButton = new Button("Update");
    private Button createButton = new Button("Create");
    private Button cancelButton = new Button("Cancel");

    public CustomerEditWindow(Customer customer, CustomerResource resource) {
        this.resource = resource;
        this.customer = customer;

        fieldGroup = new BeanFieldGroup<>(Customer.class);
        fieldGroup.setItemDataSource(customer);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        VerticalLayout nameLayout = createNameLayout();

        VerticalLayout addressLayout = createAddressLayout();

        VerticalLayout emailLayout = createEmailLayout();

        VerticalLayout phoneNumberLayout = createPhoneNumberLayout();

        buttons = createButtons();

        mainLayout.addComponents(nameLayout, addressLayout, emailLayout, phoneNumberLayout, buttons);

        setContent(mainLayout);
    }

    private HorizontalLayout createButtons() {
        HorizontalLayout layout = new HorizontalLayout();
        if (customer.getId() == null) {
            createButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
            createButton.addClickListener(event1 -> {
                try {
                    fieldGroup.commit();
                    customer.getEmail().setComplete(customer.getEmail().getLocalPart().concat("@").concat(customer.getEmail().getDomain()));
                    resource.save(customer);
                    close();

                } catch (FieldGroup.CommitException ex) {
                    Notification.show(ex.getMessage());
                }

            });
            layout.addComponent(createButton);
        } else {
            updateButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
            updateButton.addClickListener(event -> {
                try {
                    fieldGroup.commit();
                    customer.getEmail().setComplete(customer.getEmail().getLocalPart().concat("@").concat(customer.getEmail().getDomain()));
                    resource.update(customer);
                    close();
                } catch (FieldGroup.CommitException ex) {
                    Notification.show(ex.getMessage());
                }
            });
            layout.addComponent(updateButton);
        }

        layout.addComponent(cancelButton);
        cancelButton.addClickListener(event -> close());
        layout.setSpacing(true);
        return layout;
    }

    private VerticalLayout createPhoneNumberLayout() {
        Label phoneNumber = new Label("Phone Number");
        phoneNumber.setStyleName(ValoTheme.LABEL_H2);
        HorizontalLayout phoneNumberFields = new HorizontalLayout();
        phoneNumberFields.addComponents(
                fieldGroup.buildAndBind("Country Code", "phoneNumber.countryCallingCode"),
                fieldGroup.buildAndBind("Number", "phoneNumber.number")
        );
        phoneNumberFields.setSpacing(true);

        return new VerticalLayout(phoneNumber, phoneNumberFields);
    }

    private VerticalLayout createEmailLayout() {
        Label email = new Label("Email");
        email.setStyleName(ValoTheme.LABEL_H2);
        Label atSign = new Label();
        atSign.setIcon(FontAwesome.AT);
        HorizontalLayout emailFields = new HorizontalLayout();
        emailFields.addComponents(
                fieldGroup.buildAndBind("Local Part", "email.localPart"),
                atSign,
                fieldGroup.buildAndBind("Domain", "email.domain")
        );
        emailFields.setSpacing(true);
        emailFields.setComponentAlignment(atSign, Alignment.BOTTOM_CENTER);
        return new VerticalLayout(email, emailFields);
    }

    private VerticalLayout createAddressLayout() {
        Label address = new Label("Address");
        address.setStyleName(ValoTheme.LABEL_H2);
        HorizontalLayout streetAndNumberFields = new HorizontalLayout();
        streetAndNumberFields.addComponents(
                fieldGroup.buildAndBind("House Number", "address.houseNumber"),
                fieldGroup.buildAndBind("Street Name", "address.streetName")
        );
        streetAndNumberFields.setSpacing(true);
        HorizontalLayout postalCodeAndCountryFields = new HorizontalLayout();
        postalCodeAndCountryFields.addComponents(
                fieldGroup.buildAndBind("Postal Code", "address.postalCode"),
                fieldGroup.buildAndBind("Country", "address.country")
        );
        postalCodeAndCountryFields.setSpacing(true);

        return new VerticalLayout(address, streetAndNumberFields, postalCodeAndCountryFields);
    }

    private VerticalLayout createNameLayout() {
        Label name = new Label("Name");
        name.setStyleName(ValoTheme.LABEL_H2);
        HorizontalLayout nameFields = new HorizontalLayout();
        nameFields.addComponents(
                fieldGroup.buildAndBind("First Name", "firstName"),
                fieldGroup.buildAndBind("Last Name", "lastName")
        );
        nameFields.setSpacing(true);

        return new VerticalLayout(name, nameFields);
    }
}
