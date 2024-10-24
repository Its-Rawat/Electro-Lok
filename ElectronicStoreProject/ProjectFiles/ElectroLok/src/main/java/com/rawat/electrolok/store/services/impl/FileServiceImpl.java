package com.rawat.electrolok.store.services.impl;

import com.rawat.electrolok.store.exceptions.BadApiRequest;
import com.rawat.electrolok.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName: {} ",originalFilename);
        String randomUniqueFileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = randomUniqueFileName + extension;
        String fullPathWithFileName = path + File.separator + fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".gif")){

            // Save File
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
