package br.com.kaikedev.productservice.Entity;



public class ProductImageEntity {

    private Integer id;

    private Integer productId;

    private String image;

    public ProductImageEntity(Integer id, Integer productId, String image) {
        this.id = id;
        this.productId = productId;
        this.image = image;
    }

    public ProductImageEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
