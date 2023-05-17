package com.icemajor.photos.clone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {

    private final PhotosService photosService;

    public DownloadController(@Autowired PhotosService ps) {
        this.photosService = ps;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
