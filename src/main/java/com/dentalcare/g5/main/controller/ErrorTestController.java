package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.annotation.NotificarErrores;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NotificarErrores
@RequestMapping("${server.base}/${server.version}/test")
public class ErrorTestController {
    @GetMapping("/error")
    public void throeError(){
        throw new RuntimeException("Test Exception");
    }
}
