package org.github.bobobot.access;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import org.github.bobobot.entities.User;

import java.util.function.Consumer;

public class PermissionHandler {

	public static void setCurrentUserToNotLoggedIn() {
		VaadinUser currentUser = new VaadinUser();
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute("currentUser", currentUser);
	}

	public static void setCurrentUser(User user) {
		VaadinUser currentUser = new VaadinUser(user.getId(), user.isAdmin() ? UserRole.ADMIN : UserRole.USER);
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute("currentUser", currentUser);
	}

	public static VaadinUser getCurrentUser() {
		return (VaadinUser) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("currentUser");
	}

	/**
	 * Restricts the component so that it only gets shown to a user with a specific ID
	 *
	 * @param component The component to be restricted
	 * @param userId    The user's id
	 * @return The component
	 */
	public static Component restrictComponentToLoggedInUser(Component component, Long userId) {
		return restrictComponentToLoggedInUser(component, userId, false);
	}

	/**
	 * Restricts the component so that it only gets shown to a user with a specific ID
	 *
	 * @param component   The component to be restricted
	 * @param userId      The user's id
	 * @param adminAccess Whether the user id should be ignored if the current user is an admin
	 * @return The component
	 */
	public static Component restrictComponentToLoggedInUser(Component component, Long userId, boolean adminAccess) {
		return restrictComponentToLoggedInUser(component, userId, adminAccess, component::setVisible);
	}


	/**
	 * Applies the provided function to the component according to the provided user id.
	 * The function should only run if the currently logged in user's id matches the provided one,
	 * or if the current user is an admin and the adminAccess flag is set to true.
	 *
	 * @param component   The component to be restricted
	 * @param userId      The user's id
	 * @param adminAccess Whether the user id should be ignored if the current user is an admin
	 * @param func        The function to be applied to the component
	 * @return The component
	 */
	public static Component restrictComponentToLoggedInUser(Component component, Long userId, boolean adminAccess, Consumer<Boolean> func) {
		VaadinUser user = getCurrentUser();
		func.accept(false);
		if (user.getRole() != UserRole.NOT_LOGGED_IN && user.getId().equals(userId)) func.accept(true);
		if (user.getRole() == UserRole.ADMIN && adminAccess) func.accept(true);
		return component;
	}

	public static Component restrictComponentToRoles(Component component, Consumer<Boolean> func, UserRole... roles) {
		VaadinUser user = getCurrentUser();
		func.accept(false);
		for (UserRole role : roles) {
			if (user.getRole() == role) {
				func.accept(true);
				break;
			}
		}
		return component;
	}

	public static Component restrictComponentToLoggedInUsers(Component component, Consumer<Boolean> func) {
		return restrictComponentToRoles(component, func, UserRole.USER, UserRole.ADMIN);
	}


	/**
	 * Restricts the component so that it only gets shown to admins
	 *
	 * @param component The component to be restricted
	 * @return The component
	 */
	public static Component restrictComponentToAdmins(Component component, Consumer<Boolean> func) {
		return restrictComponentToRoles(component, func, UserRole.ADMIN);
	}
}
