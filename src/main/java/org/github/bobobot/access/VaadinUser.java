package org.github.bobobot.access;

import lombok.Data;

@Data
public class VaadinUser {
	private Long id;
	private UserRole role;

	public VaadinUser() {
		this.role = UserRole.NOT_LOGGED_IN;
	}

	public VaadinUser(Long id, UserRole role) {
		this.id = id;
		this.role = role;
	}
}
