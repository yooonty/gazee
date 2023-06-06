package com.multi.gazee.scheduler;

import com.multi.gazee.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ThirtyDaysScheduler {
    
    @Autowired
    MemberDAO Mdao;
    
    private String memberId;
    
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    
    /* 30일 정지 */
    @Scheduled(initialDelay = 259200000L * 10, fixedDelay = Long.MAX_VALUE)
    public void release() {
        Mdao.releaseSuspension(memberId);
    }
}
