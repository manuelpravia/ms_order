package org.mpravia.proxy;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.mpravia.proxy.Dto.ProductCodesRequest;
import org.mpravia.proxy.Dto.ProductResponse;

import java.util.List;

@Path("/product")
@RegisterRestClient(configKey = "product-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ProductClient {

    @GET
    @Path("/{id}")
    ProductResponse getProduct(@PathParam("id") Long id);

    @POST
    @Path("/codes")
    List<ProductResponse> getProductsByCode(ProductCodesRequest request);
}
