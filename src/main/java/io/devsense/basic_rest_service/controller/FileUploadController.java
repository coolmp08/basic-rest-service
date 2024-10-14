package io.devsense.basic_rest_service.controller;

import io.devsense.basic_rest_service.exception.StorageFileNotFoundException;
import io.devsense.basic_rest_service.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    public FileUploadController(StorageService storageService){
        this.storageService = storageService;
    }

    @RequestMapping(value = "/loadall",method = RequestMethod.GET)
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serveFile",
                        path.getFileName().toString()).build().toUri().toString()
        ).collect(Collectors.toList()));

        return "uploadForm";
    }

    @RequestMapping(value = "/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){

        Resource file = storageService.LoadAsResource(filename);

        if(file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFilename() + "\"").body(file);

    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file")MultipartFile multipartFile, RedirectAttributes redirectAttributes){

        storageService.store(multipartFile);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + multipartFile.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

}
