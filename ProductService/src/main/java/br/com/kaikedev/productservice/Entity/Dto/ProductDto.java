package br.com.kaikedev.productservice.Entity.Dto;


import java.util.List;
import java.util.Objects;

public class ProductDto {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private List<String> images;

    private Integer quantity;

    public ProductDto(Integer integer,String name, String description, Double price ,List<String> images) {
        this.id = integer;
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
    }

    public ProductDto() {}

    public ProductDto(String name, String description, Double price, List<String> images, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
        this.quantity = quantity;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getImages(), that.getImages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getImages());
    }



}
