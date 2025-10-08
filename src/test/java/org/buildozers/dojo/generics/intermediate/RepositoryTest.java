package org.buildozers.dojo.generics.intermediate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Repository with generic bounds and wildcards.
 * Tests show the progression from unsafe to type-safe operations.
 */
class RepositoryTest {
    
    private Repository repository;
    
    @BeforeEach
    void setUp() {
        repository = new Repository();
    }
    
    @Test
    void testBasicOperations() {
        assertTrue(repository.isEmpty());
        assertEquals(0, repository.size());
        
        repository.add("Test item");
        assertFalse(repository.isEmpty());
        assertEquals(1, repository.size());
        
        repository.clear();
        assertTrue(repository.isEmpty());
    }
    
    @Test
    void testUnsafeOperations() {
        // Current implementation allows any type
        repository.add("String");
        repository.add(123);
        repository.add(new Person(1L, "John", 30));
        
        assertEquals(3, repository.size());
        
        // This requires casting and is not type safe
        Object item = repository.findById(1L);
        assertNotNull(item);
    }
    
    @Test
    void testCollectionOperations() {
        List<Object> items = Arrays.asList("A", "B", "C");
        repository.addAll(items);
        assertEquals(3, repository.size());
        
        List<Object> result = repository.findAll();
        assertEquals(3, result.size());
        
        List<Object> destination = new ArrayList<>();
        repository.copyTo(destination);
        assertEquals(3, destination.size());
    }
    
    // TODO: After implementing generics, these tests should work:
    /*
    @Test
    void testGenericRepository() {
        Repository<Person> personRepo = new Repository<>();
        Person person = new Person(1L, "Alice", 25);
        
        personRepo.add(person);
        Person found = personRepo.findById(1L); // No casting!
        assertEquals("Alice", found.getName());
    }
    
    @Test
    void testBoundedWildcards() {
        Repository<Person> personRepo = new Repository<>();
        List<Person> people = Arrays.asList(
            new Person(1L, "Alice", 25),
            new Person(2L, "Bob", 30)
        );
        
        personRepo.addAll(people); // Should accept List<Person>
        
        List<? super Person> destination = new ArrayList<Object>();
        personRepo.copyTo(destination); // PECS: Consumer Super
    }
    */
}
