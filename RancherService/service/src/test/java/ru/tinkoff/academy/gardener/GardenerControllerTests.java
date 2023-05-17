package ru.tinkoff.academy.gardener;

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
import ru.tinkoff.academy.field.dto.FieldDto;
import ru.tinkoff.academy.gardener.dto.GardenerCreateDto;
import ru.tinkoff.academy.gardener.dto.GardenerDto;
import ru.tinkoff.academy.gardener.dto.GardenerUpdateDto;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class GardenerControllerTests extends AbstractIntegrationTest {
    private final String fielders = "/fielders";
    private final String fieldersById = "/fielders/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GardenerRepository gardenerRepository;

    @Test
    public void testSaveCorrectFielder() throws Exception {
        GardenerDto expectedFielder = GardenerDto.builder()
                .id("id8")
                .name("name7")
                .surname("surname7")
                .email("email7@email.com")
                .fields(List.of(
                        FieldDto.builder()
                                .id("id1")
                                .address("addr1")
                                .latitude(800.0)
                                .longitude(800.0)
                                .area(25.0)
                                .build()
                ))
                .telephone("telephone")
                .build();

        GardenerCreateDto request = new GardenerCreateDto();
        request.setName("name7");
        request.setSurname("surname7");
        request.setEmail("email7@email.com");
        request.setFieldsId(List.of("id1"));
        request.setTelephone("telephone");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(fielders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        GardenerDto actualFielder = objectMapper.readValue(response.getResponse().getContentAsString(), GardenerDto.class);

        Assertions.assertEquals(expectedFielder.getName(), actualFielder.getName());
        Assertions.assertEquals(expectedFielder.getSurname(), actualFielder.getSurname());
        Assertions.assertEquals(expectedFielder.getEmail(), actualFielder.getEmail());
        Assertions.assertEquals(expectedFielder.getTelephone(), actualFielder.getTelephone());
        Assertions.assertEquals(expectedFielder.getFields(), actualFielder.getFields());
    }

    @Test
    @Disabled("disable until validator creation")
    public void testSaveIncorrectFielder() throws Exception {
        GardenerCreateDto request = new GardenerCreateDto();
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
        String id = "id2";

        GardenerDto expectedFielder = GardenerDto.builder()
                .id(id)
                .name("name2")
                .surname("surname2")
                .email("email2@email.com")
                .fields(List.of(FieldDto.builder()
                        .id("id1")
                        .address("addr1")
                        .latitude(800D)
                        .longitude(800D)
                        .area(25.0)
                        .build()))
                .build();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(fieldersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        GardenerDto actualFielder = objectMapper.readValue(response.getResponse().getContentAsString(), GardenerDto.class);

        Assertions.assertEquals(expectedFielder, actualFielder);
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        String id = "id1000";

        mockMvc.perform(MockMvcRequestBuilders.get(String.format(fieldersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        List<GardenerDto> expectedFielder = List.of(
                GardenerDto.builder()
                        .id("id2")
                        .name("name2")
                        .surname("surname2")
                        .email("email2@email.com")
                        .fields(List.of(
                                FieldDto.builder()
                                        .id("id1")
                                        .address("addr1")
                                        .latitude(800D)
                                        .longitude(800D)
                                        .area(25.0)
                                        .build()
                        ))
                        .build(),
                GardenerDto.builder()
                        .id("id3")
                        .name("name3")
                        .surname("surname3")
                        .email("email3@email.com")
                        .telephone("800-800-800")
                        .fields(List.of(
                                FieldDto.builder()
                                        .id("id2")
                                        .address("addr3")
                                        .latitude(800D)
                                        .longitude(800D)
                                        .area(25.0)
                                        .build()
                        ))
                        .build(),
                GardenerDto.builder()
                        .id("id6")
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

        List<GardenerDto> actualFielder = objectMapper.readValue(response.getResponse().getContentAsString(), listFielderType());

        Assertions.assertTrue(actualFielder.containsAll(expectedFielder));
    }

    @Test
    public void testUpdateCorrectFielder() throws Exception {
        String id = "id4";
        GardenerDto expectedFielder = GardenerDto.builder()
                .id(id)
                .name("name4")
                .surname("surname4")
                .email("email4@email.com")
                .fields(List.of())
                .telephone("new_phone")
                .build();

        GardenerUpdateDto request = new GardenerUpdateDto();
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

        GardenerDto actualFielder = objectMapper.readValue(response.getResponse().getContentAsString(), GardenerDto.class);

        Assertions.assertEquals(expectedFielder, actualFielder);
    }

    @Test
    @Disabled("until creation of validator")
    public void testUpdateIncorrectFielder() throws Exception {
        GardenerUpdateDto request = new GardenerUpdateDto();
        request.setId("id4");
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
        String id = "id5";

        Assertions.assertTrue(gardenerRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(fieldersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(gardenerRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        String id = "id1000";

        Assertions.assertFalse(gardenerRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(fieldersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(gardenerRepository.existsById(id));
    }

    private JavaType listFielderType() {
        return objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, GardenerDto.class);
    }
}
