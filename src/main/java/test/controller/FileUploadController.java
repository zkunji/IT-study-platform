package test.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import test.result.Result;

import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) throws IOException {
        System.out.println(file);
        String originalFileName = file.getOriginalFilename();
        file.transferTo(new File("D:\\FileTest\\" + originalFileName));
        return Result.success(originalFileName);
    }
}
