package org.mpravia.service;

import org.mpravia.dto.OrderPageResponseDto;
import org.mpravia.dto.OrderRequestDto;
import org.mpravia.dto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto findById(Long orderId);

    OrderResponseDto findByCode(String code);

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequestDto);

    OrderResponseDto deleteOrder(Long orderId);

    OrderPageResponseDto getOrders(Integer page, Integer size);
}
