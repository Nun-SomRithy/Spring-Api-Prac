package co.istad.Banking.api.file;

import co.istad.Banking.api.accounttype.web.AccountType;
import co.istad.Banking.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private FileUtil fileUtil;

    @Autowired
    private void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return fileUtil.upload(file);
    }


    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        List<FileDto> filesDto = new ArrayList<>();
        for (MultipartFile file : files) {
            filesDto.add(fileUtil.upload(file));
        }
        return filesDto;
    }

    @Override
    public List<FileDto> getAllFiles() {
        List<FileDto> fileDtoList = new ArrayList<>();
        File folder = new File(fileUtil.fileServerPath);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String name = file.getName();
                String url = fileUtil.fileBaseUrl + name;
                long size = file.length();
                int lastDotIndex = name.lastIndexOf(".");
                String extension = name.substring(lastDotIndex + 1);

                fileDtoList.add(new FileDto(name, url, extension, size));
            }
        }
        return fileDtoList;
    }


    @Override
    public FileDto removeFileByName(String filename) {

        FileDto fileDto = this.getAllFiles().stream().filter(
                fileDto1 -> fileDto1.name().equalsIgnoreCase(filename)).findFirst().orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "File Not Found"));

        File file = new File(fileUtil.fileServerPath, filename);
        file.delete();
        return fileDto;
    }

    @Override
    public boolean removeAllFile() {
        if (this.getAllFiles().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"File is Empty !");
        }

        for (FileDto fileDto : this.getAllFiles()) {
            this.removeFileByName(fileDto.name());
        }

        return true;
    }


}
