package com.convivir.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileService {

    public String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        
        String originalName = file.getOriginalFilename();
        String uniqueName = UUID.randomUUID().toString() + "_" + (originalName != null ? originalName.replace(" ", "_") : "image");

        
        String targetPathStr = new File("target/classes/static/images/uploads").getAbsolutePath();
        Path targetPath = Paths.get(targetPathStr);

        
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }

        
        Path fileTargetDest = targetPath.resolve(uniqueName);
        Files.copy(file.getInputStream(), fileTargetDest);

        try {
            String srcPathStr = new File("src/main/resources/static/images/uploads").getAbsolutePath();
            Path srcPath = Paths.get(srcPathStr);
            if (!Files.exists(srcPath)) {
                Files.createDirectories(srcPath);
            }
            Path fileSrcDest = srcPath.resolve(uniqueName);
            Files.copy(file.getInputStream(), fileSrcDest);
        } catch (Exception e) {
            
        }

        
        return uniqueName;
    }
}