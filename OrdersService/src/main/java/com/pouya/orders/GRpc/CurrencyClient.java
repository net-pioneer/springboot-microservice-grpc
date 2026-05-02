package com.pouya.orders.GRpc;

import com.pouya.common.grpc.CurrencyServiceGrpc;
import com.pouya.common.grpc.Empty;
import com.pouya.library.DTO.CurrencyDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyClient {
    @GrpcClient("main-service")
    private CurrencyServiceGrpc.CurrencyServiceBlockingStub Stub;

    public List<CurrencyDTO> getCurrencies() {
        return Stub.listCurrencies(Empty.newBuilder().build()).getCurrenciesList().stream().map(CurrencyDTO::fromProto).toList();
    }
}
