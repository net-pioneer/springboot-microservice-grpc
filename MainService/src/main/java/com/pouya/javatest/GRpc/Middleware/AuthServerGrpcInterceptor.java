package com.pouya.javatest.GRpc.Middleware;

import com.pouya.common.grpc.CurrencyServiceGrpc;
import com.pouya.javatest.Auth.TokenValidator;
import com.pouya.library.Execptions.AuthException;
import com.pouya.javatest.Execptions.Customs.GrpcAuthExceptionMapper;
import com.pouya.javatest.Models.Service.Interfaces.TokenService;
import com.pouya.javatest.Models.User;
import com.pouya.library.StaticKeys;
import com.pouya.library.Auth.GRpc.GrpcUserContext;
import com.pouya.library.DTO.UserInfo;
import io.grpc.*;

import java.util.Set;

public class AuthServerGrpcInterceptor implements ServerInterceptor {

    private final TokenService tokenService;

    public AuthServerGrpcInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    private static final Set<Class<?>> EXCLUDED_SERVICES = Set.of(
            CurrencyServiceGrpc.class
    );

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        try {
            String serviceName = call.getMethodDescriptor().getServiceName();

            // 🔥 convert to class-based check
            if (isExcluded(serviceName)) {
                return next.startCall(call, headers);
            }else {
                String header = headers.get(Metadata.Key.of(StaticKeys.headerKey, Metadata.ASCII_STRING_MARSHALLER));

                if (header == null || !header.startsWith(StaticKeys.Bearer)) {
                    throw new AuthException("invalid_token");
                }
                String token = header.substring(7);
                User user = TokenValidator.validate(token, tokenService);
                UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), user.getRoles().stream().map(r -> r.getRole().toString()).toList());
                Context ctx = Context.current().withValue(GrpcUserContext.USER_CTX_KEY, userInfo);
                return Contexts.interceptCall(ctx, call, headers, next);
            }

        } catch (AuthException ex) {

            call.close(GrpcAuthExceptionMapper.map(ex), new Metadata());
            return new ServerCall.Listener<>() {};
        }
    }
    private boolean isExcluded(String serviceName) {
        return EXCLUDED_SERVICES.stream().anyMatch(cls -> serviceName.equals(cls.getName().replace("$Grpc", "").replace("Grpc", "")) || serviceName.contains(cls.getSimpleName().replace("Grpc", ""))
        );
    }
}