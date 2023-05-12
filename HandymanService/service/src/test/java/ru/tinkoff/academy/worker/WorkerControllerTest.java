package ru.tinkoff.academy.worker;

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
import ru.tinkoff.academy.work.WorkEnum;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkerControllerTest extends AbstractIntegrationTest {
    private final String workers = "/workers";
    private final String workersById = "/workers/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WorkerRepository workerRepository;

    @Test
    public void testSaveCorrectWorker() throws Exception {
        Worker expectedResponse = Worker.builder()
                .landscapeUserId(UUID.fromString(""))
                .latitude(800D)
                .longitude(800D)
                .services(List.of(WorkEnum.plant))
                .build();

        WorkerCreateDto request = new WorkerCreateDto();
        request.setLatitude(800D);
        request.setLongitude(800D);
        request.setServices(List.of(WorkEnum.plant));
        request.setTelephone("telephone");
        request.setEmail("email6@email.com");
        request.setLogin("login");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(workers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Worker actualWorker = objectMapper.readValue(response.getResponse().getContentAsString(), Worker.class);

        Assertions.assertEquals(expectedResponse.getLandscapeUserId(), actualWorker.getLandscapeUserId());
        Assertions.assertEquals(expectedResponse.getLatitude(), actualWorker.getLatitude());
        Assertions.assertEquals(expectedResponse.getLongitude(), actualWorker.getLongitude());
        Assertions.assertEquals(expectedResponse.getServices(), actualWorker.getServices());
    }

    @Test
    public void testGetByExistId() throws Exception {
        ExtendedByUserWorker expectedResponse = ExtendedByUserWorker.builder()
                .id("id1")
                .latitude(800D)
                .longitude(800D)
                .services(List.of(WorkEnum.plant))
                .landscapeUser(null)
                .build();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(workersById, "id1"))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ExtendedByUserWorker actualResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ExtendedByUserWorker.class);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        String id = "id1000";

        mockMvc.perform(MockMvcRequestBuilders.get(String.format(workersById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        List<ExtendedByUserWorker> expectedResponse = List.of(
                ExtendedByUserWorker.builder()
                        .id("id1")
                        .latitude(800D)
                        .longitude(800D)
                        .services(List.of(WorkEnum.plant))
                        .landscapeUser(null)
                        .build(),
                ExtendedByUserWorker.builder()
                        .id("id2")
                        .latitude(800D)
                        .longitude(800D)
                        .services(List.of(WorkEnum.shovel))
                        .landscapeUser(null)
                        .build()
        );

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(workers)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<ExtendedByUserWorker> actualResponse = objectMapper.readValue(response.getResponse().getContentAsString(), listExtendedWorkerType());

        Assertions.assertTrue(actualResponse.containsAll(expectedResponse));
    }

    @Test
    public void testUpdateCorrectWorker() throws Exception {
        Worker expectedResponse = Worker.builder()
                .id("id3")
                .landscapeUserId(UUID.fromString("4572c5f7-529b-4514-84a6-59547e6aa2c3"))
                .latitude(600D)
                .longitude(600D)
                .services(List.of(WorkEnum.shovel, WorkEnum.water))
                .build();

        WorkerUpdateDto request = new WorkerUpdateDto();
        request.setId("id3");
        request.setLatitude(600D);
        request.setLongitude(600D);
        request.setServices(List.of(WorkEnum.shovel, WorkEnum.water));

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(workers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Worker actualWorker = objectMapper.readValue(response.getResponse().getContentAsString(), Worker.class);

        Assertions.assertEquals(expectedResponse, actualWorker);
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        String id = "id4";

        Assertions.assertTrue(workerRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(workersById, id)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(workerRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        String id = "id4";

        Assertions.assertFalse(workerRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(workersById, id)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(workerRepository.existsById(id));
    }

    private JavaType listExtendedWorkerType() {
        return objectMapper.getTypeFactory()
                .constructCollectionLikeType(ArrayList.class, ExtendedByUserWorker.class);
    }
}
