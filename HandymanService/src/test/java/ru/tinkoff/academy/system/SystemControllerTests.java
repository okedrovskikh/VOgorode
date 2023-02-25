package ru.tinkoff.academy.system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class SystemControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BuildProperties buildProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetLiveness() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/system/liveness"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetReadiness() throws Exception {
        Map<String, String> expectedReadiness = Map.of(buildProperties.getName(), "OK");

        MvcResult responseReadiness = mockMvc.perform(MockMvcRequestBuilders.get("/system/readiness"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Map<String, String> actualReadiness = objectMapper.readValue(responseReadiness.getResponse().getContentAsString(), new TypeReference<Map<String, String>>() {});
        Assertions.assertEquals(expectedReadiness, actualReadiness);
    }
}
