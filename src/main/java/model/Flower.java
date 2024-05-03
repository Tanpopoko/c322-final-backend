package model;

import java.util.Arrays;

public class Flower {

    public Flower(Integer id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    private Integer id;
    private String description;
    private double price;

    public String toLine() {
        String line = String.format("%1s,%2s,%3s",
                getId(),
                getDescription().trim(),
                getPrice());
        return line;
    }

    public static Flower fromLine(String line) {
        String[] tokens = line.split(",");
        String[] choiceTokens = Arrays.copyOfRange(tokens, 3, tokens.length);
        Flower f = new Flower(Integer.parseInt(tokens[0]),
                tokens[1].trim(),
                Float.parseFloat(tokens[3]));
        return f;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

