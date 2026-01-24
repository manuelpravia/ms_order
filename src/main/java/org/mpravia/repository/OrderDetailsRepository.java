package org.mpravia.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mpravia.repository.entity.OrderDetail;

@ApplicationScoped
public class OrderDetailsRepository implements PanacheRepository<OrderDetail> {
}
