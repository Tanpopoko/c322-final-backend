package repository;

import model.Flower;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileRepository {
    private String IMAGES_FOLDER_PATH = "flowers/images";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String FLOWER_DATABASE_NAME = "flowers.txt";

    public FileRepository() {
        File imagesDirectory = new File(IMAGES_FOLDER_PATH);
        if(!imagesDirectory.exists()) {
            imagesDirectory.mkdirs();
        }
    }

    private static void appendToFile(Path path, String content)
            throws IOException {
        Files.write(path,
                content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public int add(Flower flower) throws IOException {
        Path path = Paths.get(FLOWER_DATABASE_NAME);
        List<Flower> flowers = findAllFlowers();
        int id = 0;
        for(Flower f : flowers) {
            if(f.getId() > id) {
                id = f.getId();
            }
        }
        id = id + 1;
        String data = flower.toLine();
        appendToFile(path, data + NEW_LINE);
        return id;
    }

    public List<Flower> findAllFlowers() throws IOException {
        List<Flower> result = new ArrayList<>();
        Path path = Paths.get(FLOWER_DATABASE_NAME);
        if (Files.exists(path)) {
            List<String> data = Files.readAllLines(path);
            for (String line : data) {
                if(line.trim().length() != 0) {
                    Flower q = Flower.fromLine(line);
                    result.add(q);
                }
            }
        }
        return result;
    }



    public List<Flower> find(String description) throws IOException {
        List<Flower> flowers = findAllFlowers();
        List<Flower> result = new ArrayList<>();
        for (Flower flower : flowers) {
            if (description != null && !flower.getDescription().trim().equalsIgnoreCase(description.trim())) {
                continue;
            }
            result.add(flower);
        }
        return result;
    }

    public List<Flower> find(List<Integer> ids) throws IOException {
        List<Flower> flowers = findAllFlowers();
        List<Flower> result = new ArrayList<>();
        for (int id : ids) {
            Flower q = flowers.stream().filter(x -> x.getId() == id).toList().get(0);
            result.add(q);
        }
        return result;
    }



    public Flower get(Integer id) throws IOException {
        List<Flower> flowers = findAllFlowers();
        for (Flower flower : flowers) {
            if (flower.getId() == id) {
                return flower;
            }
        }
        return null;
    }

    public boolean updateImage(int id, MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());

        String fileExtension = ".png";
        Path path = Paths.get(IMAGES_FOLDER_PATH
                + "/" + id + fileExtension);
        System.out.println("The file " + path + " was saved successfully.");
        file.transferTo(path);
        return true;
    }

    public byte[] getImage(int id) throws IOException {
        String fileExtension = ".png";
        Path path = Paths.get(IMAGES_FOLDER_PATH
                + "/" + id + fileExtension);
        byte[] image = Files.readAllBytes(path);
        return image;
    }

}