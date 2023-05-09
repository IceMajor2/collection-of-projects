package com.icemajor.todolist.app.repositories;

import com.icemajor.todolist.app.models.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    
    
}
