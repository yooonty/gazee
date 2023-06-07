package com.multi.gazee.scheduler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.multi.gazee.product.ProductDAO;

@Component
public class ProductScheduler {
	
	@Autowired
	ProductDAO productDao;
	
	@Autowired
	private TaskScheduler taskScheduler;
	
	private int productId; // 클래스의 멤버 변수로 선언
	
	private Map<Integer, ScheduledFuture<?>> schedulerMap = new HashMap<>();
	
	public void startScheduler(int productId) {
	    // 이미 스케줄러가 실행 중인 경우 중지시킴
	    stopScheduler(productId);

	    // 스케줄러를 생성하고 시작
	    ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(() -> scheduleSellTimeUpdate(productId), new Date(System.currentTimeMillis() + 10 * 60 * 1000));

	    // 생성된 스케줄러를 맵에 저장
	    schedulerMap.put(productId, scheduledFuture);
	}
	
	public void stopScheduler(int productId) {
	    // 맵에서 해당 productId에 대한 스케줄러를 가져옴
	    ScheduledFuture<?> scheduledFuture = schedulerMap.get(productId);

	    // 스케줄러가 실행 중인 경우 중지시킴
	    if (scheduledFuture != null && !scheduledFuture.isDone()) {
	        scheduledFuture.cancel(false);
	    }

	    // 맵에서 해당 스케줄러를 제거
	    schedulerMap.remove(productId);
	}

	public void setProductId(int productId) {
	    this.productId = productId;
	}

    public void scheduleSellTimeUpdate(int productId) {
    	LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("Scheduled task is executing at: " + currentTime);
        productDao.sellTimeDelete(productId);
    }
}
