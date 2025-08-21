package com.eazybytes.accounts.service.Client;

import com.eazybytes.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallBack implements LoanFeignClient {

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber) {
        return null;
    }
}
