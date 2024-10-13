package io.devsense.basic_rest_service;

import io.devsense.basic_rest_service.service.ScheduledTasks;
import org.awaitility.Durations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;


import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ScheduledTaskTest {

    @SpyBean
    ScheduledTasks tasks;

    public void reportCurrentTime(){
        await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
            verify(tasks, atLeast(2)).reportCurrentTime();
        });
    }
}