package com.srbms.dao;

import java.util.List;

import com.srbms.dto.Resource;


public interface ResourceDao {
	boolean addResource(Resource resource);              // Create
    Resource getResourceById(String resourceId);         // Read one
    List<Resource> getAllResources();                    // Read all
    boolean updateResource(Resource resource);           // Update
    boolean deleteResource(String resourceId);           // Delete
}
