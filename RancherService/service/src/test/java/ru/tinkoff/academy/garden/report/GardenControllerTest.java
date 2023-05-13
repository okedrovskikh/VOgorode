package ru.tinkoff.academy.garden.report;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Assertions;
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
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.garden.ExtendedGarden;
import ru.tinkoff.academy.garden.Garden;
import ru.tinkoff.academy.garden.GardenRepository;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;
import ru.tinkoff.academy.landscape.site.Site;
import ru.tinkoff.academy.work.WorkEnum;

import java.util.UUID;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 8082)
public class GardenControllerTest extends AbstractIntegrationTest {
    private final String gardens = "/gardens";
    private final String gardensBy = "/garden/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GardenRepository gardenRepository;

    @Test
    public void testSaveCorrectGarden() throws Exception {
        Site mockedSite = Site.builder()
                .id(UUID.fromString("80ef711b-80ac-40b5-8e3f-762c6111383f"))
                .build();
        WireMock.stubFor(WireMock.post("http://localhost:8082/sites")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockedSite))));

        Garden expectedResponse = Garden.builder()
                .id("id10")
                .ownerId(UUID.fromString("30a5d2e9-f0e9-4e39-aca2-71a4d3752e56"))
                .siteId(UUID.fromString("80ef711b-80ac-40b5-8e3f-762c6111383f"))
                .x1(30D)
                .y1(30D)
                .x2(60D)
                .y2(60D)
                .square(900D)
                .works(List.of(WorkEnum.plant))
                .build();

        GardenCreateDto request = new GardenCreateDto();
        request.setOwnerId(UUID.fromString("30a5d2e9-f0e9-4e39-aca2-71a4d3752e56"));
        request.setX1(30D);
        request.setY1(30D);
        request.setX2(60D);
        request.setY2(60D);
        request.setWorks(List.of(WorkEnum.plant));

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(gardens)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Garden actualResponse = objectMapper.readValue(response.getResponse().getContentAsString(), Garden.class);

        Assertions.assertEquals(expectedResponse.getOwnerId(), actualResponse.getOwnerId());
        Assertions.assertEquals(expectedResponse.getSiteId(), actualResponse.getSiteId());
        Assertions.assertEquals(expectedResponse.getX1(), actualResponse.getX1());
        Assertions.assertEquals(expectedResponse.getY1(), actualResponse.getY1());
        Assertions.assertEquals(expectedResponse.getX2(), actualResponse.getX2());
        Assertions.assertEquals(expectedResponse.getY2(), actualResponse.getY2());
        Assertions.assertEquals(expectedResponse.getSquare(), actualResponse.getSquare());
        Assertions.assertEquals(expectedResponse.getWorks(), actualResponse.getWorks());
    }

    @Test
    public void testGetByExistId() throws Exception {
        Site mockerSite = Site.builder()
                .id(UUID.fromString("e2bfdf03-12a0-486b-9a28-9a8873dc5994"))
                .latitude(800D)
                .longitude(800D)
                .build();
        WireMock.stubFor(WireMock.get("http://localhost:8082/sites/e2bfdf03-12a0-486b-9a28-9a8873dc5994")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockerSite))));

        ExtendedGarden expectedResponse = ExtendedGarden.builder()
                .id("id2")
                .ownerId(UUID.fromString("30a5d2e9-f0e9-4e39-aca2-71a4d3752e56"))
                .siteId(UUID.fromString("e2bfdf03-12a0-486b-9a28-9a8873dc5994"))
                .x1(30D)
                .y1(30D)
                .x2(60D)
                .y2(60D)
                .works(List.of(WorkEnum.plant))
                .latitude(800D)
                .longitude(800D)
                .build();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(gardensBy, ""))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ExtendedGarden actualResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ExtendedGarden.class);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        String id = "id1000";

        mockMvc.perform(MockMvcRequestBuilders.get(String.format(gardensBy, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateCorrectGarden() throws Exception {
        Garden expectedResponse = Garden.builder()
                .id("id3")
                .ownerId(UUID.fromString("30a5d2e9-f0e9-4e39-aca2-71a4d3752e56"))
                .siteId(UUID.fromString("c6334fe8-2951-4e99-9d93-f1255900a426"))
                .x1(30D)
                .y1(30D)
                .x2(40D)
                .y2(40D)
                .square(100D)
                .works(List.of(WorkEnum.sow))
                .build();

        GardenUpdateDto request = new GardenUpdateDto();
        request.setId("id3");
        request.setOwnerId(UUID.fromString("30a5d2e9-f0e9-4e39-aca2-71a4d3752e56"));
        request.setX1(30D);
        request.setY1(30D);
        request.setX2(40D);
        request.setY2(40D);
        request.setWorks(List.of(WorkEnum.sow));

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(gardens)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Garden actualGarden = objectMapper.readValue(response.getResponse().getContentAsString(), Garden.class);

        Assertions.assertEquals(expectedResponse, actualGarden);
    }

    @Test
    public void testUpdateIncorrectGarden() throws Exception {
        GardenUpdateDto request = new GardenUpdateDto();
        request.setId("id1000");

        mockMvc.perform(MockMvcRequestBuilders.put(gardens)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        String id = "id4";

        Assertions.assertTrue(gardenRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(gardensBy, id)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(gardenRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        String id = "id1000";

        Assertions.assertFalse(gardenRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(gardensBy, id)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(gardenRepository.existsById(id));
    }
}
