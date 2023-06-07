package com.multi.gazee.scheduler;

import com.multi.gazee.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SevenDaysScheduler {
    
    @Autowired
    MemberDAO Mdao;
    
    private String memberId;
    
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    
    /* 7일 정지 */
    @Scheduled(initialDelay = 86400000 * 7, fixedDelay = Long.MAX_VALUE)
    public void sevenDaysSuspension() {
        Mdao.releaseSuspension(memberId);
    }
}
