package model;

public class Service {
    private int serviceId;
    private String serviceName;
    private String serviceDescription;
    private double servicePrice;
    private int serviceDuration;
    
    // Constructor
    public Service(int serviceId, String serviceName, String serviceDescription, 
                   double servicePrice, int serviceDuration) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
        this.serviceDuration = serviceDuration;
    }
    
    // Constructor untuk insert baru (tanpa ID)
    public Service(String serviceName, String serviceDescription, 
                   double servicePrice, int serviceDuration) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
        this.serviceDuration = serviceDuration;
    }
    
    // VALIDATION METHODS (sesuai requirement)
    public boolean validateServiceName() {
        return serviceName != null && !serviceName.trim().isEmpty() && serviceName.length() <= 50;
    }
    
    public boolean validateServiceDescription() {
        return serviceDescription != null && !serviceDescription.trim().isEmpty() && serviceDescription.length() <= 250;
    }
    
    public boolean validateServicePrice() {
        return servicePrice > 0;
    }
    
    public boolean validateServiceDuration() {
        return serviceDuration >= 1 && serviceDuration <= 30;
    }
    
    public boolean validateAll() {
        return validateServiceName() && validateServiceDescription() && 
               validateServicePrice() && validateServiceDuration();
    }
    
    // Getters & Setters
    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public String getServiceDescription() { return serviceDescription; }
    public void setServiceDescription(String serviceDescription) { this.serviceDescription = serviceDescription; }
    
    public double getServicePrice() { return servicePrice; }
    public void setServicePrice(double servicePrice) { this.servicePrice = servicePrice; }
    
    public int getServiceDuration() { return serviceDuration; }
    public void setServiceDuration(int serviceDuration) { this.serviceDuration = serviceDuration; }
    
    @Override
    public String toString() {
        return serviceName + " - Rp" + servicePrice + " (" + serviceDuration + " days)";
    }
}