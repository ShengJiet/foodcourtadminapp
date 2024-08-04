package my.edu.utar.foodcourtadmin;

public class MainModelDrink {

    private Boolean BestDrink;
    private int CategoryId;
    private String Description;
    private String ImagePath;
    private int LocationId;
    private double Price;
    private int PriceId;
    private double Star;
    private int TimeId;
    private int TimeValue;
    private String Title;

    MainModelDrink(){

    }

    public MainModelDrink(Boolean bestDrink, int categoryId, String description, String imagePath, int locationId, double price, int priceId, double star, int timeId, int timeValue, String title) {
        BestDrink = bestDrink;
        CategoryId = categoryId;
        Description = description;
        ImagePath = imagePath;
        LocationId = locationId;
        Price = price;
        PriceId = priceId;
        Star = star;
        TimeId = timeId;
        TimeValue = timeValue;
        Title = title;
    }

    public Boolean getBestDrink() {
        return BestDrink;
    }

    public void setBestDrink(Boolean bestDrink) {
        BestDrink = bestDrink;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getPriceId() {
        return PriceId;
    }

    public void setPriceId(int priceId) {
        PriceId = priceId;
    }

    public double getStar() {
        return Star;
    }

    public void setStar(double star) {
        Star = star;
    }

    public int getTimeId() {
        return TimeId;
    }

    public void setTimeId(int timeId) {
        TimeId = timeId;
    }

    public int getTimeValue() {
        return TimeValue;
    }

    public void setTimeValue(int timeValue) {
        TimeValue = timeValue;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
