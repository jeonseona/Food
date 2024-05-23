package com.demo.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {

	private final AtomicInteger visitorCount = new AtomicInteger(0);

    // 조회수 증가
    public void increaseVisitorCount() {
        visitorCount.incrementAndGet();
    }

    // 조회수 반환
    public int getVisitorCount() {
        return visitorCount.get();
    }
}