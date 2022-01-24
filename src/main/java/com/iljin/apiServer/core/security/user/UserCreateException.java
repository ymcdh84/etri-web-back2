package com.iljin.apiServer.core.security.user;

public class UserCreateException extends UserException {

    UserCreateException() { super("사용자 생성에 실패했습니다."); }
    UserCreateException(String msg) { super(msg); }
}
