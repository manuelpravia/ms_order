package org.mpravia.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private String paymentMethod;
    private double amountIgv;
    private double discount;
    private double priceFinal;
    private String orderStatus;
    private Long userId;
    private Long customerId;
    private List<DetailRequestDto> detail;
}
