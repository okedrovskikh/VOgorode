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
import ru.tinkoff.academy.account.dto.AccountCreateDto;
import ru.tinkoff.academy.account.dto.AccountUpdateDto;
import ru.tinkoff.academy.account.payment.system.PaymentSystem;
import ru.tinkoff.academy.user.User;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest extends AbstractIntegrationTest {
    private final String accounts = "/accounts";
    private final String accountById = "/accounts/%s";
    private final User testUser = User.builder()
            .id(1L)
            .name("name1")
            .surname("surname1")
            .email("email1@email.com")
            .skills(new WorkEnum[]{WorkEnum.shovel})
            .telephone("telephone")
            .photo(new Byte[]{})
            .build();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveCorrectAccount() throws Exception {
        Account expectedAccount = Account.builder()
                .id(5L)
                .cardId("0000000000000005")
                .paymentSystem(PaymentSystem.unionpay)
                .user(testUser)
                .build();

        AccountCreateDto request = new AccountCreateDto();
        request.setCardId("0000000000000005");
        request.setPaymentSystem(PaymentSystem.unionpay);
        request.setUserId(1L);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(accounts)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Account actualAccount = objectMapper.readValue(response.getResponse().getContentAsString(), Account.class);

        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testSaveIncorrectAccount() throws Exception {
        AccountCreateDto request = new AccountCreateDto();
        request.setCardId("0000000000000005");

        mockMvc.perform(MockMvcRequestBuilders.post(accounts)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testGetByExistId() throws Exception {
        Long id = 1L;
        Account expectedAccount = Account.builder()
                .id(id)
                .cardId("0000000000000001")
                .paymentSystem(PaymentSystem.mastercard)
                .user(testUser)
                .build();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(accountById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Account actualAccount = objectMapper.readValue(response.getResponse().getContentAsString(), Account.class);

        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get(String.format(accountById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        List<Account> expectedAccounts = List.of(
                Account.builder()
                        .id(1L)
                        .cardId("0000000000000001")
                        .paymentSystem(PaymentSystem.mastercard)
                        .user(testUser)
                        .build(),
                Account.builder()
                        .id(2L)
                        .cardId("0000000000000002")
                        .paymentSystem(PaymentSystem.mir)
                        .user(testUser)
                        .build()
        );

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(accountById, "all"))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<Account> actualAccounts = objectMapper.readValue(response.getResponse().getContentAsString(), listAccountType());

        Assertions.assertTrue(actualAccounts.containsAll(expectedAccounts));
    }

    @Test
    public void testUpdateCorrectAccount() throws Exception {
        Account expectedAccount = Account.builder()
                .id(3L)
                .cardId("0000000000000003")
                .paymentSystem(PaymentSystem.visa)
                .user(testUser)
                .build();

        AccountUpdateDto request = new AccountUpdateDto();
        request.setId(3L);
        request.setCardId("0000000000000003");
        request.setUserId(1L);
        request.setPaymentSystem(PaymentSystem.visa);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(accounts)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Account actualAccount = objectMapper.readValue(response.getResponse().getContentAsString(), Account.class);

        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testUpdateIncorrectAccount() throws Exception {
        AccountUpdateDto request = new AccountUpdateDto();
        request.setId(3L);
        request.setUserId(1L);
        request.setCardId("0000000000000003");

        mockMvc.perform(MockMvcRequestBuilders.put(accounts)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        Long id = 4L;

        Assertions.assertTrue(accountRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(accountById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(accountRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        Long id = 1000L;

        Assertions.assertFalse(accountRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(accountById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(accountRepository.existsById(id));
    }

    private JavaType listAccountType() {
        return objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, Account.class);
    }
}
