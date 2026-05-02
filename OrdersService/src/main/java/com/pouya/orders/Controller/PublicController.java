package com.pouya.orders.Controller;

import com.pouya.library.PacketTemplate.ApiResponse;
import com.pouya.orders.GRpc.CurrencyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    CurrencyClient _currencyClient;
    @GetMapping("/get_currencies")
    public ResponseEntity<?> getCurrencies() {
        return ApiResponse.success(_currencyClient.getCurrencies());
    }
}
