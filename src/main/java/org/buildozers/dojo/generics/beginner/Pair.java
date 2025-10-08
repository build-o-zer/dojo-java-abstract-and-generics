package org.buildozers.dojo.generics.beginner;

/**
 * A basic pair class that holds two values.
 * Currently uses Object types - needs to be converted to generic.
 * 
 * KATA TASK: Convert to generic Pair<T, U> class.
 */
public class Pair {
    private Object first;
    private Object second;
    
    public Pair(Object first, Object second) {
        this.first = first;
        this.second = second;
    }
    
    public Object getFirst() {
        return first;
    }
    
    public void setFirst(Object first) {
        this.first = first;
    }
    
    public Object getSecond() {
        return second;
    }
    
    public void setSecond(Object second) {
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
        
        Pair pair = (Pair) obj;
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
