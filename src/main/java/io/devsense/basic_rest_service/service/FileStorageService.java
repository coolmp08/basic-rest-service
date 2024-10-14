package io.devsense.basic_rest_service.service;

import io.devsense.basic_rest_service.config.StorageProperties;
import io.devsense.basic_rest_service.exception.StorageException;
import io.devsense.basic_rest_service.exception.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
public class FileStorageService implements StorageService {

    private final Path rootLocation;


    public FileStorageService(StorageProperties storageProperties) {
        if (storageProperties.getLocation().isEmpty()) {
            throw new StorageException("File Upload Location can not be empty");
        }
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage" +e);
        }

    }

    @Override
    public void store(MultipartFile multipartFile) {
        try {


            if (multipartFile.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }
            if (!multipartFile.getOriginalFilename().isBlank()) {
                Path destinationFile = this.rootLocation.resolve(Paths.get(multipartFile.getOriginalFilename())).
                        normalize().toAbsolutePath();


                if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath()))
                    throw new StorageException("Can not store file outside current directory");

                try (InputStream inputStream = multipartFile.getInputStream()) {
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        catch (IOException e){
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource LoadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file" + filename);
            }
        }
        catch (MalformedURLException exception){
            throw new StorageFileNotFoundException("could not read file, "+ filename , exception);
        }

    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
