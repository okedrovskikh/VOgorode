package ru.tinkoff.academy.rating;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.rating.dto.RatingDto;

import java.util.UUID;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "rating"},
        description = "Duration Landscape service Rating process handling",
        histogram = true
)
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public RatingDto getRatingBasedOnLastCompletedOrders(@RequestParam("worker") UUID workerId) {
        return ratingService.getRatingBasedOnLastCompletedOrders(workerId);
    }
}
