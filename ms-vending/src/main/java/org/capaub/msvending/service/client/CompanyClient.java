package org.capaub.msvending.service.client;


import org.springframework.cloud.openfeign.FeignClient;


@FeignClient("ms-company")
public interface CompanyClient {

}
