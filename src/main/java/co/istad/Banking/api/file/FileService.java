package co.istad.Banking.api.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    /**
     * uses to upload a single file
     * file upload request form data from client
     * @return
     */
    FileDto uploadSingle(MultipartFile file);

    List<FileDto> uploadMultiple(List<MultipartFile> files);

    List<FileDto> getAllFiles();

    FileDto removeFileByName(String filename);
    boolean removeAllFile();


}
