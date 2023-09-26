package com.linkminifier.service;

import com.linkminifier.model.Link;
import com.linkminifier.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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

    public void updateVisit(String id) throws ChangeSetPersister.NotFoundException {
        Optional<Link> optionalLink = this.linkRepository.findById(id);

        if (optionalLink.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        Link link = optionalLink.get();
        link.addOneVisit();
        this.linkRepository.save(link);
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
