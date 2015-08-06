package project.tddd80.keval992.liu.ida.se.navigationbase.main;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for holding the results returned from requests.
 * <p/>
 * Created by kevin on 2015-08-04.
 */
public final class ResultsReceiver {

    private static final Map<Class, List<Serializable>> results = new LinkedHashMap<>();

    private ResultsReceiver() {
    }

    public static final void newSearch() {
        if (results != null) results.clear();
    }

    public static final void addResults(Class clazz, Serializable result) {
        if (results.containsKey(clazz)) {
            results.put(clazz, new LinkedList<Serializable>());
        }
        results.get(clazz).add(result);
    }

    public static final List<Serializable> getResults(Class clazz) {
        return results.get(clazz);
    }
}
