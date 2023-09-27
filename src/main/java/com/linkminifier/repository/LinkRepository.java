package com.linkminifier.repository;

import com.linkminifier.model.Link;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LinkRepository extends MongoRepository<Link, String> {

    Optional<Link> findByminifiedUrl(String minifiedUrl);
}
