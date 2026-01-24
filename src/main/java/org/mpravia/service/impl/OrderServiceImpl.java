package org.mpravia.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.mpravia.dto.*;
import org.mpravia.handler.AppException;
import org.mpravia.mapper.OrderServiceMapper;
import org.mpravia.repository.OrderDetailsRepository;
import org.mpravia.repository.OrderRepository;
import org.mpravia.repository.entity.Order;
import org.mpravia.service.OrderService;

import java.util.UUID;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderDetailsRepository orderDetailsRepository;

    @Inject
    OrderServiceMapper orderServiceMapper;

    @Override
    public OrderResponseDto findById(Long orderId) {

        var order = orderRepository.findById(orderId);
        if (order == null) {
            throw new AppException("EB01","La orden no existe", Response.Status.NOT_FOUND);
        }
        var orderResponseDto = orderServiceMapper.toOrderResponseDto(order);
        orderResponseDto.setCustomer(new CustomerDto());
        orderResponseDto.setUser(new UserDto());

        var listOrderDetailResponse = orderDetailsRepository.find("orderId", orderId)
                .stream()
                .map(orderServiceMapper::toDetailResponseDto)
                .toList();
        orderResponseDto.setDetail(listOrderDetailResponse);

        Log.info(" datos orderCode: "  + orderResponseDto.getOrderCode());
        Log.info(" datos method payment: "  + orderResponseDto.getPaymentMethod());
        Log.info(" datos price final: "  + orderResponseDto.getPriceFinal());
        return orderResponseDto;
    }

    @Override
    public OrderResponseDto findByCode(String code) {

        return null;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Log.info("Inside createOrder ");
        if (orderRequestDto.getDetail() == null || orderRequestDto.getDetail().isEmpty() ) {
            throw new AppException("EB01","La orden no tiene elementos", Response.Status.NOT_FOUND);
        }

        Order order = orderServiceMapper.toOrder(orderRequestDto);
        order.setOrderCode(generateUniqueRandomCode());

        Log.info("Persist order ");
        orderRepository.persist(order);

        Order orderNew = orderRepository.find("orderCode", order.getOrderCode())
                .firstResultOptional()
                .orElse(null);

        var orderDetails = orderRequestDto.getDetail()
                .stream()
                .map(detailRequestDto -> orderServiceMapper.toOrderDetail(detailRequestDto))
                .map(orderDetail -> {
                    orderDetail.setOrderId(orderNew.getId());
                    //orderDetail.setSubTotal(orderDetail.getQuantities()*orderDetail.getProductPrice());
                    orderDetail.setSubTotal(orderDetail.getQuantities()*5);
                    return orderDetail;
                })
                .toList();
        orderDetailsRepository.persist(orderDetails);

        OrderResponseDto orderResponseDto = orderServiceMapper.toOrderResponseDto(order);
        var listOrderDetailResponse = orderDetailsRepository.find("orderId", orderNew.getId())
                .stream()
                .map(orderServiceMapper::toDetailResponseDto)
                .toList();
        orderResponseDto.setDetail(listOrderDetailResponse);

        Log.info("End Service create order");
        return orderResponseDto;
    }

    @Override
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequestDto) {
        return null;
    }

    @Override
    public OrderResponseDto deleteOrder(Long orderId) {
        return null;
    }

    @Override
    public OrderPageResponseDto getOrders(Integer page, Integer size) {
        return null;
    }

    private String generateUniqueRandomCode() {
        Log.info("Generating unique random code ");
        String newCode;
        boolean existCode;
        do {
            newCode = UUID.randomUUID().toString().substring(0,10).toUpperCase();
            existCode = orderRepository.find("orderCode", newCode).firstResultOptional().isPresent();
        }while (existCode);
        Log.info("Code generated: " + newCode);
        return newCode;
    }
}
