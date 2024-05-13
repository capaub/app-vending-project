package org.capaub.msproduct.service;

import org.springframework.stereotype.Service;
import pl.coderion.model.*;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;


@Service
public class OpenFoodFactApiService {

    protected Product getProductDetails(String barcode){
        OpenFoodFactsWrapper wrapper = new OpenFoodFactsWrapperImpl();
        ProductResponse productResponse = wrapper.fetchProductByCode(barcode);

        Product product = productResponse.getProduct();

        System.out.println("PRODUCT NAME: " + product.getProductName());


        SelectedImages selectedImages = product.getSelectedImages();

        return product;
    }
}
