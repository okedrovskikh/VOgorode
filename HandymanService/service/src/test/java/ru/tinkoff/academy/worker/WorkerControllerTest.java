package ru.tinkoff.academy.worker;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JavaType;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.landscape.account.Account;
import ru.tinkoff.academy.work.WorkEnum;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 8082)
public class WorkerControllerTest extends AbstractIntegrationTest {
    private final String workers = "/workers";
    private final String workersById = "/workers/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WorkerRepository workerRepository;

    @Test
    public void testSaveCorrectWorker() throws Exception {
        Account mockedUser = Account.builder()
                .id(UUID.fromString("751e0662-c503-4f7f-9146-9545dc882a4e"))
                .build();
        WireMock.stubFor(WireMock.post(WireMock.urlPathEqualTo("/users"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockedUser))));

        Worker expectedResponse = Worker.builder()
                .landscapeUserId(UUID.fromString("751e0662-c503-4f7f-9146-9545dc882a4e"))
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
        Account mockedUser = Account.builder()
                .id(UUID.fromString("18481034-3765-4ba1-9640-b5f440300299"))
                .longitude(800D)
                .longitude(800D)
                .login("login")
                .email("email")
                .telephone("telephone")
                .creationDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                .updateDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                .build();
        WireMock.stubFor(WireMock.get("/users/18481034-3765-4ba1-9640-b5f440300299")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockedUser))));

        ExtendedByUserWorker expectedResponse = ExtendedByUserWorker.builder()
                .id("id1")
                .latitude(800D)
                .longitude(800D)
                .services(List.of(WorkEnum.plant))
                .account(mockedUser)
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
    @Disabled("until write correct responses in mock server")
    public void testFindAll() throws Exception {
        Account mockedUser1 = Account.builder()
                .id(UUID.fromString("18481034-3765-4ba1-9640-b5f440300299"))
                .longitude(800D)
                .longitude(800D)
                .login("login")
                .email("email")
                .telephone("telephone")
                .creationDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                .updateDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                .build();
        WireMock.stubFor(WireMock.get("/users/18481034-3765-4ba1-9640-b5f440300299")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockedUser1))));
        Account mockedUser2 = Account.builder()
                .id(UUID.fromString("684d6f11-9b35-4906-90a2-95ce59ccc058"))
                .longitude(800D)
                .longitude(800D)
                .login("login")
                .email("email")
                .telephone("telephone")
                .creationDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                .updateDate(Timestamp.valueOf("2023-03-26 18:04:39.151"))
                .build();
        WireMock.stubFor(WireMock.get("/users/684d6f11-9b35-4906-90a2-95ce59ccc058")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockedUser2))));

        List<ExtendedByUserWorker> expectedResponse = List.of(
                ExtendedByUserWorker.builder()
                        .id("id1")
                        .latitude(800D)
                        .longitude(800D)
                        .services(List.of(WorkEnum.plant))
                        .account(mockedUser1)
                        .build(),
                ExtendedByUserWorker.builder()
                        .id("id2")
                        .latitude(800D)
                        .longitude(800D)
                        .services(List.of(WorkEnum.shovel))
                        .account(mockedUser2)
                        .build()
        );

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(workersById, "/extended/all"))
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
