package com.chitkara.bfhl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chitkara.bfhl.service.AiService;
import java.util.*;

@RestController
public class BfhlController {

    private final AiService aiService;

    public BfhlController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("is_success", true);
        response.put("official_email", "tejasv2547.be23@chitkara.edu.in");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bfhl")
    public ResponseEntity<Map<String, Object>> bfhl(
            @RequestBody Map<String, Object> request) {

        if (request == null || request.size() != 1) {
            return ResponseEntity.badRequest().body(
                    Map.of("is_success", false, "error", "Request must contain exactly one key")
            );
        }

        String key = request.keySet().iterator().next();
        Object value = request.get(key);
        Object result;

        try {
            switch (key) {

                case "fibonacci":
                    if (!(value instanceof Number)) {
                        throw new IllegalArgumentException("fibonacci value must be an integer");
                    }
                    int n = ((Number) value).intValue();
                    if (n <= 0) {
                        return ResponseEntity.badRequest().body(
                                Map.of("is_success", false, "error", "fibonacci value must be a positive integer")
                        );
                    }
                    result = fibonacci(n);
                    break;

                case "prime":
                    if (!(value instanceof List<?>)) {
                        throw new IllegalArgumentException("prime value must be an integer array");
                    }
                    result = prime((List<?>) value);
                    break;

                case "hcf":
                    if (!(value instanceof List<?>)) {
                        throw new IllegalArgumentException("hcf value must be an integer array");
                    }
                    result = hcfList((List<?>) value);
                    break;

                case "lcm":
                    if (!(value instanceof List<?>)) {
                        throw new IllegalArgumentException("lcm value must be an integer array");
                    }
                    result = lcmList((List<?>) value);
                    break;

                case "AI":
                    if (!(value instanceof String)) {
                        throw new IllegalArgumentException("AI value must be a string");
                    }
                    result = aiService.askAI((String) value);
                    break;

                default:
                    return ResponseEntity.badRequest().body(
                            Map.of("is_success", false, "error", "Invalid key: " + key)
                    );
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("is_success", false, "error", e.getMessage())
            );
        }

        Map<String, Object> response = new HashMap<>();
        response.put("is_success", true);
        response.put("official_email", "tejasv2547.be23@chitkara.edu.in");
        response.put("data", result);

        return ResponseEntity.ok(response);
    }

    // ---------- LOGIC METHODS ----------

    private List<Integer> fibonacci(int n) {
        List<Integer> list = new ArrayList<>();
        int a = 0, b = 1;
        list.add(a);
        for (int i = 1; i < n; i++) {
            list.add(b);
            int temp = a + b;
            a = b;
            b = temp;
        }
        return list;
    }

    private List<Integer> prime(List<?> numbers) {
        List<Integer> result = new ArrayList<>();
        for (Object obj : numbers) {
            // FIX #5: safely convert JSON Number to int
            int num = ((Number) obj).intValue();
            if (isPrime(num)) {
                result.add(num);
            }
        }
        return result;
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private long hcf(long a, long b) {
        return b == 0 ? a : hcf(b, a % b);
    }

    private long hcfList(List<?> list) {
        long result = ((Number) list.get(0)).longValue();
        for (int i = 1; i < list.size(); i++) {
            result = hcf(result, ((Number) list.get(i)).longValue());
        }
        return result;
    }

    private long lcm(long a, long b) {
        return (a / hcf(a, b)) * b;
    }

    private long lcmList(List<?> list) {
        long result = ((Number) list.get(0)).longValue();
        for (int i = 1; i < list.size(); i++) {
            result = lcm(result, ((Number) list.get(i)).longValue());
        }
        return result;
    }
}