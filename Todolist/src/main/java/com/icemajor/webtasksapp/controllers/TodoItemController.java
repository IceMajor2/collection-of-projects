package com.icemajor.todolistwebapp.controllers;

import com.icemajor.todolistwebapp.repositories.TodoItemRepository;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.ZoneId;

@Controller
public class TodoItemController {
    
    
    private final Logger log = LoggerFactory.getLogger(TodoItemController.class);
    @Autowired
    private TodoItemRepository todoItemRepository;
    
    @GetMapping("")
    public ModelAndView index() {
        log.debug("request to GET index");
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("todoItems", todoItemRepository.findAll());
        modelAndView.addObject("today",
                Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek());
        return modelAndView;
    }
}
