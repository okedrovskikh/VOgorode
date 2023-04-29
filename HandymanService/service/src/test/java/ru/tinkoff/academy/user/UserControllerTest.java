package ru.tinkoff.academy.user;

import org.junit.jupiter.api.Assertions;
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
import ru.tinkoff.academy.work.WorkEnum;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractIntegrationTest {
    private final String users = "/users";
    private final String usersById = "/users/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveCorrectUser() throws Exception {
        User expectedUser = User.builder()
                .id(5L)
                .name("name5")
                .surname("surname5")
                .skills(new WorkEnum[]{WorkEnum.plant})
                .email("email5@email.com")
                .telephone("telephone")
                .accounts(List.of())
                .photo(new Byte[]{})
                .build();

        UserCreateDto request = new UserCreateDto();
        request.setName("name5");
        request.setSurname("surname5");
        request.setSkills(new WorkEnum[]{WorkEnum.plant});
        request.setEmail("email5@email.com");
        request.setTelephone("telephone");
        request.setPhoto(new Byte[]{});

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(users)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        User actualUser = objectMapper.readValue(response.getResponse().getContentAsString(), User.class);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testSaveIncorrectUser() throws Exception {
        UserCreateDto request = new UserCreateDto();
        request.setName("name5");
        request.setSurname("surname5");
        request.setSkills(new WorkEnum[]{WorkEnum.plant});
        request.setEmail("email5@email.com");

        mockMvc.perform(MockMvcRequestBuilders.post(users)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testGetByExistId() throws Exception {
        Long id = 2L;
        User expectedUser = User.builder()
                .id(id)
                .name("user2")
                .surname("surname2")
                .skills(new WorkEnum[]{WorkEnum.water})
                .email("email2@email.com")
                .telephone("telephone")
                .photo(new Byte[]{})
                .build();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(usersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        User actualUser = objectMapper.readValue(response.getResponse().getContentAsString(), User.class);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get(String.format(usersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> expectedUsers = List.of(
                User.builder()
                        .id(1L)
                        .name("user1")
                        .surname("surname1")
                        .skills(new WorkEnum[]{WorkEnum.shovel})
                        .email("email1@email.com")
                        .telephone("telephone")
                        .photo(new Byte[]{})
                        .build(),
                User.builder()
                        .id(2L)
                        .name("user2")
                        .surname("surname2")
                        .skills(new WorkEnum[]{WorkEnum.water})
                        .email("email2@email.com")
                        .telephone("telephone")
                        .photo(new Byte[]{})
                        .build()
        );

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(usersById, "all"))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<User> actualUser = objectMapper.readValue(response.getResponse().getContentAsString(), listUserType());

        Assertions.assertTrue(actualUser.containsAll(expectedUsers));
    }

    @Test
    public void testUpdateCorrectUser() throws Exception {
        User expectedUser = User.builder()
                .id(3L)
                .name("user3")
                .surname("surname33")
                .skills(new WorkEnum[]{WorkEnum.plant, WorkEnum.shovel})
                .email("email3@email.com")
                .telephone("telephone")
                .photo(new Byte[]{})
                .build();

        UserUpdateDto request = new UserUpdateDto();
        request.setId(3L);
        request.setName("user3");
        request.setSurname("surname33");
        request.setSkills(new WorkEnum[]{WorkEnum.plant, WorkEnum.shovel});
        request.setEmail("email3@email.com");
        request.setTelephone("telephone");
        request.setPhoto(new Byte[]{});

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(users)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        User actualUser = objectMapper.readValue(response.getResponse().getContentAsString(), User.class);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testUpdateIncorrectUser() throws Exception {
        UserUpdateDto request = new UserUpdateDto();
        request.setId(3L);
        request.setName("user3");
        request.setSurname("surname33");
        request.setSkills(new WorkEnum[]{WorkEnum.plant, WorkEnum.shovel});
        request.setEmail("email3@email.com");

        mockMvc.perform(MockMvcRequestBuilders.put(users)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        Long id = 4L;

        Assertions.assertTrue(userRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(usersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(userRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        Long id = 1000L;

        Assertions.assertFalse(userRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(usersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(userRepository.existsById(id));
    }

    private JavaType listUserType() {
        return objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, User.class);
    }
}
