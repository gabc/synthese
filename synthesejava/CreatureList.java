package synthesejava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CreatureList<T extends Creature> implements List, Iterable {
    private List<T> liste;
    
    public CreatureList() {
        super();
        this.liste = new ArrayList<T>();
    }

    @Override
    public int size() {
        return this.liste.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return this.liste.contains((T)o);
    }

    @Override
    public Iterator iterator() {
        return this.liste.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.liste.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return (Creature[])new Object[0];
    }

    @Override
    public boolean add(Object e) {
        return this.liste.add((T)e);
    }

    @Override
    public void add(int index, Object element) {
        this.liste.add(index, (T)element);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public Object remove(int index) {
        return (Creature)null;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return this.liste.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public Object get(int index) {
        return (Creature)this.liste.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        return (Creature)null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return this.liste.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return this.liste.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return this.liste.subList(fromIndex, toIndex);
    }

    public T getCreature(int x, int y) {
        for(T c : liste){
            if(c.getTaille().getX() == x && c.getTaille().getY() == y){
                return c;
            }
        }
        return null;
    }
}
