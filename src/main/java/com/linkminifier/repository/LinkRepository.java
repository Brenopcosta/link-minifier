package com.linkminifier.repository;

import com.linkminifier.model.Link;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LinkRepository extends MongoRepository<Link, String> {
}
