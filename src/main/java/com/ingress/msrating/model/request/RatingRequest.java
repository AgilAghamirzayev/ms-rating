package com.ingress.msrating.model.request;

import com.ingress.msrating.entity.Rates;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

public record RatingRequest(Long productId, Integer rate) {

}
