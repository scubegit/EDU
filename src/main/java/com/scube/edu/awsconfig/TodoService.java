package com.scube.edu.awsconfig;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TodoService {
    String saveTodo(String title, String description, MultipartFile file);

    byte[] downloadTodoImage(Long id);

    List<Todo> getAllTodos();
}