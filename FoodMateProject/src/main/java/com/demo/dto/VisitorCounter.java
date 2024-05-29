package com.demo.dto;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class VisitorCounter {

    // AtomicInteger : 멀티스레드 환경에서 방문자 수를 안전하게 관리하기 위해 사용
    private final AtomicInteger visitorCount = new AtomicInteger(0);

    // 방문자 수가 저장될 파일 경로.
    private final String filePath = System.getProperty("user.dir") + "/visitorCount.txt";

    // 로그 메시지를 기록하기 위한 Logger.
    private static final Logger logger = LoggerFactory.getLogger(VisitorCounter.class);

    // 빈이 생성되고 의존성이 주입된 후에 호출
    @PostConstruct
    public void init() {
        try {
            File file = new File(filePath);
            if (file.exists()) { // 파일이 존재하면, 파일에서 방문자 수를 읽음
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String countStr = reader.readLine();
                    if (countStr != null) {
                        visitorCount.set(Integer.parseInt(countStr));
                        logger.info("Visitor count initialized to {}", countStr);
                    }
                }
            } else {
                logger.warn("Visitor count file not found, initializing to 0");
            }
        } catch (IOException | NumberFormatException e) {
            logger.error("Error initializing visitor count", e);
        }
    }

    // 빈이 소멸되기 전에 호출.
    @PreDestroy
    public void destroy() {
        saveVisitorCount();
    }

    // 방문자 수를 증가시키고 새로운 방문자 수를 반환
    public int incrementAndGet() {
        int newCount = visitorCount.incrementAndGet();
        logger.info("Incremented visitor count to {}", newCount); // 방문자 수 증가 로그 추가
        saveVisitorCount(); // 방문자 수를 파일에 저장
        return newCount;
    }

    // 현재 방문자 수를 반환
    public int getCount() {
        return visitorCount.get();
    }

    // 방문자 수를 파일에 저장하는 메서드.
    private void saveVisitorCount() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(Integer.toString(visitorCount.get()));
            logger.info("Visitor count saved as {}", visitorCount.get());
        } catch (IOException e) {
            logger.error("Error writing visitor count file", e);
        }
    }
}
