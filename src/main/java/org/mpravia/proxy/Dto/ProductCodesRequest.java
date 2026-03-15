package org.mpravia.proxy.Dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductCodesRequest {
    private List<String> codes;
}
