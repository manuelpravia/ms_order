package org.mpravia.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.mpravia.config.RedisMapProperties;
import org.mpravia.dto.*;
import org.mpravia.handler.AppException;
import org.mpravia.mapper.OrderServiceMapper;
import org.mpravia.repository.OrderDetailsRepository;
import org.mpravia.repository.OrderRepository;
import org.mpravia.repository.entity.Order;
import org.mpravia.service.CacheService;
import org.mpravia.service.OrderService;

import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderDetailsRepository orderDetailsRepository;

    @Inject
    OrderServiceMapper orderServiceMapper;

    @Inject
    CacheService cacheService;

    @Inject
    RedisMapProperties redisMapProperties;

    @Override
    public OrderResponseDto findById(Long orderId) {
        Log.info("Get order whit id: " + orderId);
        var dataCache = cacheService.get(redisMapProperties.getOrderName(),getKeyCache(orderId));

        if (Objects.nonNull(dataCache)) {
            Log.info("Get order whit cache: " + orderId);
            return (OrderResponseDto) dataCache;
        }

        var order = orderRepository.findById(orderId);
        if ( Objects.isNull(order)) {
            throw new AppException("EB01","the order no exist", Response.Status.NOT_FOUND);
        }
        var orderResponseDto = orderServiceMapper.toOrderResponseDto(order);
        orderResponseDto.setCustomer(new CustomerDto());
        orderResponseDto.setUser(new UserDto());

        var listOrderDetailResponse = orderDetailsRepository.find("orderId", orderId)
                .stream()
                .map(orderServiceMapper::toDetailResponseDto)
                .toList();
        orderResponseDto.setDetail(listOrderDetailResponse);
        Log.info("Get successful of database" );

        cacheService.put(redisMapProperties.getOrderName(),
                getKeyCache(orderId),
                orderResponseDto,redisMapProperties.getOrderTtl());
        Log.info("Save order in cache whit key: " + orderId);

        return orderResponseDto;
    }

    @Override
    public OrderResponseDto findByCode(String code) {

        return null;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

        Log.info("Init create Order ");
        if (Objects.isNull(orderRequestDto.getDetail()) || orderRequestDto.getDetail().isEmpty() ) {
            throw new AppException("EB01","The order is empty", Response.Status.BAD_REQUEST);
        }

        Order order = orderServiceMapper.toOrder(orderRequestDto);
        order.setOrderCode(generateUniqueRandomCode());

        Log.info("Persist order in database ");
        orderRepository.persist(order);

        Order orderNew = orderRepository.find("orderCode", order.getOrderCode())
                .firstResultOptional()
                .orElseThrow();

        var orderDetails = orderRequestDto.getDetail()
                .stream()
                .map(detailRequestDto -> orderServiceMapper.toOrderDetail(detailRequestDto))
                .peek(orderDetail -> {
                    orderDetail.setOrderId(orderNew.getId());
                    //orderDetail.setSubTotal(orderDetail.getQuantities()*orderDetail.getProductPrice());
                    orderDetail.setSubTotal(orderDetail.getQuantities()*5);
                })
                .toList();
        orderDetailsRepository.persist(orderDetails);

        OrderResponseDto orderResponseDto = orderServiceMapper.toOrderResponseDto(order);
        var listOrderDetailResponse = orderDetailsRepository.find("orderId", orderNew.getId())
                .stream()
                .map(orderServiceMapper::toDetailResponseDto)
                .toList();
        orderResponseDto.setDetail(listOrderDetailResponse);

        Log.info("Save order in cache");
        cacheService.put(redisMapProperties.getOrderName(),
                getKeyCache(orderNew.getId()),
                orderResponseDto,redisMapProperties.getOrderTtl());

        Log.info("End Service create order");
        return orderResponseDto;
    }

    public String getKeyCache(Long orderId) {
        return redisMapProperties.getOrderPrefix().concat(String.valueOf(orderId));
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
            newCode = UUID.randomUUID()
                    .toString()
                    .substring(0,10)
                    .toUpperCase();
            existCode = orderRepository.find("orderCode", newCode)
                    .firstResultOptional()
                    .isPresent();
        }while (existCode);
        Log.info("Code generated: " + newCode);

        return newCode;
    }
}
