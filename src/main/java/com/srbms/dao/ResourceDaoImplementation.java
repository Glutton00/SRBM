package com.srbms.dao;


import java.util.ArrayList;
import java.util.List;

import com.srbms.dto.Resource;
import com.srbms.util.CollectionUtil;

public class ResourceDaoImplementation implements ResourceDao {

	private List<Resource> resourceRepo = CollectionUtil.resourceRepo;

    @Override
    public boolean addResource(Resource resource) {
        if (resourceRepo.contains(resource)) {
            return false;
        }
        resourceRepo.add(resource);
        return true;
    }

    @Override
    public Resource getResourceById(String resourceId) {
        for (Resource res : resourceRepo) {
            if (res.getResourceID().equals(resourceId)) {
                System.out.println("resource found");
                return res;
            }
        }
        return null;
    }

    @Override
    public List<Resource> getAllResources() {
        return new ArrayList<>(resourceRepo);
    }

    @Override
    public boolean updateResource(Resource resource) {
        int index = resourceRepo.indexOf(resource);
        if (index != -1) {
            resourceRepo.set(index, resource);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteResource(String resourceId) {
        return resourceRepo.removeIf(res -> res.getResourceID().equals(resourceId));
    }
}

