package com.srbms.service ;

import com.srbms.customException.ResourceAlreadyExistsException;
import com.srbms.customException.ResourceNotFoundException;
import com.srbms.dto.Resource;

import java.util.List;

public interface ResourceService {

    boolean addResource(Resource resource) throws ResourceAlreadyExistsException; // Input will be taken inside implementation

    Resource getResource(String resourceId) throws ResourceNotFoundException;

    List<Resource> getAllResources();

    boolean removeResource(String resourceId) throws ResourceNotFoundException;

    boolean updateResource(Resource updatedResource) throws ResourceNotFoundException;
}