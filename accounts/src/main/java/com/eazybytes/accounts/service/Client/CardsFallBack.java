package com.eazybytes.accounts.service.Client;

import com.eazybytes.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallBack implements CardFeignClient {

    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String mobileNumber) {
        return null;
    }
}
