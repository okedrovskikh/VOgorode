package ru.tinkoff.academy.order.request;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.landscape.order.Order;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "Order"},
        description = "Duration Rancher service Order process handling",
        histogram = true
)
public class OrderRequestController {
    private final OrderRequestServiceFacade orderRequestServiceFacade;

    @PostMapping("/createOrder")
    public Order createOrder(@RequestParam("garden") String gardenId) {
        return orderRequestServiceFacade.create(gardenId);
    }

    @GetMapping("/{id}")
    public OrderStatus getStatus(@PathVariable("id") String gardenId) {
        return orderRequestServiceFacade.getStatus(gardenId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void approve(@PathVariable("id") String gardenId) {
        orderRequestServiceFacade.approve(gardenId);
    }
}
