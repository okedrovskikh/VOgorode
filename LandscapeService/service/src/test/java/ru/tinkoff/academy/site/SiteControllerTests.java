package ru.tinkoff.academy.site;

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
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class SiteControllerTests extends AbstractIntegrationTest {
    private final String sites = "/sites";
    private final String sitesById = "/sites/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SiteRepository siteRepository;

    @Test
    public void testSaveCorrectSite() throws Exception {
        Site expectedSite = Site.builder()
                .latitude(9090.0)
                .longitude(6789.1)
                .build();

        SiteCreateDto request = new SiteCreateDto();
        request.setLatitude("9090.0");
        request.setLongitude("6789.1");

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post(this.sites)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Site actualSite = this.objectMapper.readValue(response.getResponse().getContentAsString(), Site.class);

        this.assertWithoutId(expectedSite, actualSite);
    }

    @Test
    public void testSaveIncorrectSite() throws Exception {
        SiteCreateDto request = new SiteCreateDto();
        request.setLatitude("678.2");

        this.mockMvc.perform(MockMvcRequestBuilders.post(this.sites)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testGetByExistId() throws Exception {
        UUID id = UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-b07c2166152d");

        Site expectedSite = Site.builder()
                .id(id)
                .latitude(5000.0)
                .longitude(322.0)
                .build();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get(String.format(sitesById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Site actualSite = this.objectMapper.readValue(response.getResponse().getContentAsString(), Site.class);

        Assertions.assertEquals(expectedSite, actualSite);
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        this.mockMvc.perform(MockMvcRequestBuilders.get(String.format(sitesById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Site> expectedSites = List.of(
                Site.builder()
                        .id(UUID.fromString("3e76f3f6-9f6e-41fd-8e0f-b07c2166152d"))
                        .latitude(5000.0)
                        .longitude(322.0)
                        .build(),
                Site.builder()
                        .id(UUID.fromString("3e76f3f6-9f6e-41fd-8e4f-b07c2166152d"))
                        .latitude(5000.0)
                        .longitude(322.0)
                        .build(),
                Site.builder()
                        .id(UUID.fromString("3e76f3f6-9f6e-40fd-8e0f-b07c2166152d"))
                        .latitude(5000.0)
                        .longitude(322.0)
                        .build()
        );

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get(String.format(this.sitesById, "/all"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Site> actualSites = this.objectMapper.readValue(response.getResponse().getContentAsString(), this.listSiteType());

        Assertions.assertTrue(actualSites.containsAll(expectedSites));
    }

    @Test
    public void testUpdateSiteWithExistId() throws Exception {
        UUID id = UUID.fromString("3e76f3f6-9f6e-49fd-8e0f-b07c2166152d");

        Site expectedSite = Site.builder()
                .id(id)
                .latitude(54.0)
                .longitude(430.0)
                .build();

        SiteUpdateDto request = new SiteUpdateDto();
        request.setId(id);
        request.setLatitude("54.0");
        request.setLongitude("430.0");

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.put(this.sites)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Site actualSite = this.objectMapper.readValue(response.getResponse().getContentAsString(), Site.class);

        Assertions.assertEquals(expectedSite, actualSite);
    }

    @Test
    public void testUpdateSiteWithNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        SiteUpdateDto request = new SiteUpdateDto();
        request.setId(id);
        request.setLatitude("54.0");
        request.setLongitude("430.0");

        this.mockMvc.perform(MockMvcRequestBuilders.put(this.sites)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        UUID id = UUID.fromString("4e76f3f6-9f6e-49fd-8e0f-b07c2166152d");

        Assertions.assertTrue(siteRepository.existsById(id));

        this.mockMvc.perform(MockMvcRequestBuilders.delete(String.format(sitesById, id))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(siteRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        UUID id = UUID.randomUUID();

        Assertions.assertFalse(siteRepository.existsById(id));

        this.mockMvc.perform(MockMvcRequestBuilders.delete(String.format(sitesById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(siteRepository.existsById(id));
    }

    private JavaType listSiteType() {
        return this.objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, Site.class);
    }

    private void assertWithoutId(Site expected, Site actual) {
        Assertions.assertEquals(expected.getLatitude(), actual.getLatitude());
        Assertions.assertEquals(expected.getLongitude(), actual.getLongitude());
    }
}
