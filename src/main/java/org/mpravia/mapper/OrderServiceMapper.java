package org.mpravia.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mpravia.dto.DetailRequestDto;
import org.mpravia.dto.DetailResponseDto;
import org.mpravia.dto.OrderRequestDto;
import org.mpravia.dto.OrderResponseDto;
import org.mpravia.repository.entity.Order;
import org.mpravia.repository.entity.OrderDetail;

@ApplicationScoped
@Mapper(componentModel = "jakarta")
public interface OrderServiceMapper {

    Order toOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto toOrderResponseDto(Order order);

    OrderDetail toOrderDetail(DetailRequestDto detailRequestDto);

    DetailResponseDto toDetailResponseDto(OrderDetail orderDetail);
}
