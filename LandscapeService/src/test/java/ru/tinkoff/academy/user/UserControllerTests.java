package ru.tinkoff.academy.user;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.user.dto.UserCreateDto;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveCorrectUser() throws Exception {
        User expectedUser = User.builder()
                .build();

        UserCreateDto userCreateDto = new UserCreateDto();

        MvcResult userResponse = this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(userCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        User actualUser = this.objectMapper.readValue(userResponse.getResponse().getContentAsString(), User.class);

        Assertions.assertEquals(expectedUser.getType(), actualUser.getType());
        Assertions.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getTelephone(), actualUser.getTelephone());
        Assertions.assertEquals(expectedUser.getLatitude(), actualUser.getLatitude());
        Assertions.assertEquals(expectedUser.getLongitude(), actualUser.getLongitude());
    }

    @Test
    public void testSaveIncorrectUser() throws Exception {

    }

    @Test
    public void testSaveCorrectWithNotExistId() throws Exception {

    }

    @Test
    public void testSaveCorrectUserWithExistId() throws Exception {

    }

    @Test
    public void testSaveIncorrectUserWithNotExistId() throws Exception {

    }

    @Test
    public void testSaveIncorrectUserWithExistId() throws Exception {

    }

    @Test
    public void testGetByExistId() throws Exception {

    }

    @Test
    public void testGetByNotExistId() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {

    }

    @Test
    public void testUpdateCorrectUserWithNotExistId() throws Exception {

    }

    @Test
    public void testUpdateCorrectUserWithExistId() throws Exception {

    }

    @Test
    public void testUpdateIncorrectUserWithNotExistId() throws Exception {

    }

    @Test
    public void testUpdateIncorrectUserWithExistId() throws Exception {

    }

    @Test
    public void testDeleteByExistId() throws Exception {

    }

    @Test
    public void testDeleteByNotExistId() throws Exception {

    }
}
