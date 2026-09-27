package com.example.studenterp.controller;

import com.example.studenterp.exception.TestException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final long slowDelayMs;

    public TestController(@Value("${app.test.slow-delay-ms}") long slowDelayMs) {
        this.slowDelayMs = slowDelayMs;
    }

    @GetMapping("/slow")
    public Map<String, Object> slowEndpoint() throws InterruptedException {
        Thread.sleep(slowDelayMs);
        return Map.of(
                "success", true,
                "message", "Slow endpoint completed",
                "delayMs", slowDelayMs);
    }

    @GetMapping("/error")
    public Map<String, Object> errorEndpoint() {
        throw new TestException("Controlled test exception from /api/test/error");
    }
}
