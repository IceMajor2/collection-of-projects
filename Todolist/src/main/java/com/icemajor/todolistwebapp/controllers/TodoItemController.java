package com.icemajor.todolistwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TodoItemController {
    
    private final Logger log = LoggerFactory.getLogger(TodoItemController.class);
    
    @GetMapping("")
    public ModelAndView index() {
        log.debug("request to GET index");
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }
}
