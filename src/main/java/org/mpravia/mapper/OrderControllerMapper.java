package org.mpravia.mapper;

import org.mpravia.model.OrderPageResponse;
import org.mpravia.model.OrderRequest;
import org.mpravia.model.OrderResponse;
import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mpravia.dto.OrderPageResponseDto;
import org.mpravia.dto.OrderRequestDto;
import org.mpravia.dto.OrderResponseDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@ApplicationScoped
@Mapper(componentModel = "jakarta")
public interface OrderControllerMapper {

    default LocalDateTime map(OffsetDateTime value) {
        if (value == null) {
            return null;
        }

        ZoneId zoneLima = ZoneId.of("America/Lima");

        return value.atZoneSameInstant(zoneLima).toLocalDateTime();
    }

    OrderRequestDto toOrderRequestDto(OrderRequest orderRequest);

    OrderResponse toOrderResponse(OrderResponseDto orderResponseDto);

    @Mapping(target = "content", source = "orderResponseDto")
    OrderPageResponse toOrderPageResponse(OrderPageResponseDto orderPagResponseDto);

    List<OrderResponse> toListOrderResponse(List<OrderResponseDto> orderResponseDto);
}
