package com.linkminifier.controller;

import com.linkminifier.dto.LinkDTO;
import com.linkminifier.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping()
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping("/link")
    public String createLink(@RequestBody LinkDTO linkDTO) throws NoSuchAlgorithmException {
        if(linkDTO.getUrl().isBlank()){
            return String.valueOf(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }
        return linkService.create(linkDTO.getUrl());
    }

}
