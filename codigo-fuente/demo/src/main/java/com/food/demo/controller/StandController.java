package com.food.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/stands")
public class StandController {

    @GetMapping
    public List<String> listar() {
        return new ArrayList<>();
    }
}
