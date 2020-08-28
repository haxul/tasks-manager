package com.haxul.manager.users.security;


public class SecurityContextHolder {
    private static final ThreadLocal<String> myThreadLocal = new ThreadLocal<>();

    public static void setUsername(String username) {
        myThreadLocal.set(username);
    }

    public static String getUsername() {
        return myThreadLocal.get();
    }

    public static void remove() {
        myThreadLocal.remove();
    }

}
