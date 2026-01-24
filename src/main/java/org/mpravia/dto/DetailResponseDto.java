package org.mpravia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailResponseDto {
    private long id;
    private String orderId;
    private String productCode;
    private String productName;
    private String unitMeasurement;
    private Long productPrice;
    private Long quantities;
    private double subTotal;
}
