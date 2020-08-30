package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.PopupView;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.services.IUserService;
import org.github.bobobot.ui.views.LoginView;
import org.github.bobobot.ui.views.MainView;
import org.github.bobobot.ui.views.RegisterView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Slf4j
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NavbarLayout extends CssLayout implements View {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    IUserService userService;

    @PostConstruct
    void init() {
        log.info("rendered navbar");
        render();
    }

    public void render() {
        removeAllComponents();

        Image logo = new Image(null, new ThemeResource("images/logo.png"));
        logo.setStyleName("logo");
        logo.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(MainView.name));
        addComponent(logo);
        Button loginButton = new Button("Login", event -> getUI().getNavigator().navigateTo(LoginView.name));
        loginButton.addStyleName("btn btn-primary");
        Button registerButton = new Button("Register", event -> getUI().getNavigator().navigateTo(RegisterView.name));
        registerButton.addStyleName("btn btn-primary");
        registerButton.removeStyleName("v-button");
        Button logoutButton = new Button("Log out", event -> {
            PermissionHandler.setCurrentUserToNotLoggedIn();
            Page.getCurrent().reload();
        });
        logoutButton.addStyleName("btn btn-primary");
        addComponent(loginButton);
        addComponent(registerButton);
        addComponent(logoutButton);

        //TODO: debug shit, töröld
        Button adminLogin = new Button("Login as admin", event -> {
            userService.login("admin@chan.com", "admin");
            init();
        });
        addComponent(adminLogin);
        PermissionHandler.restrictComponentToLoggedOutUsers(adminLogin, adminLogin::setVisible);
        //TODO: end of TODO

        //TODO: actual szép gomb a notificationökre
        if (PermissionHandler.isLoggedIn()) {
            NotificationLayout notifLayout = appContext.getBean(NotificationLayout.class);
            PopupView notificationPopUp = new PopupView("Notifications", notifLayout);
            addComponent(notificationPopUp);
            PermissionHandler.restrictComponentToLoggedInUsers(notificationPopUp, notificationPopUp::setVisible);
        }

        PermissionHandler.restrictComponentToLoggedOutUsers(loginButton, loginButton::setVisible);
        PermissionHandler.restrictComponentToLoggedOutUsers(registerButton, registerButton::setVisible);
        PermissionHandler.restrictComponentToLoggedInUsers(logoutButton, logoutButton::setVisible);
    }
}
