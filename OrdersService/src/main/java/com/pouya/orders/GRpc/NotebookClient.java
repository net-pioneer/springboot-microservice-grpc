package com.pouya.orders.GRpc;

import com.pouya.common.grpc.EmptyRequest;
import com.pouya.common.grpc.NotebookListResponse;
import com.pouya.common.grpc.NotebookResponse;
import com.pouya.common.grpc.NotebookServiceGrpc;
import com.pouya.library.Auth.Gate.AuthRequest;
import com.pouya.library.DTO.NotebookDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotebookClient {

    @GrpcClient("main-service")
    private NotebookServiceGrpc.NotebookServiceBlockingStub stub;

    @AuthRequest
    public List<NotebookDto> getNotebooks() {
       // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<NotebookDto> _res = stub.getNotebooks(EmptyRequest.newBuilder().build()).getNotebooksList().stream().map(NotebookDto::fromProto).toList();
        return _res;
    }
}