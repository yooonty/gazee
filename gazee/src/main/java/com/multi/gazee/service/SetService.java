package com.multi.gazee.service;

public interface SetService {
    
    /* ADMIN 정산 */
    String settlement(int productId, String sellerId, String orderTransactionId) throws Exception;
}
