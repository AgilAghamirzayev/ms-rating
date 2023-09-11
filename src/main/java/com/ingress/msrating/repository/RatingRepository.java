package com.ingress.msrating.repository;

import com.ingress.msrating.entity.Rating;
import com.ingress.msrating.model.client.RatingStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("""
               SELECT new  com.ingress.msrating.model.client.RatingStatistic(
               productId,
               ROUND(AVG(rate), 1),
               COUNT(*),
               COUNT(CASE WHEN rate = 1 THEN 1 END),
               COUNT(CASE WHEN rate = 2 THEN 1 ELSE 0 END),
               COUNT(CASE WHEN rate = 3 THEN 1 ELSE 0 END),
               COUNT(CASE WHEN rate = 4 THEN 1 ELSE 0 END),
               COUNT(CASE WHEN rate = 5 THEN 1 ELSE 0 END))
               FROM Rating
               WHERE productId = :productId
               GROUP BY productId
            """)
    RatingStatistic getRatingStatistic(@Param("productId") Long productId);
}
