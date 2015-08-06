package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kevin on 2015-07-08.
 */
class ModelCache<Model extends Serializable> {

    public static final int CACHE_SIZE = 100;

    private final HashMap<Integer, Model> modelHashMap;
    private final ArrayList<Integer> idOrderList;
    private final int maximumCacheSize;

    public ModelCache(int maximumCacheSize) {
        this.maximumCacheSize = maximumCacheSize;
        idOrderList = new ArrayList<>();
        modelHashMap = new HashMap<>();
    }

    /**
     * Puts a model to the cache. If a model is already cached under an id, the model is updated.
     * If however the cache is full, the first model added to the cache is removed before adding
     * the new model to the cache.
     *
     * @param id
     * @param model
     */
    public void put(int id, Model model) {
        if (modelHashMap.containsKey(id)) {
            idOrderList.remove(id);
            modelHashMap.put(id, model);
            idOrderList.add(id);
        } else if (modelHashMap.size() + 1 < maximumCacheSize) {
            modelHashMap.put(id, model);
            idOrderList.add(id);
        } else {
            modelHashMap.remove(idOrderList.get(0));
            idOrderList.remove(0);
            // IF OVERFLOW OCCUR, SWITCH BELOW LINES TO REPEAT THE ADD METHOD.
            modelHashMap.put(id, model);
            idOrderList.add(id);
        }
    }

    /**
     * Returns the model from the cache by the cached id of the model.
     *
     * @param id The id of the model to retrieve.
     * @return The model or null if no value was found under the given id.
     */
    public Model get(int id) {
        return modelHashMap.get(id);
    }

    /**
     * Clears the cache of all its entries.
     */
    public void clearCache() {
        modelHashMap.clear();
        idOrderList.clear();
    }
}
