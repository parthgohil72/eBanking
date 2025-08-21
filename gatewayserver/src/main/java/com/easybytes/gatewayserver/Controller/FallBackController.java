package com.easybytes.gatewayserver.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/contactSupportInfo")
    public Mono<String> contactSupportInfo(){
        return Mono.just(
                "An error occurred while trying to contact the system. Please try again later or contact Support team");
    }
}
