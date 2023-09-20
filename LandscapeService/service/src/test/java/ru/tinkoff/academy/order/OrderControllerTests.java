package ru.tinkoff.academy.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JavaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import ru.tinkoff.academy.AbstractIntegrationTest;
import ru.tinkoff.academy.order.dto.OrderCreateDto;
import ru.tinkoff.academy.order.dto.OrderUpdateDto;
import ru.tinkoff.academy.order.status.OrderStatus;
import ru.tinkoff.academy.work.WorkEnum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests extends AbstractIntegrationTest {
    private final String orders = "/orders";
    private final String orderById = "/orders/%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveCorrectOrder() throws Exception {
        Order expectedOrder = Order.builder()
                .id(9L)
                .workerId(null)
                .gardenId(UUID.fromString("0fac7f6f-fef5-4919-9df3-1a57b2378e72"))
                .works(new WorkEnum[]{WorkEnum.sow})
                .status(OrderStatus.created)
                .build();

        OrderCreateDto request = new OrderCreateDto();
        request.setGardenId(UUID.fromString("0fac7f6f-fef5-4919-9df3-1a57b2378e72"));
        request.setWorks(new WorkEnum[]{WorkEnum.sow});

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(orders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Order actualOrder = objectMapper.readValue(response.getResponse().getContentAsString(), Order.class);

        assertEqualsOrder(expectedOrder, actualOrder);
    }

    @Test
    public void testSaveIncorrectOrder() throws Exception {
        OrderCreateDto request = new OrderCreateDto();
        request.setGardenId(UUID.randomUUID());

        mockMvc.perform(MockMvcRequestBuilders.post(orders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testGetByExistId() throws Exception {
        Long id = 2L;
        Order expectedOrder = Order.builder()
                .id(id)
                .workerId(UUID.fromString("3b1635c7-abad-4198-adbe-c60c02bb0bf5"))
                .gardenId(UUID.fromString("3b1635c7-abad-4198-adbe-c60c02bb0bf5"))
                .works(new WorkEnum[]{WorkEnum.plant})
                .status(OrderStatus.approved)
                .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                .build();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(orderById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Order actualOrder = objectMapper.readValue(response.getResponse().getContentAsString(), Order.class);

        assertEqualsOrder(expectedOrder, actualOrder);
    }

    @Test
    public void testGetByNotExistId() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get(String.format(orderById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Order> expectedOrders = List.of(
                Order.builder()
                        .id(1L)
                        .workerId(UUID.fromString("e37d98b9-e7d5-4517-887d-8e50d0e5b591"))
                        .gardenId(UUID.fromString("e37d98b9-e7d5-4517-887d-8e50d0e5b591"))
                        .works(new WorkEnum[]{WorkEnum.shovel, WorkEnum.water})
                        .status(OrderStatus.created)
                        .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .build(),
                Order.builder()
                        .id(2L)
                        .workerId(UUID.fromString("3b1635c7-abad-4198-adbe-c60c02bb0bf5"))
                        .gardenId(UUID.fromString("3b1635c7-abad-4198-adbe-c60c02bb0bf5"))
                        .works(new WorkEnum[]{WorkEnum.plant})
                        .status(OrderStatus.approved)
                        .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .build(),
                Order.builder()
                        .id(3L)
                        .workerId(UUID.fromString("be6c5410-c8b2-4b5c-a56f-da6d48db6e0a"))
                        .gardenId(UUID.fromString("be6c5410-c8b2-4b5c-a56f-da6d48db6e0a"))
                        .works(new WorkEnum[]{WorkEnum.plant})
                        .status(OrderStatus.created)
                        .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .build(),
                Order.builder()
                        .id(4L)
                        .workerId(UUID.fromString("5a9abd5c-fcb3-4b79-8651-fb690b892cb3"))
                        .gardenId(UUID.fromString("5a9abd5c-fcb3-4b79-8651-fb690b892cb3"))
                        .works(new WorkEnum[]{WorkEnum.shovel, WorkEnum.water})
                        .status(OrderStatus.created)
                        .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .build(),
                Order.builder()
                        .id(5L)
                        .workerId(UUID.fromString("30f80f73-370f-4531-9a9b-9e55f685fc16"))
                        .gardenId(UUID.fromString("30f80f73-370f-4531-9a9b-9e55f685fc16"))
                        .works(new WorkEnum[]{WorkEnum.shovel, WorkEnum.water})
                        .status(OrderStatus.created)
                        .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .build(),
                Order.builder()
                        .id(6L)
                        .workerId(UUID.fromString("b96efb51-618c-4a11-b7b5-a8805910457c"))
                        .gardenId(UUID.fromString("b96efb51-618c-4a11-b7b5-a8805910457c"))
                        .works(new WorkEnum[]{WorkEnum.sow})
                        .status(OrderStatus.created)
                        .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .build()
        );

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(String.format(orderById, "all"))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<Order> actualOrders = objectMapper.readValue(response.getResponse().getContentAsString(), listOrderType());
        Assertions.assertTrue(actualOrders.containsAll(expectedOrders));
    }

    @Test
    public void testGetPage() throws Exception {
        List<Order> expectedOrders = List.of(
                Order.builder()
                        .id(4L)
                        .workerId(UUID.fromString("5a9abd5c-fcb3-4b79-8651-fb690b892cb3"))
                        .gardenId(UUID.fromString("5a9abd5c-fcb3-4b79-8651-fb690b892cb3"))
                        .works(new WorkEnum[]{WorkEnum.shovel, WorkEnum.water})
                        .status(OrderStatus.created)
                        .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .build(),
                Order.builder()
                        .id(5L)
                        .workerId(UUID.fromString("30f80f73-370f-4531-9a9b-9e55f685fc16"))
                        .gardenId(UUID.fromString("30f80f73-370f-4531-9a9b-9e55f685fc16"))
                        .works(new WorkEnum[]{WorkEnum.shovel, WorkEnum.water})
                        .status(OrderStatus.created)
                        .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .build(),
                Order.builder()
                        .id(6L)
                        .workerId(UUID.fromString("b96efb51-618c-4a11-b7b5-a8805910457c"))
                        .gardenId(UUID.fromString("b96efb51-618c-4a11-b7b5-a8805910457c"))
                        .works(new WorkEnum[]{WorkEnum.sow})
                        .status(OrderStatus.created)
                        .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                        .build()
        );

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(orders + "?page=1&size=3")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Page<Order> page = objectMapper.readValue(response.getResponse().getContentAsString(), pageOrderType());
        List<Order> actualOrders = page.getContent();
        Assertions.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void testUpdateByCorrectDto() throws Exception {
        Order expectedOrder = Order.builder()
                .id(7L)
                .workerId(UUID.fromString("2946c578-5120-45f4-8cd6-8ab1b981dc5a"))
                .gardenId(UUID.fromString("2946c578-5120-45f4-8cd6-8ab1b981dc5a"))
                .works(new WorkEnum[]{WorkEnum.shovel, WorkEnum.water})
                .status(OrderStatus.in_progress)
                .createdAt(Timestamp.valueOf("2023-03-26 17:04:39.151"))
                .build();

        OrderUpdateDto request = new OrderUpdateDto();
        request.setId(7L);
        request.setWorks(new WorkEnum[]{WorkEnum.shovel, WorkEnum.water});

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(orders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Order actualOrder = objectMapper.readValue(response.getResponse().getContentAsString(), Order.class);

        assertEqualsOrder(expectedOrder, actualOrder);
    }

    @Test
    public void testUpdateByNotExistId() throws Exception {
        OrderUpdateDto request = new OrderUpdateDto();
        request.setId(100L);

        mockMvc.perform(MockMvcRequestBuilders.put(orders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateByIncorrectDto() throws Exception {
        OrderUpdateDto request = new OrderUpdateDto();
        request.setId(7L);

        mockMvc.perform(MockMvcRequestBuilders.put(orders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testDeleteByExistId() throws Exception {
        Long id = 8L;

        Assertions.assertTrue(orderRepository.existsById(id));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(orderById, id))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(orderRepository.existsById(id));
    }

    @Test
    public void testDeleteByNotExistId() throws Exception {
        Long id = 1000L;

        Assertions.assertFalse(orderRepository.existsById(id));

        this.mockMvc.perform(MockMvcRequestBuilders.delete(String.format(orderById, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(orderRepository.existsById(id));
    }

    private JavaType listOrderType() {
        return objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, Order.class);
    }

    private JavaType pageOrderType() {
        return objectMapper.getTypeFactory()
                .constructParametricType(RestResponsePage.class, Order.class);
    }

    private void assertEqualsOrder(Order expected, Order actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getWorkerId(), actual.getWorkerId());
        Assertions.assertEquals(expected.getGardenId(), actual.getGardenId());
        Assertions.assertArrayEquals(expected.getWorks(), actual.getWorks());
        Assertions.assertEquals(expected.getStatus(), actual.getStatus());
        Assertions.assertNotNull(actual.getCreatedAt());
    }

    private static class RestResponsePage<T> extends PageImpl<T> {

        private static final long serialVersionUID = 3248189030448292002L;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public RestResponsePage(@JsonProperty("content") List<T> content, @JsonProperty("number") int number, @JsonProperty("size") int size,
                                @JsonProperty("totalElements") Long totalElements, @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
                                @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort, @JsonProperty("first") boolean first,
                                @JsonProperty("numberOfElements") int numberOfElements, @JsonProperty("empty") boolean empty) {
            super(content, PageRequest.of(number, size), totalElements);
        }
    }
}
