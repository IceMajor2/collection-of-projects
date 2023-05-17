package com.icemajor.photos.clone;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@RestController
public class PhotosController {

    private final PhotosService photosService;

    public PhotosController(@Autowired PhotosService ps) {
        this.photosService = ps;
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/photos")
    public Collection<Photo> get() {
        return photosService.get();
    }

    @GetMapping("/photos/{id}")
    public Photo get(@PathVariable String id) {
        Photo photo = photosService.get(id);
        if(photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return photo;
    }

    @DeleteMapping("/photos/{id}")
    public void delete(@PathVariable String id) {
        Photo photo = photosService.remove(id);
        if(photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/photos")
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {
        return photosService.save(file.getOriginalFilename(), file.getBytes());
    }
}
