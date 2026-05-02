package com.pouya.library.Auth.Gate;

import com.pouya.library.StaticKeys;
import io.grpc.Metadata;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.MetadataUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1) //run after security so spring secutiry would not be null then !
public class GrpcAuthAspect {

    @Around("@annotation(AuthRequest) || within(@AuthRequest *)")
    public Object addAuthMetadata(ProceedingJoinPoint joinPoint) throws Throwable {

        Object target = joinPoint.getTarget();
        AbstractStub<?> originalStub = findStubField(target);

        if (originalStub == null) {
            return joinPoint.proceed();
        }
        //SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of(StaticKeys.headerKey, Metadata.ASCII_STRING_MARSHALLER), StaticKeys.Bearer + token);

        AbstractStub<?> stubWithAuth = originalStub.withInterceptors(
                MetadataUtils.newAttachHeadersInterceptor(metadata)
        );

        replaceStubField(target, stubWithAuth);

        try {
            return joinPoint.proceed();
        } finally {
            replaceStubField(target, originalStub);
        }
    }

    private AbstractStub<?> findStubField(Object target) {
        try {
            for (var field : target.getClass().getDeclaredFields()) {
                if (AbstractStub.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    return (AbstractStub<?>) field.get(target);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void replaceStubField(Object target, AbstractStub<?> newStub) {
        try {
            for (var field : target.getClass().getDeclaredFields()) {
                if (AbstractStub.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    field.set(target, newStub);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}