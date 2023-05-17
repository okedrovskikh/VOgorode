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
import ru.tinkoff.academy.gardener.Gardener;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class FieldControllerTests extends AbstractIntegrationTest {
    private final String fields = "/fields";
    private final String fieldsById = "/fields/%s";

    private final Gardener testGardener1 = Gardener.builder()
            .id("id1")
            .name("name1")
            .surname("surname1")
            .email("email1@email.com")
            .build();
    private final Gardener testGardener2 = Gardener.builder()
            .id("id2")
            .name("name2")
            .surname("surname2")
            .email("email2@email.com")
            .build();
    private final Gardener testGardener3 = Gardener.builder()
            .id("id3")
            .name("name3")
            .surname("surname3")
            .email("email3@email.com")
            .telephone("800-800-800")
            .build();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FieldRepository fieldRepository;

    @Test
    public void testSaveCorrectField() throws Exception {
        FieldDto expectedField = FieldDto.builder()
                .id("id7")
                .address("addr4")
                .longitude(500D)
                .latitude(490.2)
                .area(25.0)
                .build();

        FieldCreateDto request = new FieldCreateDto();
        request.setAddress("addr4");
        request.setLongitude(500.0);
        request.setLatitude(490.2);
        request.setArea(List.of(0D, 0D, 0D, 5D, 5D, 5D, 5D, 0D, 0D, 0D));

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(fields)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        FieldDto actualField = objectMapper.readValue(response.getResponse().getContentAsString(), FieldDto.class);

        Assertions.assertEquals(expectedField.getAddress(), actualField.getAddress());
        Assertions.assertEquals(expectedField.getLatitude(), actualField.getLatitude());
        Assertions.assertEquals(expectedField.getLongitude(), actualField.getLongitude());
        Assertions.assertEquals(expectedField.getArea(), actualField.getArea());
        Assertions.assertEquals(expectedField.getGardener(), actualField.getGardener());
    }

    @Test
    public void testSaveIncorrectField() throws Exception {
        FieldCreateDto request = new FieldCreateDto();

        mockMvc.perform(MockMvcRequestBuilders.post(fields)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testGetByExistId() throws Exception {
        String id = "id2";
        FieldDto expectedField = FieldDto.builder()
                .id(id)
                .address("addr3")
                .latitude(800D)
                .longitude(800D)
                .area(25.0)
                .gardener(testGardener3)
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
                        .id("id1")
                        .address("addr1")
                        .latitude(800D)
                        .longitude(800D)
                        .area(25.0)
                        .gardener(testGardener2)
                        .build(),
                FieldDto.builder()
                        .id("id2")
                        .address("addr3")
                        .latitude(800D)
                        .longitude(800D)
                        .area(25.0)
                        .gardener(testGardener3)
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
        String id = "id3";
        FieldDto expectedField = FieldDto.builder()
                .id(id)
                .address("addr33")
                .latitude(800D)
                .longitude(800D)
                .area(25.0)
                .gardener(testGardener1)
                .build();

        FieldUpdateDto request = new FieldUpdateDto();
        request.setAddress("addr33");
        request.setId(id);
        request.setLatitude(800D);
        request.setLongitude(800D);
        request.setArea(List.of(3D, 0D, 3D, 5D, 8D, 5D, 8D, 0D, 3D, 0D));

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
        request.setId("id3");
        request.setAddress("addr33");

        mockMvc.perform(MockMvcRequestBuilders.put(fields)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        String id = "id4";

        Assertions.assertTrue(fieldRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(fieldsById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(fieldRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        String id = "id1000";

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
