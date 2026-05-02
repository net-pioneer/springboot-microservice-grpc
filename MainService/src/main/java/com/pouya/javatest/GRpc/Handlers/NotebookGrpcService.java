package com.pouya.javatest.GRpc.Handlers;


import com.pouya.common.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class NotebookGrpcService extends NotebookServiceGrpc.NotebookServiceImplBase {

    @Override
    public void getNotebooks(EmptyRequest request, StreamObserver<NotebookListResponse> responseObserver) {

        List<NotebookResponse> list = List.of(
                NotebookResponse.newBuilder().setId(1).setName("Pouya").setPhone("+989398948859").build(),
                NotebookResponse.newBuilder().setId(1).setName("Someone").setPhone("+98123456789").build()
        );

        NotebookListResponse response = NotebookListResponse.newBuilder().addAllNotebooks(list).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}