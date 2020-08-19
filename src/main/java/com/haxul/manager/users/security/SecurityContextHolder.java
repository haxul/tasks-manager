package com.haxul.manager.users.security;

import lombok.Data;


public class SecurityContextHolder {
    private static final ThreadLocal<String> myThreadLocal = new ThreadLocal<>();

    public static void setUsername(String username) {
        myThreadLocal.set(username);
    }

    public static String getUsername() {
        return myThreadLocal.get();
    }

}
