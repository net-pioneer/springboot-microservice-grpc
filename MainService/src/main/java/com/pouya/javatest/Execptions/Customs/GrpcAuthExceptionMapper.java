package com.pouya.javatest.Execptions.Customs;

import com.pouya.library.Execptions.AuthException;
import io.grpc.Status;

public class GrpcAuthExceptionMapper {

    public static Status map(AuthException ex) {
        return Status.UNAUTHENTICATED.withDescription(ex.getMessage());
    }
}
