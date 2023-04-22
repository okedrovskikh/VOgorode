package ru.tinkoff.academy.account;

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
import ru.tinkoff.academy.account.Account;
import ru.tinkoff.academy.account.AccountRepository;
import ru.tinkoff.academy.account.dto.AccountCreateDto;
import ru.tinkoff.academy.account.dto.AccountUpdateDto;
import ru.tinkoff.academy.account.type.AccountType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests extends AbstractIntegrationTest {
    private final String users = "/users";
    private final String usersById = "/users/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveCorrectUser() throws Exception {
        Account expectedAccount = Account.builder()
                .type(AccountType.landscape)
                .login("login")
                .email("email@email.com")
                .telephone("89999999999")
                .latitude(100.0)
                .longitude(100.0)
                .build();

        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setType(AccountType.landscape);
        accountCreateDto.setLogin("login");
        accountCreateDto.setEmail("email@email.com");
        accountCreateDto.setTelephone("89999999999");
        accountCreateDto.setLatitude("100.0");
        accountCreateDto.setLongitude("100.0");

        MvcResult userResponse = this.mockMvc.perform(MockMvcRequestBuilders.post(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(accountCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Account actualAccount = this.objectMapper.readValue(userResponse.getResponse().getContentAsString(), Account.class);

        this.assertWithoutId(expectedAccount, actualAccount);
    }

    @Test
    public void testSaveIncorrectUser() throws Exception {
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setLogin("login");
        accountCreateDto.setTelephone("89999999999");
        accountCreateDto.setLatitude("100.0");
        accountCreateDto.setLongitude("100.0");

        this.mockMvc.perform(MockMvcRequestBuilders.post(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(accountCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testGetByExistId() throws Exception {
        UUID id = UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-b07c2166152c");

        Account expectedAccount = Account.builder()
                .id(id)
                .type(AccountType.rancher)
                .login("login")
                .email("email@email.com")
                .telephone("89999999999")
                .creationDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                .updateDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                .latitude(900.0)
                .longitude(900.0)
                .build();

        MvcResult userResponse = this.mockMvc.perform(MockMvcRequestBuilders.get(String.format(this.usersById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Account actualAccount = this.objectMapper.readValue(userResponse.getResponse().getContentAsString(), Account.class);

        Assertions.assertEquals(expectedAccount, actualAccount);
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
        List<Account> expectedAccounts = List.of(
                Account.builder()
                        .id(UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-b07c2166152d"))
                        .type(AccountType.rancher)
                        .login("login")
                        .email("email@email.com")
                        .telephone("89999999999")
                        .creationDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                        .updateDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                        .latitude(900.0)
                        .longitude(900.0)
                        .build(),
                Account.builder()
                        .id(UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-b08c2166152d"))
                        .type(AccountType.rancher)
                        .login("login")
                        .email("email@email.com")
                        .telephone("89999999999")
                        .creationDate(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .updateDate(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .latitude(900.0)
                        .longitude(900.0)
                        .build(),
                Account.builder()
                        .id(UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-b07c2166152c"))
                        .type(AccountType.rancher)
                        .login("login")
                        .email("email@email.com")
                        .telephone("89999999999")
                        .creationDate(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .updateDate(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .latitude(900.0)
                        .longitude(900.0)
                        .build()
        );

        MvcResult usersResponse = this.mockMvc.perform(MockMvcRequestBuilders.get(String.format(this.usersById, "/all"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Account> actualAccounts = this.objectMapper.readValue(usersResponse.getResponse().getContentAsString(), this.listUserType());

        Assertions.assertTrue(actualAccounts.containsAll(expectedAccounts));
    }

    @Test
    public void testUpdateCorrectUserWithExistId() throws Exception {
        UUID id = UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-a07c2166152d");

        Account expectedAccount = Account.builder()
                .id(id)
                .type(AccountType.landscape)
                .login("new-login")
                .email("email@email.com")
                .telephone("89999999998")
                .creationDate(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                .latitude(890.0)
                .longitude(900.9)
                .build();

        AccountUpdateDto accountUpdateDto = new AccountUpdateDto();
        accountUpdateDto.setId(id);
        accountUpdateDto.setType(AccountType.landscape);
        accountUpdateDto.setLogin("new-login");
        accountUpdateDto.setEmail("email@email.com");
        accountUpdateDto.setTelephone("89999999998");
        accountUpdateDto.setLatitude("890.0");
        accountUpdateDto.setLongitude("900.9");

        MvcResult userResponse = this.mockMvc.perform(MockMvcRequestBuilders.put(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(accountUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Account actualAccount = this.objectMapper.readValue(userResponse.getResponse().getContentAsString(), Account.class);

        this.assertWithId(expectedAccount, actualAccount);
    }

    @Test
    public void testUpdateCorrectUserWithNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        AccountUpdateDto accountUpdateDto = new AccountUpdateDto();
        accountUpdateDto.setId(id);
        accountUpdateDto.setType(AccountType.landscape);
        accountUpdateDto.setLogin("new-login");
        accountUpdateDto.setEmail("email@email.com");
        accountUpdateDto.setTelephone("89999999998");
        accountUpdateDto.setLatitude("890.0");
        accountUpdateDto.setLongitude("900.9");

        this.mockMvc.perform(MockMvcRequestBuilders.put(this.users)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(accountUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        UUID id = UUID.fromString("5e76f3f6-9f6e-41fd-8e0f-a07c2166152d");

        Assertions.assertTrue(this.accountRepository.existsById(id));

        this.mockMvc.perform(MockMvcRequestBuilders.delete(String.format(this.usersById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(this.accountRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        Assertions.assertFalse(this.accountRepository.existsById(id));

        this.mockMvc.perform(MockMvcRequestBuilders.delete(String.format(this.usersById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(this.accountRepository.existsById(id));
    }

    private void assertWithId(Account expectedAccount, Account actualAccount) {
        Assertions.assertEquals(expectedAccount.getId(), actualAccount.getId());
        Assertions.assertEquals(expectedAccount.getType(), actualAccount.getType());
        Assertions.assertEquals(expectedAccount.getLogin(), actualAccount.getLogin());
        Assertions.assertEquals(expectedAccount.getEmail(), actualAccount.getEmail());
        Assertions.assertEquals(expectedAccount.getTelephone(), actualAccount.getTelephone());
        Assertions.assertEquals(expectedAccount.getLatitude(), actualAccount.getLatitude());
        Assertions.assertEquals(expectedAccount.getLongitude(), actualAccount.getLongitude());
    }

    private void assertWithoutId(Account expectedAccount, Account actualAccount) {
        Assertions.assertEquals(expectedAccount.getType(), actualAccount.getType());
        Assertions.assertEquals(expectedAccount.getLogin(), actualAccount.getLogin());
        Assertions.assertEquals(expectedAccount.getEmail(), actualAccount.getEmail());
        Assertions.assertEquals(expectedAccount.getTelephone(), actualAccount.getTelephone());
        Assertions.assertEquals(expectedAccount.getLatitude(), actualAccount.getLatitude());
        Assertions.assertEquals(expectedAccount.getLongitude(), actualAccount.getLongitude());
    }

    private JavaType listUserType() {
        return this.objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, Account.class);
    }
}
