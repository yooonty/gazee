package com.multi.gazee.set;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class SetDAO {
    @Autowired
    SqlSessionTemplate my;
    
    public List<SetVO> listSet() {
        List<SetVO> setList = my.selectList("set.listSet");
        return setList;
    }
    
}
