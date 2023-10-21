package com.ingress.msrating.dao.repository;

import com.ingress.msrating.dao.entity.RatingEntity;
import com.ingress.msrating.model.client.RatingStatistic;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    @Query("""
                    SELECT new com.ingress.msrating.model.client.RatingStatistic(
                        productId,
                        ROUND(AVG(rate), 1),
                        COUNT(*),
                        COUNT(CASE WHEN rate = 1 THEN 1 END),
                        COUNT(CASE WHEN rate = 2 THEN 1 END),
                        COUNT(CASE WHEN rate = 3 THEN 1 END),
                        COUNT(CASE WHEN rate = 4 THEN 1 END),
                        COUNT(CASE WHEN rate = 5 THEN 1 END)
                    )
                    FROM RatingEntity
                    WHERE productId = :productId
                    GROUP BY productId
            """)
    Optional<RatingStatistic> getRatingStatistic(Long productId);
}
