package model;

import java.util.List;

public class Service {
    private int serviceID;
    private String serviceName;
    private String serviceDescription;
    private Double servicePrice;
    private int serviceDuration;

    

    public Service(int serviceID, String serviceName, String serviceDescription, Double servicePrice, int serviceDuration) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
        this.serviceDuration = serviceDuration;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(int serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public void addService(String name, String description, double price, int duration) {
    }

    public void editService(int serviceID, String name, String description, double price, int duration) {
    }

    public void deleteService(int serviceID) {
    }

    public List<Service> getAllServices() {
        return null;
    }
}