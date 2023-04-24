package ru.tinkoff.academy.fielder;

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
import ru.tinkoff.academy.field.dto.FieldDto;
import ru.tinkoff.academy.fielder.dto.FielderCreateDto;
import ru.tinkoff.academy.fielder.dto.FielderDto;
import ru.tinkoff.academy.fielder.dto.FielderUpdateDto;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class FielderControllerTests extends AbstractIntegrationTest {
    private final String fielders = "/fielders";
    private final String fieldersById = "/fielders/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FielderRepository fielderRepository;

    @Test
    public void testSaveCorrectFielder() throws Exception {
        FielderDto expectedFielder = FielderDto.builder()
                .id(7L)
                .name("name7")
                .surname("surname7")
                .email("email7@email.com")
                .fields(List.of(
                        FieldDto.builder()
                                .id(1L)
                                .address("addr1")
                                .latitude(800.0)
                                .longitude(800.0)
                                .area(25.0)
                                .build()
                ))
                .telephone("telephone")
                .build();

        FielderCreateDto request = new FielderCreateDto();
        request.setName("name7");
        request.setSurname("surname7");
        request.setEmail("email7@email.com");
        request.setFieldsId(List.of(1L));
        request.setTelephone("telephone");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(fielders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        FielderDto actualFielder = objectMapper.readValue(response.getResponse().getContentAsString(), FielderDto.class);

        Assertions.assertEquals(expectedFielder, actualFielder);
    }

    @Test
    public void testSaveIncorrectFielder() throws Exception {
        FielderCreateDto request = new FielderCreateDto();
        request.setName("name6");
        request.setEmail("email6@email.com");
        request.setFieldsId(List.of());
        request.setTelephone("telephone");

        mockMvc.perform(MockMvcRequestBuilders.post(fielders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testGetByExistId() throws Exception {
        Long id = 2L;

        FielderDto expectedFielder = FielderDto.builder()
                .id(id)
                .name("name2")
                .surname("surname2")
                .email("email2@email.com")
                .fields(List.of())
                .build();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(fieldersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        FielderDto actualFielder = objectMapper.readValue(response.getResponse().getContentAsString(), FielderDto.class);

        Assertions.assertEquals(expectedFielder, actualFielder);
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get(String.format(fieldersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        List<FielderDto> expectedFielder = List.of(
                FielderDto.builder()
                        .id(2L)
                        .name("name2")
                        .surname("surname2")
                        .email("email2@email.com")
                        .fields(List.of(
                                FieldDto.builder()
                                        .id(1L)
                                        .address("addr1")
                                        .latitude(800D)
                                        .longitude(800D)
                                        .area(25.0)
                                        .build()
                        ))
                        .build(),
                FielderDto.builder()
                        .id(3L)
                        .name("name3")
                        .surname("surname3")
                        .email("email3@email.com")
                        .telephone("800-800-800")
                        .fields(List.of(
                                FieldDto.builder()
                                        .id(2L)
                                        .address("addr3")
                                        .latitude(800D)
                                        .longitude(800D)
                                        .area(25.0)
                                        .build()
                        ))
                        .build(),
                FielderDto.builder()
                        .id(6L)
                        .name("name6")
                        .surname("surname6")
                        .email("email6@email.com")
                        .telephone("890-900-678")
                        .fields(List.of())
                        .build()
        );

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(fieldersById, "all"))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<FielderDto> actualFielder = objectMapper.readValue(response.getResponse().getContentAsString(), listFielderType());

        Assertions.assertTrue(actualFielder.containsAll(expectedFielder));
    }

    @Test
    public void testUpdateCorrectFielder() throws Exception {
        Long id = 4L;
        FielderDto expectedFielder = FielderDto.builder()
                .id(id)
                .name("name4")
                .surname("surname4")
                .email("email4@email.com")
                .fields(List.of())
                .telephone("new_phone")
                .build();

        FielderUpdateDto request = new FielderUpdateDto();
        request.setId(id);
        request.setName("name4");
        request.setSurname("surname4");
        request.setEmail("email4@email.com");
        request.setFieldsId(List.of());
        request.setTelephone("new_phone");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(fielders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        FielderDto actualFielder = objectMapper.readValue(response.getResponse().getContentAsString(), FielderDto.class);

        Assertions.assertEquals(expectedFielder, actualFielder);
    }

    @Test
    public void testUpdateIncorrectFielder() throws Exception {
        FielderUpdateDto request = new FielderUpdateDto();
        request.setId(4L);
        request.setName("name4");
        request.setSurname("surname4");
        request.setFieldsId(List.of());
        request.setTelephone("new_phone");

        mockMvc.perform(MockMvcRequestBuilders.put(fielders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        Long id = 5L;

        Assertions.assertTrue(fielderRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(fieldersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(fielderRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        Long id = 1000L;

        Assertions.assertFalse(fielderRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(fieldersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(fielderRepository.existsById(id));
    }

    private JavaType listFielderType() {
        return objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, FielderDto.class);
    }
}
