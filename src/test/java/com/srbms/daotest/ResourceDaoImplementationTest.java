package com.srbms.daotest;

import com.srbms.dao.ResourceDao;
import com.srbms.dao.ResourceDaoImplementation;
import com.srbms.dto.Resource;
import com.srbms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResourceDaoImplementationTest {

    private static ResourceDao dao;

    @BeforeAll
    static void init() {
        dao = new ResourceDaoImplementation();
        CollectionUtil.resourceRepo.clear();
    }

    @Test
    @Order(1)
    void testAddResource_Success() {
        Resource res = new Resource("R001", "Laptop", "Electronics", 1000.0, true);
        assertTrue(dao.addResource(res));

        List<Resource> allResources = dao.getAllResources();
        assertEquals(1, allResources.size());
        assertEquals("R001", allResources.get(0).getResourceID());
    }

    @Test
    @Order(2)
    void testAddResource_Duplicate() {
        Resource duplicate = new Resource("R001", "Laptop", "Electronics", 1000.0, true);
        assertFalse(dao.addResource(duplicate));
    }

    @Test
    @Order(3)
    void testGetResourceById_Exists() {
        Resource found = dao.getResourceById("R001");
        assertNotNull(found);
        assertEquals("Laptop", found.getResourceName());
    }

    @Test
    @Order(4)
    void testGetResourceById_NotExists() {
        Resource notFound = dao.getResourceById("R999");
        assertNull(notFound);
    }

    @Test
    @Order(5)
    void testUpdateResource_Success() {
        Resource updated = new Resource("R001", "Laptop Pro", "Electronics", 1200.0, false);
        assertTrue(dao.updateResource(updated));

        Resource fetched = dao.getResourceById("R001");
        assertEquals("Laptop Pro", fetched.getResourceName());
        assertEquals(1200.0, fetched.getResourceCost());
        assertFalse(fetched.isResourceIsAvailable());
    }

    @Test
    @Order(6)
    void testUpdateResource_NotExists() {
        Resource nonExisting = new Resource("R999", "Tablet", "Electronics", 500.0, true);
        assertFalse(dao.updateResource(nonExisting));
    }

    @Test
    @Order(7)
    void testDeleteResource_Success() {
        assertTrue(dao.deleteResource("R001"));
        assertNull(dao.getResourceById("R001"));
    }

    @Test
    @Order(8)
    void testDeleteResource_NotExists() {
        assertFalse(dao.deleteResource("R001")); // Already deleted
    }
}
