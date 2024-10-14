package io.devsense.basic_rest_service.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FileStorageService implements StorageService{

    private final Path rootLocation;

    @Override
    public void init() {

    }

    @Override
    public void store(MultipartFile multipartFile) {

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource LoadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
