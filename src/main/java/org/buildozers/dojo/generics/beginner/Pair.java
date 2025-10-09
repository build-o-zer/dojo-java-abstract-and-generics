package org.buildozers.dojo.generics.beginner;

/**
 * A basic pair class that holds two values.
 * Currently uses Object types - needs to be converted to generic.
 * 
 * KATA TASK: Convert to generic Pair<T, U> class.
 */
public class Pair<T, U> {
    private T first;
    private U second;
    
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
    
    public T getFirst() {
        return first;
    }
    
    public void setFirst(T first) {
        this.first = first;
    }
    
    public U getSecond() {
        return second;
    }
    
    public void setSecond(U second) {
        this.second = second;
    }
    
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        @SuppressWarnings("unchecked")
		Pair<T,U> pair = (Pair<T,U>) obj;
        return (first != null ? first.equals(pair.first) : pair.first == null) &&
               (second != null ? second.equals(pair.second) : pair.second == null);
    }
    
    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
