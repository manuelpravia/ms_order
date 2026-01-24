package org.mpravia.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mpravia.repository.entity.Order;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {
}
