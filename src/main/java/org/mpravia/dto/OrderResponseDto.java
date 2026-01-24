package org.mpravia.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private long id;
    private String orderCode;
    private String paymentMethod;
    private double amountIgv;
    private double discount;
    private double priceFinal;
    private String orderStatus;
    private UserDto user;
    private CustomerDto customer;
    private OffsetDateTime createDate;
    private OffsetDateTime changeDate;
    private List<DetailResponseDto> detail;
}
