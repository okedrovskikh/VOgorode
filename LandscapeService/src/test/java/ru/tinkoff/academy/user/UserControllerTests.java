package ru.tinkoff.academy.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JavaType;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests extends AbstractIntegrationTest {
    private final String users = "/users";
    private final String usersById = "/users/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void set() {

    }

    @Test
    public void testSaveCorrectUser() throws Exception {
        User expectedUser = User.builder()
                .type(UserType.LANDSCAPE)
                .login("login")
                .email("email@email.com")
                .telephone("89999999999")
                .latitude(100.0)
                .longitude(100.0)
                .build();

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setType(UserType.LANDSCAPE);
        userCreateDto.setLogin("login");
        userCreateDto.setEmail("email@email.com");
        userCreateDto.setTelephone("89999999999");
        userCreateDto.setLatitude("100.0");
        userCreateDto.setLongitude("100.0");

        MvcResult userResponse = this.mockMvc.perform(MockMvcRequestBuilders.post(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        User actualUser = this.objectMapper.readValue(userResponse.getResponse().getContentAsString(), User.class);

        this.assertWithoutId(expectedUser, actualUser);
    }

    @Test
    public void testSaveIncorrectUser() throws Exception {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setLogin("login");
        userCreateDto.setTelephone("89999999999");
        userCreateDto.setLatitude("100.0");
        userCreateDto.setLongitude("100.0");

        this.mockMvc.perform(MockMvcRequestBuilders.post(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testSaveCorrectWithNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        User expectedUser = User.builder()
                .id(id)
                .type(UserType.LANDSCAPE)
                .login("login")
                .email("email@email.com")
                .telephone("89999999999")
                .latitude(800.9)
                .longitude(809.7)
                .build();

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setType(UserType.LANDSCAPE);
        userCreateDto.setLogin("login");
        userCreateDto.setEmail("email@email.com");
        userCreateDto.setTelephone("89999999999");
        userCreateDto.setLatitude("800.9");
        userCreateDto.setLongitude("809.7");

        MvcResult userResponse = this.mockMvc.perform(MockMvcRequestBuilders.post(String.format(this.usersById, id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        User actualUser = this.objectMapper.readValue(userResponse.getResponse().getContentAsString(), User.class);

        this.assertWithId(expectedUser, actualUser);
    }

    @Test
    public void testSaveCorrectUserWithExistId() throws Exception {
        String id = "3e76f3f6-9f6e-41fd-8e0f-b07c2166152d";

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setType(UserType.LANDSCAPE);
        userCreateDto.setLogin("login");
        userCreateDto.setEmail("email@email.com");
        userCreateDto.setTelephone("89999999999");
        userCreateDto.setLatitude("800.9");
        userCreateDto.setLongitude("809.7");

        this.mockMvc.perform(MockMvcRequestBuilders.post(String.format(this.usersById, id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testSaveIncorrectUserWithNotExistId() throws Exception {
        String id = UUID.randomUUID().toString();

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setLogin("login");
        userCreateDto.setTelephone("89999999999");
        userCreateDto.setLatitude("100.0");
        userCreateDto.setLongitude("100.0");

        this.mockMvc.perform(MockMvcRequestBuilders.post(String.format(this.usersById, id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testSaveIncorrectUserWithExistId() throws Exception {
        String id = "3e76f3f6-9f6e-41fd-8e0f-b07c2166152d";

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setLogin("login");
        userCreateDto.setTelephone("89999999999");
        userCreateDto.setLatitude("100.0");
        userCreateDto.setLongitude("100.0");

        this.mockMvc.perform(MockMvcRequestBuilders.post(String.format(this.usersById, id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testGetByExistId() throws Exception {
        UUID id = UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-b07c2166152d");

        User expectedUser = User.builder()
                .id(id)
                .type(UserType.LANDSCAPE)
                .login("login")
                .email("email@email.com")
                .telephone("89999999999")
                .creationDate(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                .updateDate(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                .latitude(450.0)
                .longitude(540.0)
                .build();

        MvcResult userResponse = this.mockMvc.perform(MockMvcRequestBuilders.get(String.format(this.usersById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        User actualUser = this.objectMapper.readValue(userResponse.getResponse().getContentAsString(), User.class);

        this.assertWithId(expectedUser, actualUser);
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        this.mockMvc.perform(MockMvcRequestBuilders.get(String.format(this.usersById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetAll() throws Exception {
        int expectedSize = 3;

        MvcResult usersResponse = this.mockMvc.perform(MockMvcRequestBuilders.get(this.users + "/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<User> actualUsers = this.objectMapper.readValue(usersResponse.getResponse().getContentAsString(), this.listUserType());

        Assertions.assertTrue(expectedSize <= actualUsers.size());
    }

    @Test
    public void testUpdateCorrectUserWithExistId() throws Exception {
        UUID id = UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-a07c2166152d");

        User expectedUser = User.builder()
                .id(id)
                .type(UserType.LANDSCAPE)
                .login("new-login")
                .email("email@email.com")
                .telephone("89999999998")
                .creationDate(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                .latitude(890.0)
                .longitude(900.9)
                .build();

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setId(id);
        userUpdateDto.setType(UserType.LANDSCAPE);
        userUpdateDto.setLogin("new-login");
        userUpdateDto.setEmail("email@email.com");
        userUpdateDto.setTelephone("89999999998");
        userUpdateDto.setLatitude("890.0");
        userUpdateDto.setLongitude("900.9");

        MvcResult userResponse = this.mockMvc.perform(MockMvcRequestBuilders.put(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        User actualUser = this.objectMapper.readValue(userResponse.getResponse().getContentAsString(), User.class);

        this.assertWithId(expectedUser, actualUser);
    }

    @Test
    public void testUpdateCorrectUserWithNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setId(id);
        userUpdateDto.setType(UserType.LANDSCAPE);
        userUpdateDto.setLogin("new-login");
        userUpdateDto.setEmail("email@email.com");
        userUpdateDto.setTelephone("89999999998");
        userUpdateDto.setLatitude("890.0");
        userUpdateDto.setLongitude("900.9");

        this.mockMvc.perform(MockMvcRequestBuilders.put(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testUpdateIncorrectUserWithNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setId(id);
        userUpdateDto.setType(UserType.LANDSCAPE);
        userUpdateDto.setLogin("new-login");
        userUpdateDto.setEmail("email@email.com");

        this.mockMvc.perform(MockMvcRequestBuilders.put(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testUpdateIncorrectUserWithExistId() throws Exception {
        UUID id = UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-a07c2166152d");

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setId(id);
        userUpdateDto.setType(UserType.LANDSCAPE);
        userUpdateDto.setLogin("new-login");
        userUpdateDto.setTelephone("89999999998");
        userUpdateDto.setLatitude("890.0");
        userUpdateDto.setLongitude("900.9");

        this.mockMvc.perform(MockMvcRequestBuilders.put(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        UUID id = UUID.fromString("5e76f3f6-9f6e-41fd-8e0f-a07c2166152d");

        Assertions.assertTrue(this.userRepository.existsById(id));

        this.mockMvc.perform(MockMvcRequestBuilders.delete(String.format(this.usersById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(this.userRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        Assertions.assertFalse(this.userRepository.existsById(id));

        this.mockMvc.perform(MockMvcRequestBuilders.delete(String.format(this.usersById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Assertions.assertFalse(this.userRepository.existsById(id));
    }

    private void assertWithId(User expectedUser, User actualUser) {
        Assertions.assertEquals(expectedUser.getId(), actualUser.getId());
        Assertions.assertEquals(expectedUser.getType(), actualUser.getType());
        Assertions.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getTelephone(), actualUser.getTelephone());
        Assertions.assertEquals(expectedUser.getLatitude(), actualUser.getLatitude());
        Assertions.assertEquals(expectedUser.getLongitude(), actualUser.getLongitude());
    }

    private void assertWithoutId(User expectedUser, User actualUser) {
        Assertions.assertEquals(expectedUser.getType(), actualUser.getType());
        Assertions.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getTelephone(), actualUser.getTelephone());
        Assertions.assertEquals(expectedUser.getLatitude(), actualUser.getLatitude());
        Assertions.assertEquals(expectedUser.getLongitude(), actualUser.getLongitude());
    }

    private JavaType listUserType() {
        return this.objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class);
    }
}
