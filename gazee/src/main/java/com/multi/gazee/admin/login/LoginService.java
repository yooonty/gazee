package com.multi.gazee.admin.login;

public interface LoginService {
    
    //아이디 비교
    int checkId(String idToCheck) throws Exception;
    
    //암호 평문-해쉬값 비교
    int checkPass(String plainPassword, String hasedPassword) throws Exception;
}
