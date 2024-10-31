package org.capaub.msexternalapi.service;

import org.springframework.stereotype.Service;
import pl.coderion.model.Product;
import pl.coderion.model.ProductResponse;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;

@Service
public class OpenFoodFactApiService {

    protected Product getProductDetails(String barcode){
        OpenFoodFactsWrapper wrapper = new OpenFoodFactsWrapperImpl();
        ProductResponse productResponse = wrapper.fetchProductByCode(barcode);

        Product product = productResponse.getProduct();

        String selectedImages = product.getImageFrontUrl();
        System.out.println(selectedImages);

        return product;
    }
}