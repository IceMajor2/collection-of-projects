package com.icemajor.todolistwebapp.repositories;

import com.icemajor.todolistwebapp.models.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    
    
}
