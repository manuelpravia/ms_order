package org.mpravia.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long orderId;
    private String productCode;
    private String productName;
    private String unitMeasurement;
    private Long productPrice;
    private Long quantities;
    private double subTotal;
}
