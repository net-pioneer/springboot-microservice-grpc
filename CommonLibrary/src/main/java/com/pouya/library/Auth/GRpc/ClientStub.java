package com.pouya.library.Auth.GRpc;

import com.pouya.common.grpc.AuthServiceGrpc;
import com.pouya.library.StaticKeys;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;

public class ClientStub {
    public static AuthServiceGrpc.AuthServiceBlockingStub authenticateRequest(AuthServiceGrpc.AuthServiceBlockingStub stub,String token){
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of(StaticKeys.headerKey, Metadata.ASCII_STRING_MARSHALLER), StaticKeys.Bearer + token);
        return stub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
    }
}
