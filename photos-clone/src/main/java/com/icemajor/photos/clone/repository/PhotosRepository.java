package com.icemajor.photos.clone.repository;

import com.icemajor.photos.clone.model.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotosRepository extends CrudRepository<Photo, Integer> {
}
