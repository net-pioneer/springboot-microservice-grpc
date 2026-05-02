package com.pouya.library.Auth.GRpc;

import com.pouya.library.DTO.UserInfo;
import io.grpc.Context;

//just like springSecutiry Authenticate Holder
// equals => SecurityContextHolder.getContext().setAuthentication(user);
public class GrpcUserContext {
    public static final Context.Key<UserInfo> USER_CTX_KEY = Context.key("user");

    public static UserInfo getUser() {
        return USER_CTX_KEY.get();
    }
}