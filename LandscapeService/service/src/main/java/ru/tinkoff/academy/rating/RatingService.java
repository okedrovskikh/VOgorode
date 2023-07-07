package ru.tinkoff.academy.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.rating.dto.RatingDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingDto getRatingBasedOnLastCompletedOrders(UUID workerId) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setRating(getRating(workerId));
        return ratingDto;
    }

    private Float getRating(UUID workerId) {
        return (Float) ((Object[]) ratingRepository.getLastOrdersRating(10L, workerId))[0];
    }
}
