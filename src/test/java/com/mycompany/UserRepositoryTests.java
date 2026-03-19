package com.mycompany;

import com.mycompany.user.User;
import com.mycompany.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    private User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setFirstName("Test");
        user.setLastName("User");
        return repo.save(user);
    }

    @Test
    public void testAddNew() {
        User savedUser = createUser("user1@test.com");

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        createUser("user2@test.com");

        Iterable<User> users = repo.findAll();

        Assertions.assertThat(users).isNotEmpty();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        User user = createUser("user3@test.com");

        user.setPassword("updatedPassword");
        repo.save(user);

        User updatedUser = repo.findById(user.getId()).orElseThrow();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("updatedPassword");
    }

    @Test
    public void testGet() {
        User user = createUser("user4@test.com");

        Optional<User> optionalUser = repo.findById(user.getId());

        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        User user = createUser("user5@test.com");

        repo.deleteById(user.getId());

        Optional<User> optionalUser = repo.findById(user.getId());

        Assertions.assertThat(optionalUser).isNotPresent();
    }
}