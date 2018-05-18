package com.neueda.shorty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gandreou on 04/02/2018.
 */

@Controller
public class IndexController {
    @RequestMapping("/")
    String index(){
        return "index";
    }
}
