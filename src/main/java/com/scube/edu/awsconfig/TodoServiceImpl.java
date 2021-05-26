package com.scube.edu.awsconfig;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final FileStore fileStore;
//    private final TodoRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);
    
    @Override
//    public String saveTodo(MultipartFile file) {
    public String saveTodo(String title, String description, MultipartFile file) {
        //check if the file is empty
    	
    	logger.info("---------TodoServiceImpl saveTodo----------------");
    	
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        
        logger.info("---------TodoServiceImpl file is not Empty----------------");
        
        //Check if the file is an image
//        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
//                IMAGE_BMP.getMimeType(),
//                IMAGE_GIF.getMimeType(),
//                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
//            throw new IllegalStateException("FIle uploaded is not an image");
//        }
        
        logger.info("---------TodoServiceImpl Arrays as List----------------");
        
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        
        //Save Image in S3 and then save Todo in the database
        
        
        String path = String.format("%s/%s", BucketName.TODO_IMAGE.getBucketName(), UUID.randomUUID());
        
        logger.info("---------TodoServiceImpl path----------------"+ path );
        
        String fileName = String.format("%s", file.getOriginalFilename());
        
        logger.info("---------TodoServiceImpl fileName----------------"+fileName );
        
        logger.info("---------TodoServiceImpl start fileStore upload----------------");
        
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        
        logger.info("---------TodoServiceImpl End fileStore upload----------------");
        
        Todo todo = Todo.builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .imageFileName(fileName)
                .build();
        // repository.save(todo);
        return "ok";
    }

    @Override
    public byte[] downloadTodoImage(Long id) {
//		return null;
//        Todo todo = repository.findById(id).get();
        return fileStore.download("educred/a27a2080-fa5e-4e1c-ad75-ab2ed0b9ff12", "Marksheet_2000 (4) (1).pdf");
    }

    @Override
    public List<Todo> getAllTodos() {
		return null;
 //       List<Todo> todos = new ArrayList<>();
 //       repository.findAll().forEach(todos::add);
 //       return todos;
    }
}