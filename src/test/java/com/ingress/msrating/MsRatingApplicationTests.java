package com.ingress.msrating;

import com.ingress.msrating.dao.entity.RatingEntity;
import com.ingress.msrating.model.request.RatingRequest;
import com.ingress.msrating.dao.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class MsRatingApplicationTests {

	@Mock
	private RatingRepository ratingRepository;

	@Test
	void contextLoads() {
		Long id =  1L;
		RatingRequest ratingRequest = new RatingRequest(1L, 5);
		RatingEntity rating = RatingEntity.builder()
				.userId(id)
				.rate(5)
				.productId(1L)
				.build();

//		when(ratingRepository.save())


	}

}
