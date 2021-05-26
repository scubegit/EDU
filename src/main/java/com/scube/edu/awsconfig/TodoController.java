package com.scube.edu.awsconfig;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import com.scube.edu.controller.BucketController;

import java.util.List;

@RestController
@RequestMapping("/api/storageA")
@AllArgsConstructor
@CrossOrigin("*")
public class TodoController {
    TodoService service;

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    
    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {
        return new ResponseEntity<>(service.getAllTodos(), HttpStatus.OK);
    }

    @PostMapping(
            path = "/uploadFileTodo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Todo> saveTodo(@RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("file") MultipartFile file) {
    	
    	logger.info("---------TodoController saveTodo----------------");
    	
    	service.saveTodo(title, description, file);
    	
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "{id}/image/download")
    public byte[] downloadTodoImage(@PathVariable("id") Long id) {
    	
		/*
		 * byte[] res = fileStorageService.loadFileAsResourceFromAws(UserFor,id);
		 * 
		 * return res ;
		 */
        return service.downloadTodoImage(id);
    }


}