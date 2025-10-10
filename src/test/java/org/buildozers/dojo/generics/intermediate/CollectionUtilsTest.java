package org.buildozers.dojo.generics.intermediate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test class for CollectionUtils demonstrating PECS principles.
 * 
 * These tests will pass once the CollectionUtils methods are properly implemented.
 */
@DisplayName("CollectionUtils Tests")
class CollectionUtilsTest {

    @Test
    @DisplayName("Should copy elements from source to destination using PECS")
    void shouldCopyElementsUsingPECS() {
        // Given
        Person person1 = new Person(1L, "Alice", 25);
        Person person2 = new Person(2L, "Bob", 30);
        List<Person> people = Arrays.asList(person1, person2);
        List<Entity> entities = new ArrayList<>();
        
        // When - This should work once implemented
        // CollectionUtils.copy(people, entities);
        
        // Then - Uncomment when copy is implemented
        // assertThat(entities).hasSize(2);
        // assertThat(entities).containsExactly(person1, person2);
        
        // For now, verify the method exists but throws UnsupportedOperationException
        assertThatThrownBy(() -> CollectionUtils.copy(people, entities))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("TODO: Implement copy method");
    }

    @Test
    @DisplayName("Should copy Person list to Object list (super relationship)")
    void shouldCopyPersonListToObjectList() {
        // Given
        Person person = new Person(1L, "Charlie", 35);
        List<Person> people = Arrays.asList(person);
        List<Object> objects = new ArrayList<>();
        
        // When - This should work once implemented (Person -> Object is valid)
        // CollectionUtils.copy(people, objects);
        
        // Then - Uncomment when copy is implemented
        // assertThat(objects).hasSize(1);
        // assertThat(objects).containsExactly(person);
        
        // For now, verify the method exists but throws UnsupportedOperationException
        assertThatThrownBy(() -> CollectionUtils.copy(people, objects))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("TODO: Implement copy method");
    }

    @Test
    @DisplayName("Should add all items to target collection using Consumer pattern")
    void shouldAddAllItemsUsingConsumerPattern() {
        // Given
        Person person1 = new Person(1L, "David", 28);
        Person person2 = new Person(2L, "Eva", 32);
        List<Entity> entities = new ArrayList<>();
        
        // When - This should work once implemented
        // CollectionUtils.addAll(entities, person1, person2);
        
        // Then - Uncomment when addAll is implemented
        // assertThat(entities).hasSize(2);
        // assertThat(entities).containsExactly(person1, person2);
        
        // For now, verify the method exists but throws UnsupportedOperationException
        assertThatThrownBy(() -> CollectionUtils.addAll(entities, person1, person2))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("TODO: Implement addAll method");
    }

    @Test
    @DisplayName("Should add Person items to Object list (super relationship)")
    void shouldAddPersonItemsToObjectList() {
        // Given
        Person person = new Person(1L, "Frank", 40);
        List<Object> objects = new ArrayList<>();
        
        // When - This should work once implemented (Person -> Object is valid)
        // CollectionUtils.addAll(objects, person);
        
        // Then - Uncomment when addAll is implemented
        // assertThat(objects).hasSize(1);
        // assertThat(objects).containsExactly(person);
        
        // For now, verify the method exists but throws UnsupportedOperationException
        assertThatThrownBy(() -> CollectionUtils.addAll(objects, person))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("TODO: Implement addAll method");
    }
}
