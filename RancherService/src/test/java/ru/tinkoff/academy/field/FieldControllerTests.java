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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JavaType;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;
import ru.tinkoff.academy.field.point.Point;
import ru.tinkoff.academy.fielder.Fielder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class FieldControllerTests extends AbstractIntegrationTest {
    private final String fields = "/fields";
    private final String fieldsById = "/fields/%s";
    private final Fielder testFielder = Fielder.builder()
            .id(1L)
            .name("name1")
            .surname("surname1")
            .email("email1@email.com")
            .build();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FieldRepository fieldRepository;

    @Test
    public void testSaveCorrectField() throws Exception {
        FieldDto expectedField = FieldDto.builder()
                .id(4L)
                .address("addr4")
                .longitude(500D)
                .latitude(490.2)
                .area(new Point(1D, 1D))
                .fielder(testFielder)
                .build();

        FieldCreateDto request = new FieldCreateDto();
        request.setAddress("addr4");
        request.setLongitude(500.0);
        request.setLatitude(490.2);
        request.setFielderId(1L);
        request.setArea(new ru.tinkoff.academy.field.point.Point(1D, 1D));

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(fields)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        FieldDto actualField = objectMapper.readValue(response.getResponse().getContentAsString(), FieldDto.class);
        Assertions.assertEquals(expectedField, actualField);
    }

    @Test
    public void testSaveIncorrectField() throws Exception {
        FieldCreateDto request = new FieldCreateDto();
        request.setFielderId(1L);

        mockMvc.perform(MockMvcRequestBuilders.post(fields)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testGetByExistId() throws Exception {
        Long id = 2L;
        FieldDto expectedField = FieldDto.builder()
                .id(id)
                .address("addr3")
                .latitude(800D)
                .longitude(800D)
                .area(new Point(1.0, 1.0))
                .fielder(testFielder)
                .build();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(fieldsById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        FieldDto actualField = objectMapper.readValue(response.getResponse().getContentAsString(), FieldDto.class);
        Assertions.assertEquals(expectedField, actualField);
    }

    @Test
    public void testFindAll() throws Exception {
        List<FieldDto> expectedFields = List.of(
                FieldDto.builder()
                        .id(1L)
                        .address("addr1")
                        .latitude(800D)
                        .longitude(800D)
                        .area(new Point(1.0, 1.0))
                        .fielder(testFielder)
                        .build(),
                FieldDto.builder()
                        .id(2L)
                        .address("addr3")
                        .latitude(800D)
                        .longitude(800D)
                        .area(new Point(1.0, 1.0))
                        .fielder(testFielder)
                        .build()
        );

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(fieldsById, "all"))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<FieldDto> actualFields = objectMapper.readValue(response.getResponse().getContentAsString(), listFieldType());
        Assertions.assertTrue(actualFields.containsAll(expectedFields));
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get(String.format(fieldsById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateCorrectField() throws Exception {
        Long id = 3L;
        FieldDto expectedField = FieldDto.builder()
                .id(id)
                .address("addr33")
                .latitude(800D)
                .longitude(800D)
                .area(new Point(1.0, 1.0))
                .fielder(testFielder)
                .build();

        FieldUpdateDto request = new FieldUpdateDto();
        request.setAddress("addr33");
        request.setId(id);
        request.setLatitude(800D);
        request.setLongitude(800D);
        request.setArea(new ru.tinkoff.academy.field.point.Point(1D, 1D));
        request.setFielderId(1L);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(fields)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        FieldDto actualField = objectMapper.readValue(response.getResponse().getContentAsString(), FieldDto.class);
        Assertions.assertEquals(expectedField, actualField);
    }

    @Test
    public void testUpdateIncorrectField() throws Exception {
        FieldUpdateDto request = new FieldUpdateDto();
        request.setId(3L);
        request.setAddress("addr33");

        mockMvc.perform(MockMvcRequestBuilders.put(fields)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        Long id = 3L;

        Assertions.assertTrue(fieldRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(fieldsById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(fieldRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        Long id = 1000L;

        Assertions.assertFalse(fieldRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(fieldsById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(fieldRepository.existsById(id));
    }

    private JavaType listFieldType() {
        return objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, FieldDto.class);
    }
}
