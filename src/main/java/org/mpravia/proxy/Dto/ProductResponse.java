package org.mpravia.proxy.Dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ProductResponse {
    private long id;
    private String code;
    private String name;
    private String description;
    private String presentation;
    private double price;
    private Integer stock;
    private Long categoryId;
    private  String imageUrl;
    private OffsetDateTime createDate;
    private OffsetDateTime changeDate;
}
