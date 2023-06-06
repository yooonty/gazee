package com.multi.gazee.set;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SetDAO {
    @Autowired
    SqlSessionTemplate my;
    
    /* ALL */
    public List<SetVO> listSet() {
        List<SetVO> setList = my.selectList("set.listSet");
        return setList;
    }
    
    /* INSERT */
    public int insert(SetVO bag) {
        int result = my.insert("set.create", bag);
        return result;
    }
}
