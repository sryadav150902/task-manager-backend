package com.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @GetMapping("/api/tasks/test")
    public String testTaskApi() {

        return "Protected Task API Accessed";

    }
}