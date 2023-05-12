package ru.tinkoff.academy.bank.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
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
            .id("id1")
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
                .id("id5")
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

        Assertions.assertEquals(expectedBankAccount.getCardId(), actualBankAccount.getCardId());
        Assertions.assertEquals(expectedBankAccount.getPaymentSystem(), actualBankAccount.getPaymentSystem());
        Assertions.assertEquals(expectedBankAccount.getUser(), actualBankAccount.getUser());
        Assertions.assertEquals(expectedBankAccount.getBank(), actualBankAccount.getBank());
    }

    @Test
    @Disabled("ignore until validation json validation")
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
        String id = "id1";
        BankAccount expectedBankAccount = BankAccount.builder()
                .id(id)
                .cardId("0000000000000001")
                .paymentSystem(PaymentSystem.mastercard)
                .user(testUser)
                .bank("bank1")
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
                        .id("id1")
                        .cardId("0000000000000001")
                        .paymentSystem(PaymentSystem.mastercard)
                        .user(testUser)
                        .bank("bank1")
                        .build(),
                BankAccount.builder()
                        .id("id2")
                        .cardId("0000000000000002")
                        .paymentSystem(PaymentSystem.mir)
                        .user(testUser)
                        .bank("bank1")
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
                .id("id3")
                .cardId("0000000000000003")
                .paymentSystem(PaymentSystem.visa)
                .user(testUser)
                .build();

        BankAccountUpdateDto request = new BankAccountUpdateDto();
        request.setId("id3");
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
    @Disabled("ignore until validation json validation")
    public void testUpdateIncorrectAccount() throws Exception {
        BankAccountUpdateDto request = new BankAccountUpdateDto();
        request.setId("id3");
        request.setCardId("0000000000000003");

        mockMvc.perform(MockMvcRequestBuilders.put(accounts)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        String id = "id4";

        Assertions.assertTrue(bankAccountRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(accountById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(bankAccountRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        String id = "id1000";

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
