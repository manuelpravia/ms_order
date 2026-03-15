package org.mpravia.proxy.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mpravia.proxy.Dto.ProductCodesRequest;
import org.mpravia.proxy.Dto.ProductResponse;
import org.mpravia.proxy.ProductClient;

import java.util.List;

@ApplicationScoped
public class ProductClientService {

    @Inject
    @RestClient
    ProductClient productClient;

    @CircuitBreaker
    @Timeout
    @Retry()
    @Fallback(fallbackMethod = "fallbackProducts")
    public List<ProductResponse> getProductsByCode(List<String> codes) {

        ProductCodesRequest request = new ProductCodesRequest();
        request.setCodes(codes);
        Log.info("Payload microservice product... ");
        return productClient.getProductsByCode(request);
    }

    public List<ProductResponse> fallbackProducts(List<String> codes) {
        Log.info("Active fallbackProducts circuitBreaker...");
        return List.of();
    }

}
