package com.multi.gazee.admin.brcypt;

public interface BcryptService {
    
    /* 비밀번호 암호화 (평문 -> 해쉬값) */
    String encrypt(String password) throws Exception;
}