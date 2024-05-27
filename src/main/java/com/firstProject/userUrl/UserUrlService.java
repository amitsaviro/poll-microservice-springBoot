package com.firstProject.userUrl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "UserUrlService",
        url = "${userUrl.url}"
)
public interface UserUrlService {

    @GetMapping("/user/{userId}")
    UserUrlResponse getUserUrlById(@PathVariable Long userId);
}
