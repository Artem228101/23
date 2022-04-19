package sample.database;

public class FilmsData {
    private int id;
    private String type;
    private String price;
    private String address;
    private String description;

    public FilmsData(int id, String type, String price, String address, String description) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.address = address;
        this.description = description;
    }

    public FilmsData(){}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public String getPrice() {return price;}
    public void setPrice(String price) {this.price = price;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
}
