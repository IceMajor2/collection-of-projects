package com.icemajor.todolist.app.controllers;

import com.icemajor.todolist.app.models.TodoItem;
import com.icemajor.todolist.app.repositories.TodoItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TodoFormController {

    private final Logger logger = LoggerFactory.getLogger(TodoFormController.class);

    @Autowired
    private TodoItemRepository todoItemRepository;

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        TodoItem task = todoItemRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Task not found %d"
                        .formatted(id)));
        model.addAttribute("todo", task);
        return "update-todo-item";
    }
}
