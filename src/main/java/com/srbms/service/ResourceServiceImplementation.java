package com.srbms.service;

import com.srbms.customException.ResourceAlreadyExistsException;
import com.srbms.customException.ResourceNotFoundException;
import com.srbms.dao.ResourceDao;
import com.srbms.dao.ResourceDaoImplementation;
import com.srbms.dto.Resource;

import java.util.List;
import java.util.Scanner;

public class ResourceServiceImplementation implements ResourceService {

    private final ResourceDao resourceDao = new ResourceDaoImplementation();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public boolean addResource(Resource newResource) throws ResourceAlreadyExistsException {
        // Check if resource already exists
        List<Resource> allResources = resourceDao.getAllResources();
        for (Resource r : allResources) {
            if (r.getResourceID().equalsIgnoreCase(newResource.getResourceID())) {
                throw new ResourceAlreadyExistsException("Resource with ID " + newResource.getResourceID() + " already exists.");
            }
        }

        boolean added = resourceDao.addResource(newResource);
        if (added) {
            System.out.println("Resource added successfully.");
        } else {
            System.out.println("Failed to add resource.");
        }
        return added;
    }

    @Override
    public Resource getResource(String resourceId) throws ResourceNotFoundException {
        Resource resource = resourceDao.getResourceById(resourceId);
        if (resource == null) {
            throw new ResourceNotFoundException("Resource with ID " + resourceId + " not found.");
        }
        return resource;
    }

    @Override
    public List<Resource> getAllResources() {
        return resourceDao.getAllResources();
    }

    @Override
    public boolean removeResource(String resourceId) throws ResourceNotFoundException {
        boolean removed = resourceDao.deleteResource(resourceId);
        if (!removed) {
            throw new ResourceNotFoundException("Cannot delete. Resource with ID " + resourceId + " not found.");
        }
        return true;
    }

    @Override
    public boolean updateResource(Resource updatedResource) throws ResourceNotFoundException {
        boolean updated = resourceDao.updateResource(updatedResource);
        if (!updated) {
            throw new ResourceNotFoundException("Cannot update. Resource with ID " + updatedResource.getResourceID() + " not found.");
        }
        return true;
    }

}


