package ru.tinkoff.academy.field;

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
import ru.tinkoff.academy.field.dto.FieldCreateDto;

@SpringBootTest
@AutoConfigureMockMvc
public class FieldControllerTests extends AbstractIntegrationTest {
    private final String fields = "/fields";
    private final String fieldsById = "/fields/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FieldRepository fieldRepository;

    @Test
    public void testSaveCorrectField() throws Exception {
        Field expectedField = Field.builder()
                .id(5L)
                .address("addr5")
                .longitude(500D)
                .latitude(490.2)
                .area(new Object())
                .build();

        FieldCreateDto request = new FieldCreateDto();
        request.setAddress("addr5");
        request.setLongitude(500.0);
        request.setLatitude(490.2);
        request.setArea(new Object());

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(fields)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Field actualField = objectMapper.readValue(response.getResponse().getContentAsString(), Field.class);
        Assertions.assertEquals(expectedField, actualField);
    }
}
