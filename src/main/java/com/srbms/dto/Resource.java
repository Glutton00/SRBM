package com.srbms.dto;
import java.util.Objects;

public class Resource {
    private String resourceID;
    private String resourceName;
    private String resourceType;
    private double resourceCost;
    private boolean resourceIsAvailable;

    // Constructor
    public Resource(String resourceID, String resourceName, String resourceType, double resourceCost, boolean resourceIsAvailable) {
        this.resourceID = resourceID;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceCost = resourceCost;
        this.resourceIsAvailable = resourceIsAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Double.compare(resourceCost, resource.resourceCost) == 0 && resourceIsAvailable == resource.resourceIsAvailable && Objects.equals(resourceID, resource.resourceID) && Objects.equals(resourceName, resource.resourceName) && Objects.equals(resourceType, resource.resourceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceID, resourceName, resourceType, resourceCost, resourceIsAvailable);
    }

    // Getters and Setters
    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public double getResourceCost() {
        return resourceCost;
    }

    public void setResourceCost(double resourceCost) {
        this.resourceCost = resourceCost;
    }

    public boolean isResourceIsAvailable() {
        return resourceIsAvailable;
    }

    public void setResourceIsAvailable(boolean resourceIsAvailable) {
        this.resourceIsAvailable = resourceIsAvailable;
    }

    @Override
    public String toString() {
        return
                "resourceID:" + resourceID +
                ", Name='" + resourceName +
                ", Type='" + resourceType +
                ", resource Cost per day=" + resourceCost +
                ", resourceIsAvailable=" + resourceIsAvailable;
    }
}
