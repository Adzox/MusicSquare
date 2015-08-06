package project.tddd80.keval992.liu.ida.se.navigationbase.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map for storing multiple LinkedHashMaps under given categories.
 * <p/>
 * Created by kevin on 2015-08-04.
 */
public class CategorizedLinkedHashMap<C, K, V> {

    private final Map<C, Map<K, V>> hashMap;

    public CategorizedLinkedHashMap() {
        hashMap = new LinkedHashMap<>();
    }

    public void newCategory(C category) {
        if (!hashMap.containsKey(category)) {
            hashMap.put(category, new LinkedHashMap<K, V>());
        }
    }

    public boolean hasCategory(C category) {
        return hashMap.containsKey(category);
    }

    public void removeCategory(C category) {
        hashMap.remove(category);
    }

    public V put(C category, K key, V value) {
        if (!hashMap.containsKey(category)) {
            hashMap.put(category, new LinkedHashMap<K, V>());
        }
        return hashMap.get(category).put(key, value);
    }

    public V get(C category, K key) {
        if (hashMap.containsKey(category)) {
            return hashMap.get(category).get(key);
        }
        return null;
    }

    public V remove(C category, K key, V value) {
        if (hashMap.containsKey(category)) {
            return hashMap.get(category).remove(key);
        }
        return null;
    }

    public boolean categoryHasKey(C category, K key) {
        if (hashMap.containsKey(category)) {
            return hashMap.get(category).containsKey(key);
        }
        return false;
    }

    public boolean categoryHasValue(C category, V value) {
        if (hashMap.containsKey(category)) {
            return hashMap.get(category).containsValue(value);
        }
        return false;
    }

    public boolean isCategoryEmpty(C category) {
        if (hashMap.containsKey(category)) {
            return hashMap.get(category).isEmpty();
        }
        return false;
    }

    public boolean isEmpty() {
        return hashMap.isEmpty();
    }

    public void clearCategory(C category) {
        if (hashMap.containsKey(category)) {
            hashMap.get(category).clear();
        }
    }

    public void clearAllCategories() {
        hashMap.clear();
    }

    public int sizeOfCategory(C category) {
        if (hashMap.containsKey(category)) {
            return hashMap.get(category).size();
        }
        return 0;
    }

    public int numberOfCategories() {
        return hashMap.size();
    }

    public Iterator<K> getCategoryKeysIterator(C category) {
        if (hashMap.containsKey(category)) {
            return (Iterator<K>) hashMap.get(category).keySet();
        }
        return null;
    }

    public Iterator<V> getCategoryValuesIterator(C category) {
        if (hashMap.containsKey(category)) {
            return (Iterator<V>) hashMap.get(category).values();
        }
        return null;
    }
}
