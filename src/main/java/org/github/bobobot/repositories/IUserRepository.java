package org.github.bobobot.repositories;

import org.github.bobobot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {

	@Override
	User save(User user);

	@Override
	Optional<User> findById(Integer integer);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	@Override
	List<User> findAll();

	@Override
	void delete(User user);
}
