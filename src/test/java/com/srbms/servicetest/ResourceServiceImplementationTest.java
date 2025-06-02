package com.srbms.servicetest;

import com.srbms.service.ResourceService;
import com.srbms.service.ResourceServiceImplementation;
import com.srbms.customException.ResourceAlreadyExistsException;
import com.srbms.customException.ResourceNotFoundException;
import com.srbms.dto.Resource;
import com.srbms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResourceServiceImplementationTest {

	private static ResourceService service;

	@BeforeAll
	static void setup() {
		service = new ResourceServiceImplementation();
		CollectionUtil.resourceRepo.clear(); // ensure clean start
	}

	@Test
	@Order(1)
	void testAddResource_Success() throws Exception {
		Resource resource = new Resource("R001", "Laptop", "Electronics", 1000.0, true);
		assertTrue(service.addResource(resource));

		List<Resource> all = service.getAllResources();
		assertEquals(1, all.size());
		assertEquals("R001", all.get(0).getResourceID());
	}

	@Test
	@Order(2)
	void testAddResource_AlreadyExists() {
		Resource duplicate = new Resource("R001", "Laptop", "Electronics", 1000.0, true);
		assertThrows(ResourceAlreadyExistsException.class, () -> service.addResource(duplicate));
	}

	@Test
	@Order(3)
	void testGetResource_Success() throws Exception {
		Resource fetched = service.getResource("R001");
		assertNotNull(fetched);
		assertEquals("Laptop", fetched.getResourceName());
	}

	@Test
	@Order(4)
	void testGetResource_NotFound() {
		assertThrows(ResourceNotFoundException.class, () -> service.getResource("R404"));
	}

	@Test
	@Order(5)
	void testUpdateResource_Success() throws Exception {
		// First add R101
		Resource original = new Resource("R101", "Projector", "Electronics", 500.0, true);
		assertTrue(service.addResource(original));

		// Now update R101
		Resource updated = new Resource("R101", "Old Projector", "Electronics", 450.0, true);
		assertTrue(service.updateResource(updated));

		Resource fetched = service.getResource("R101");
		assertEquals("Old Projector", fetched.getResourceName());
		assertEquals(450.0, fetched.getResourceCost());
	}

	@Test
	@Order(6)
	void testUpdateResource_NotFound() {
		Resource updated = new Resource("R999", "Smartwatch", "Electronics", 200.0, true);
		assertThrows(ResourceNotFoundException.class, () -> service.updateResource(updated));
	}

	@Test
	@Order(7)
	void testRemoveResource_Success() throws Exception {
		assertTrue(service.removeResource("R001"));
		assertThrows(ResourceNotFoundException.class, () -> service.getResource("R001"));
	}

	@Test
	@Order(8)
	void testRemoveResource_NotFound() {
		assertThrows(ResourceNotFoundException.class, () -> service.removeResource("R001")); // already deleted
	}
}
