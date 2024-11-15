package com.steven.chameleon.core.util;

import java.util.*;

/**
 * A utility class that provides methods for collection operations.
 * This class aims to simplify collection handling by offering a set of static methods for common tasks.
 */
public class CollectionUtils {
    /**
     * Determines whether the given collection is null or empty.
     * <p>
     * This method simplifies the process of checking if a collection is null or empty by encapsulating the logic into a single call.
     * It provides a consistent way to check the emptiness of a collection without repeating the same check logic throughout the code.
     *
     * @param collection The collection to be checked for emptiness. It can be any instance that implements the Collection interface.
     * @return true if the collection is null or contains no elements; false otherwise.
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }


    /**
     * Checks if the given collection is not empty.
     * This method provides a convenient way to check if a collection has at least one element.
     * It achieves this by calling the isEmpty method and negating the result.
     * The primary purpose is to improve code readability, avoiding direct use of negative condition checks.
     *
     * @param collection The collection to be checked, which can be any collection that implements the Collection interface.
     * @return true if the collection is not empty; false otherwise.
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }


    /**
     * Checks if the given Map object is empty.
     * <p>
     * This method determines whether the specified Map object is null or empty.
     * It is primarily used to quickly validate the state of a Map object before performing further operations.
     *
     * @param map The Map object to be checked, which can be a mapping of any type of key-value pairs.
     * @return true if the Map object is null or empty; otherwise, false.
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }


    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    public static <T> Set<T> emptyIfNull(Set<T> set) {
        return set == null ? Collections.emptySet() : set;
    }
} 