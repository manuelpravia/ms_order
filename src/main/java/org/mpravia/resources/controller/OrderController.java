package org.mpravia.resources.controller;

import org.mpravia.api.OrderApi;
import org.mpravia.model.OrderPageResponse;
import org.mpravia.model.OrderRequest;
import org.mpravia.model.OrderResponse;
import jakarta.inject.Inject;
import org.mpravia.mapper.OrderControllerMapper;
import org.mpravia.service.OrderService;

import java.util.List;

public class OrderController implements OrderApi{

    @Inject
    OrderService orderService;
    @Inject
    OrderControllerMapper orderControllerMapper;


    /**
     * Add a new order to the database
     *
     * @param orderRequest structure order creation
     * @return Successful operation
     * @return Invalid input
     */
    @Override
    public OrderResponse addOrder(OrderRequest orderRequest) {
        var orderRequestDto = orderControllerMapper.toOrderRequestDto(orderRequest);

        return orderControllerMapper.toOrderResponse(orderService.createOrder(orderRequestDto));
    }

    /**
     * Delete a Order
     *
     * @param id Order identifier
     * @return Invalid parameter
     */
    @Override
    public void deleteOrder(Long id) {

    }

    /**
     * Returned to the Order when it exists.
     *
     * @param id Order identifier
     * @return successful operation
     * @return Invalid parameter
     * @return Order not found
     */
    @Override
    public OrderResponse getOrderById(Long id) {

        return orderControllerMapper.toOrderResponse(orderService.findById(id));
    }

    /**
     * Return the list of Order.
     *
     * @param page Page number (starting from 0)
     * @param size Number of records per page
     * @return Successful operation
     * @return Order not found
     */
    @Override
    public List<OrderPageResponse> listOrder(Integer page, Integer size) {
        return List.of();
    }

    /**
     * Update Order data using id.
     *
     * @param id           Order identifier
     * @param orderRequest Update an existent Order in the database
     * @return Successful operation
     * @return Invalid ID Order
     * @return Order not found
     * @return Validation exception
     */
    @Override
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        return null;
    }
}
