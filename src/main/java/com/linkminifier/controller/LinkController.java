package com.linkminifier.controller;

import com.linkminifier.service.LinkService;
import com.linkminifier.utils.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping()
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping("/link")
    public String createLink(@RequestBody Map<String, Object> linkData) throws NoSuchAlgorithmException {
        String url;
        try{
            url = (String) linkData.get("url");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid body format");
        }

        if(!Validators.isValidURL(url)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL format");
        }

        return linkService.create(url);
    }

    @GetMapping("{minifiedUrl}")
    public Map<String, Object> getLink(@PathVariable String minifiedUrl) throws ChangeSetPersister.NotFoundException {
        return linkService.getLink(minifiedUrl);
    }

    @GetMapping("link/{minifiedUrl}/analytics")
    public Map<String, Object> getLinkAnalytics(@PathVariable String minifiedUrl) throws ChangeSetPersister.NotFoundException {
        return linkService.getLinkAnalytics(minifiedUrl);
    }
}
