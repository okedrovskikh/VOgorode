package ru.tinkoff.academy.bank.account;

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
import ru.tinkoff.academy.bank.account.dto.BankAccountCreateDto;
import ru.tinkoff.academy.bank.account.dto.BankAccountUpdateDto;
import ru.tinkoff.academy.bank.account.payment.system.PaymentSystem;
import ru.tinkoff.academy.user.User;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerTest extends AbstractIntegrationTest {
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
    private BankAccountRepository bankAccountRepository;

    @Test
    public void testSaveCorrectAccount() throws Exception {
        BankAccount expectedBankAccount = BankAccount.builder()
                .id(5L)
                .cardId("0000000000000005")
                .paymentSystem(PaymentSystem.unionpay)
                .build();

        BankAccountCreateDto request = new BankAccountCreateDto();
        request.setCardId("0000000000000005");
        request.setPaymentSystem(PaymentSystem.unionpay);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(accounts)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        BankAccount actualBankAccount = objectMapper.readValue(response.getResponse().getContentAsString(), BankAccount.class);

        Assertions.assertEquals(expectedBankAccount, actualBankAccount);
    }

    @Test
    public void testSaveIncorrectAccount() throws Exception {
        BankAccountCreateDto request = new BankAccountCreateDto();
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
        BankAccount expectedBankAccount = BankAccount.builder()
                .id(id)
                .cardId("0000000000000001")
                .paymentSystem(PaymentSystem.mastercard)
                .user(testUser)
                .build();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(accountById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        BankAccount actualBankAccount = objectMapper.readValue(response.getResponse().getContentAsString(), BankAccount.class);

        Assertions.assertEquals(expectedBankAccount, actualBankAccount);
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
        List<BankAccount> expectedBankAccounts = List.of(
                BankAccount.builder()
                        .id(1L)
                        .cardId("0000000000000001")
                        .paymentSystem(PaymentSystem.mastercard)
                        .user(testUser)
                        .build(),
                BankAccount.builder()
                        .id(2L)
                        .cardId("0000000000000002")
                        .paymentSystem(PaymentSystem.mir)
                        .user(testUser)
                        .build()
        );

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(accountById, "all"))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<BankAccount> actualBankAccounts = objectMapper.readValue(response.getResponse().getContentAsString(), listAccountType());

        Assertions.assertTrue(actualBankAccounts.containsAll(expectedBankAccounts));
    }

    @Test
    public void testUpdateCorrectAccount() throws Exception {
        BankAccount expectedBankAccount = BankAccount.builder()
                .id(3L)
                .cardId("0000000000000003")
                .paymentSystem(PaymentSystem.visa)
                .user(testUser)
                .build();

        BankAccountUpdateDto request = new BankAccountUpdateDto();
        request.setId(3L);
        request.setCardId("0000000000000003");
        request.setPaymentSystem(PaymentSystem.visa);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(accounts)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        BankAccount actualBankAccount = objectMapper.readValue(response.getResponse().getContentAsString(), BankAccount.class);

        Assertions.assertEquals(expectedBankAccount, actualBankAccount);
    }

    @Test
    public void testUpdateIncorrectAccount() throws Exception {
        BankAccountUpdateDto request = new BankAccountUpdateDto();
        request.setId(3L);
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

        Assertions.assertTrue(bankAccountRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(accountById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(bankAccountRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        Long id = 1000L;

        Assertions.assertFalse(bankAccountRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(accountById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(bankAccountRepository.existsById(id));
    }

    private JavaType listAccountType() {
        return objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, BankAccount.class);
    }
}
