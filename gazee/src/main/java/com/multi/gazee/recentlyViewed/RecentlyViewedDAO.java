package com.multi.gazee.recentlyViewed;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.product.ProductVO;

@Component
public class RecentlyViewedDAO {
	//MyBatis부품!
		@Autowired
		SqlSessionTemplate my;
		
		public int recentView(HashMap<String, Object> map) {
			return my.insert("recentlyViewed.recentView", map);
		}
		
		public int recentViewCount(String memberId) {
			return my.selectOne("recentlyViewed.recentViewCount", memberId);
		}

		public List<ProductVO> recentViewList(HashMap<String, Object> map) {
			return my.selectList("recentlyViewed.recentViewList", map);
		}
		
		public int recentViewControl(String memberId) {
			return my.delete("recentlyViewed.recentViewControl", memberId);
		}
		
		/*
		 * public int count() { return my.selectOne("recentlyViewed.count"); }
		 */
		
}
