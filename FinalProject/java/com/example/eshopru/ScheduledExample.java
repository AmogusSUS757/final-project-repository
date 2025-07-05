package com.example.eshopru;

import java.time.LocalTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class ScheduledExample {
@Scheduled(fixedRate = 10000)
public void PrintTime() {
	LocalTime time = LocalTime.now();
	System.out.println(time);
}
}
