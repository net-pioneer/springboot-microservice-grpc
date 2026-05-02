package com.pouya.javatest.GRpc;

import com.pouya.common.grpc.*;
import com.pouya.library.Auth.GRpc.GrpcUserContext;
import com.pouya.library.DTO.UserInfo;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthGrpcService extends AuthServiceGrpc.AuthServiceImplBase {

    @Override
    public void validateToken(TokenRequest request, StreamObserver<UserResponse> responseObserver) {
        UserInfo user = GrpcUserContext.getUser();
        if (user == null) {
            responseObserver.onError(Status.UNAUTHENTICATED.withDescription("no_user_context").asRuntimeException());
            return;
        }
        UserResponse response = UserResponse.newBuilder().setId(user.getId()).setUsername(user.getUsername()).addAllRoles(user.getRoles()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}