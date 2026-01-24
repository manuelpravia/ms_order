package org.mpravia.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderPageResponseDto {
    private Integer totalCount;
    private Integer totalPages;
    private Integer pageIndex;
    private Integer pageSize;
    private List<OrderResponseDto> orderResponseDto;
}
