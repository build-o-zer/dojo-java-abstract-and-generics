package org.buildozers.dojo.generics.intermediate;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository pattern without proper generic bounds.
 * 
 * KATA TASK: Add proper generic bounds and wildcards for type safety.
 * - Use bounded type parameters where appropriate
 * - Use wildcards for collections
 * - Implement proper PECS (Producer Extends, Consumer Super) pattern
 */
public class Repository {
    private List<Object> items = new ArrayList<>();
    
    public void add(Object item) {
        items.add(item);
    }
    
    public Object findById(Long id) {
        // Simplified - in real scenario would search by ID
        return items.isEmpty() ? null : items.get(0);
    }
    
    public List<Object> findAll() {
        return new ArrayList<>(items);
    }
    
    public void addAll(List<Object> newItems) {
        items.addAll(newItems);
    }
    
    public void copyTo(List<Object> destination) {
        destination.addAll(items);
    }
    
    public int size() {
        return items.size();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public void clear() {
        items.clear();
    }
}
