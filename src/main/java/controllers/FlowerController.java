package controllers;

import model.Flower;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import repository.FileRepository;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/flowers")
public class FlowerController {

    private FileRepository fileRepository;

    public FlowerController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping
    public int add(@RequestBody Flower flower) {
        try {
            return fileRepository.add(flower);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<Flower> findAll() {
        try {
            return fileRepository.findAllFlowers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/search")
    public List<Flower> find(@RequestParam String answer) {
        try {
            return fileRepository.find(answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public Flower get(@PathVariable int id) {
        try {
            return fileRepository.get(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{id}/image")
    public boolean updateImage(@PathVariable int id,
                               @RequestParam MultipartFile file) {
        try {
            return fileRepository.updateImage(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        try {
            byte[] image = fileRepository.getImage(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}