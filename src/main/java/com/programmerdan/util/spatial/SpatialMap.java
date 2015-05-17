/**
 * Special map class for spatial (x,z) data with explicitly bounded (y).
 * Does all kinds of cool internal shuffling to keep lookups and searches constant time, subject
 * to the underlying hashmap. Leverages spiral index translation to convert from 2D points to integer
 * indices. Uses a Tree structure for fast y lookup.
 * Autogrows -- which does introduce potential non-constant time lookups, but still strictly speaking 
 * bounded O(ln) lookups in the very worst case.
 * 
 * @author ProgrammerDan <programmerdan@gmail.com>
 * @version 0.1-alpha
 */
public interface SpatialMap<K extends Number, V> {

	static interface SpatialMap.Entry<K,V> {
		SpatialMap.Key<K> getKey();
		V getValue();
		V setValue(V value);
	}

	static interface SpatialMap.Key<K> {
		K getX();
		K getY();
		K getZ();
	}

	void clear();

	boolean containsKey(K x, K y, K z);

	boolean containsValue(V v);

	Set<SpatialMap.Entry<K, V>> entrySet();

	V get(K x, K y, K z);

	Set<V> getNear(K x, K y, K z, K radius);

	V getClosest(K x, K y, K z);

	boolean isEmpty();

	Set<SpatialMap.Key<K>> keySet();

	V put(K x, K y, K z, V value);

	putAll(SpatialMap<? extends K, ? extends V> s);

	remove(K x, K y, K z);

	int size();

	Collection<V> values();

}
