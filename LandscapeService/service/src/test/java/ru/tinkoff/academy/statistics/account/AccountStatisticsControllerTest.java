package ru.tinkoff.academy.statistics.account;

import org.junit.Ignore;
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
import ru.tinkoff.academy.AbstractIntegrationTest;

import java.sql.Timestamp;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountStatisticsControllerTest extends AbstractIntegrationTest {
    private final String earliestCreationDate = "/stat/accounts/date/creation/earliest";
    private final String latestCreationDate = "/stat/accounts/date/creation/latest";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetEarliestCreationDate() throws Exception {
        Timestamp expectedTime = Timestamp.valueOf("2023-03-26 17:04:39.151");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(earliestCreationDate)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Timestamp actualTime = objectMapper.readValue(response.getResponse().getContentAsString(), Timestamp.class);

        Assertions.assertEquals(expectedTime, actualTime);
    }

    @Test
    @Ignore("Update after account controller test")
    public void testGetLatestCreationDate() throws Exception {
        Timestamp expectedTime = Timestamp.valueOf("2023-03-26 18:04:39.151");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(latestCreationDate)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Timestamp actualTime = objectMapper.readValue(response.getResponse().getContentAsString(), Timestamp.class);

        Assertions.assertEquals(expectedTime, actualTime);
    }
}
