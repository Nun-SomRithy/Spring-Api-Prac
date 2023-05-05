package co.istad.Banking.api.file;

import co.istad.Banking.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;
    @PostMapping
    public BaseRest<?> uploadSingle(@RequestPart("file") MultipartFile file) {
        FileDto fileDto=fileService.uploadSingle(file);
        return BaseRest.builder().status(true)
                .code(HttpStatus.OK.value())
                .message("File have been Uploaded")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }

    @PostMapping("/upload-multiple")
    public BaseRest<?> uploadMultiple(@RequestPart("files") List<MultipartFile> files) {
        List<FileDto> fileDto=fileService.uploadMultiple(files);
        return BaseRest.builder().status(true)
                .code(HttpStatus.OK.value())
                .message("File have been Uploaded")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }


    @GetMapping
    public BaseRest<?> getListOfFile(){
        List<FileDto> fileDtos = fileService.getAllFiles();
        if (fileDtos.isEmpty()) {
            System.out.println("No files found.");
        }
        return BaseRest.builder().status(true)
                .code(HttpStatus.OK.value())
                .message("File have been get *")
                .timestamp(LocalDateTime.now())
                .data(fileDtos)
                .build();
    }

    @DeleteMapping("/{fileName}")
    public  BaseRest<?> removeFileByName(@PathVariable String fileName){
         FileDto fileDto = fileService.removeFileByName(fileName);

        return BaseRest.builder().status(true)
                .code(HttpStatus.OK.value())
                .message("File have been Removed !")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }


    @DeleteMapping
    public BaseRest<?> removeAllFile(){
        boolean fileDto= fileService.removeAllFile();
        return BaseRest.builder().status(true)
                .code(HttpStatus.OK.value())
                .message("File have been Removed !")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }



}
