package com.linkminifier.service;

import com.linkminifier.model.Link;
import com.linkminifier.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public String create(String url) {
        Link link = new Link(url, 0);
        String minifiedUrl = generateLinkHash(url);
        link.setMinifiedUrl(minifiedUrl);

        this.linkRepository.save(link);
        return minifiedUrl;
    }

    public void updateVisit(Link link) throws ChangeSetPersister.NotFoundException {
        link.addOneVisit();
        this.linkRepository.save(link);
    }

    public Map<String, Object> getLink(String minifiedUrl) throws ChangeSetPersister.NotFoundException {
        Link link = getLinkByMinifiedUrl(minifiedUrl);
        updateVisit(link);

        Map<String, Object> linkData = new HashMap<>();
        linkData.put("url", link.getUrl());
        linkData.put("minifiedUrl", minifiedUrl);
        return linkData;
    }

    public Map<String, Object> getLinkAnalytics(String minifiedUrl) throws ChangeSetPersister.NotFoundException {
        Link link = getLinkByMinifiedUrl(minifiedUrl);

        Map<String, Object> linkData = new HashMap<>();

        linkData.put("url", link.getUrl());
        linkData.put("minifiedUrl", minifiedUrl);
        linkData.put("visits", link.getVisits());
        return linkData;
    }

    private Link getLinkByMinifiedUrl(String minifiedUrl) throws ChangeSetPersister.NotFoundException {
        Optional<Link> optionalLink = this.linkRepository.findByminifiedUrl(minifiedUrl);

        if (optionalLink.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Link Not Found");        }

        return optionalLink.get();
    }

    private static String generateLinkHash(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(input.getBytes());
            byte[] truncatedHash = Arrays.copyOf(hashBytes, 7);
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : truncatedHash) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
