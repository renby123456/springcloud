package com.jk.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "provider",fallback = UserServiceFallback.class)
public interface UserServiceFeign extends UserService{


}
