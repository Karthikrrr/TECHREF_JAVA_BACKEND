package com.business_website.service_implementation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.business_website.services.FileUploadService;

@Service
public class FileUploadServiceImplementation implements FileUploadService {
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Override
    public String saveFile(MultipartFile file) {
      if(file.isEmpty()){
        return "file is empty";
    }
    if(file.getSize() > 1048576){
        return "1mb";
    }
    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    Path filePath = Paths.get(UPLOAD_DIR + fileName);
    try {
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
    } catch (IOException e) {
        throw new IllegalArgumentException("DIR failed to create!!!");
    }

    return fileName;
    }

}
