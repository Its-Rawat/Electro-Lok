package com.rawat.electrolok.store.services.impl;

import com.rawat.electrolok.store.exceptions.BadApiRequest;
import com.rawat.electrolok.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        logger.info("Full Image path {}",fullPathWithFileName);
        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){

            // Save File
            logger.info("File Extension is: {}",extension);
            File folder = new File(path);
            if(!folder.exists()){
                // create a folder
                folder.mkdirs();
            }
            // upload

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        }else{
            throw new BadApiRequest("File with this "+extension+" extension is not supported");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path+File.separator+ name;
        // reads data from file
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
