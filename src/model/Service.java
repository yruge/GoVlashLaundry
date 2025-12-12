package model;

public class Service {
    private String serviceId;
    private String name;
    private String description;
    private double price;
    private int durationDays;

    public Service() {}

    public Service(String serviceId, String name, String description, double price, int durationDays) {
        this.serviceId = serviceId; this.name = name; this.description = description;
        this.price = price; this.durationDays = durationDays;
    }

    // getters & setters
    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getDurationDays() { return durationDays; }
    public void setDurationDays(int durationDays) { this.durationDays = durationDays; }
}
