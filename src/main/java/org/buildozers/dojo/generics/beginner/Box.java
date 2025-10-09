package org.buildozers.dojo.generics.beginner;

/**
 * A simple non-generic box that can only store Object types.
 * This leads to type safety issues and requires casting.
 * 
 * KATA TASK: Convert this to a generic Box<T> class for type safety.
 */
public class Box<T> {
    private T item;
    
    public void setItem(T item) {
        this.item = item;
    }
    
    public T getItem() {
        return item;
    }
    
    public boolean isEmpty() {
        return item == null;
    }
    
    public void clear() {
        item = null;
    }
}
