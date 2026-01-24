package org.mpravia.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "amount_igv")
    private double amountIgv;

    private double discount;

    @Column(name = "price_final")
    private double priceFinal;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "create_date", nullable = false, updatable = false)
    private OffsetDateTime createDate;

    @Column(name = "change_date", nullable = false)
    private OffsetDateTime changeDate;

    @PrePersist
    protected void onCreate() {
        createDate = OffsetDateTime.now();
        changeDate = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        changeDate = OffsetDateTime.now();
    }
}
