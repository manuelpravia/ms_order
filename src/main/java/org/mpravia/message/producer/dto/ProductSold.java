package org.mpravia.message.producer.dto;

import lombok.Data;

@Data
public class ProductSold {
    private String productCode;
    private long quantity;
}
