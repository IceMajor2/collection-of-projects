package com.icemajor.photos.clone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotosController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World!";
    }
}
