package com.pouya.javatest.GRpc.Handlers;

import com.pouya.common.grpc.*;
import com.pouya.javatest.Models.Currency;
import com.pouya.javatest.Models.Service.Interfaces.CurrencyService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class CurrencyGrpcServiceServer extends CurrencyServiceGrpc.CurrencyServiceImplBase {

    @Autowired
    CurrencyService _currencyService;

    @Override
    public void listCurrencies(Empty request, StreamObserver<CurrencyListResponse> responseObserver) {
        try {
            List<Currency> _items = _currencyService.getCurrencies();
            List<CurrencyResponse> grpcCurrencies = _items.stream().map(this::toCurrencyResponse).toList();
            CurrencyListResponse _res = CurrencyListResponse.newBuilder().addAllCurrencies(grpcCurrencies).build();
            responseObserver.onNext(_res);
            responseObserver.onCompleted();
        }catch (Exception e){
            responseObserver.onError(Status.INTERNAL.withDescription("faild_to_fetch").asRuntimeException());
        }
    }

    @Override
    public void getCurrencyById(CurrencyRequest request, StreamObserver<CurrencyResponse> responseObserver) {

    }

    private CurrencyResponse toCurrencyResponse(Currency currency) {
        return CurrencyResponse.newBuilder()
                .setId(currency.getId())
                .setName(currency.getName() != null ? currency.getName() : "")
                .setSymbol(currency.getSymbol() != null ? currency.getSymbol() : "")
                .setRate(currency.getRate().doubleValue())
                .build();
    }
}
