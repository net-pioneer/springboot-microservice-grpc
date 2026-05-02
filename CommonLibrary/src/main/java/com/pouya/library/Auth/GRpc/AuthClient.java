package com.pouya.library.Auth.GRpc;

import com.pouya.common.grpc.AuthServiceGrpc;
import com.pouya.common.grpc.TokenRequest;
import com.pouya.common.grpc.UserResponse;
import com.pouya.library.Auth.Gate.AuthRequest;
import com.pouya.library.StaticKeys;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        name = "auth.client.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class AuthClient {

    @GrpcClient("main-service")
    private AuthServiceGrpc.AuthServiceBlockingStub stub;

    //@AuthRequest
    public UserResponse validate(String token) {
       // return stub.validateToken(TokenRequest.newBuilder().setToken(token).build());
        return ClientStub.authenticateRequest(stub,token).validateToken(TokenRequest.newBuilder().setToken(token).build());
    }
}