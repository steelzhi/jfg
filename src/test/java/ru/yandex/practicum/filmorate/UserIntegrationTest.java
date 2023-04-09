package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserIntegrationTest {

    private final UserDbStorage userStorage;

    @Test
    void FindUserById() {
        User userTest = new User(1, "john", null, "john.doe@hotmail.com",
                LocalDate.parse("2000-11-30"), null);
        userStorage.create(userTest);
        userTest = userStorage.getUserById(1);

        assertThat(userTest).hasFieldOrPropertyWithValue("id", 1);
    }

    @Test
    void findAll() {
        User userTest1 = new User(1, "john", null, "john.doe@hotmail.com",
                LocalDate.parse("2000-11-30"), null);
        User userTest2 = new User(2, "jane", null, "jane.doe@hotmail.com",
                LocalDate.parse("2001-10-29"), null);

        userStorage.create(userTest1);
        userStorage.create(userTest2);
        List<User> users = userStorage.findAll();

        assertThat(users).contains(userTest1, userTest2);
    }

    @Test
    void update() {
        User userTest1 = new User(1, "john", null, "john.doe@hotmail.com",
                LocalDate.parse("2000-11-30"), null);
        userTest1.setBirthday(LocalDate.parse("2001-10-29"));
        userTest1.setName("jane");
        userTest1.setLogin("jane");
        userTest1.setEmail("jane@mail.com");
        userStorage.update(userTest1);

        List<User> users = userStorage.findAll();

        assertThat(users).contains(userTest1);
    }

    @Test
    void addFriend() {
        User userTest1 = new User(1, "john", null, "john.doe@hotmail.com",
                LocalDate.parse("2000-11-30"), null);
        User userTest2 = new User(2, "jane", null, "jane.doe@hotmail.com",
                LocalDate.parse("2001-10-29"), null);

        userStorage.create(userTest1);
        userStorage.create(userTest2);
        userStorage.addFriend(userTest1.getId(), userTest2.getId());

        List<User> friends = userStorage.getFriends(userTest1.getId());

        assertThat(friends).hasSize(1)
                .containsOnly(userTest2);
    }

    @Test
    void deleteFriend() {
        User userTest1 = new User(1, "john", null, "john.doe@hotmail.com",
                LocalDate.parse("2000-11-30"), null);
        User userTest2 = new User(2, "jane", null, "jane.doe@hotmail.com",
                LocalDate.parse("2001-10-29"), null);

        userStorage.create(userTest1);
        userStorage.create(userTest2);

        userStorage.addFriend(userTest1.getId(), userTest2.getId());
        List<User> friends = userStorage.getFriends(userTest1.getId());
        assertThat(friends).hasSize(1)
                .containsOnly(userTest2);

        userStorage.deleteFriend(userTest1.getId(), userTest2.getId());
        friends = userStorage.getFriends(userTest1.getId());
        assertThat(friends).doesNotContain(userTest2);
    }

    @Test
    void getCommonFriends() {
        User userTest1 = new User(1, "john", null, "john.doe@hotmail.com",
                LocalDate.parse("2000-11-30"), null);
        User userTest2 = new User(2, "jane", null, "jane.doe@hotmail.com",
                LocalDate.parse("2001-10-29"), null);
        User friend = new User(3, "friend", null, "jane.friend@hotmail.com",
                LocalDate.parse("2002-09-19"), null);

        userStorage.create(userTest1);
        userStorage.create(userTest2);
        userStorage.create(friend);

        userStorage.addFriend(userTest1.getId(), friend.getId());
        userStorage.addFriend(userTest2.getId(), friend.getId());

        List<User> friends = userStorage.getCommonFriends(userTest1.getId(), userTest2.getId());
        assertThat(friends).hasSize(1)
                .containsOnly(friend);

    }
}
