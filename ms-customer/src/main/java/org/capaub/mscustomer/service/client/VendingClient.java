package org.capaub.mscustomer.service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("ms-vending")
public interface VendingClient {
}
