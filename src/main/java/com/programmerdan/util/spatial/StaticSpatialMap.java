/**
 * A Static Spatial Map implementation of SpatialMap interface.
 * This class represents a fixed portion of dimensional space,
 * at a fixed power-of-reduction to index size. It supports
 * a configurable tolerance, after which new additions to the 
 * spatial map and existing additions will be moved into 
 * static spatial maps with a lower power-of-reduction, up until
 * the "end" -- no power reduction.
 * 
 * Incoming indexes are expected to be Integers.
 * 
 * Indexes are automatically "reduced" for inner cells using 
 * modulo. Thus, inner cells don't realize they are inner cells.
 * Searches are handled in a similar fashion. Note by 
 * default searches are not euchlidean but rather axis-difference.
 * Perhaps eventually I'll make that configurable.
 *
 * @author ProgrammerDan <programmerdan@gmail.com>
 * @version 0.1-alpha
 */
public class StaticSpatialMap<V> implements SpatialMap<Integer,V> {

	/*               *
	 * Configuration *
	 *               */

	/**
	 * Default cellsize -- cellsize ^ power yields division performed
	 * on indices before insertion.
	 */
	private int cellsize = 10;

	/**
	 * Default power -- controls both the insertion division as well
	 * as if new spatial maps for subdivision will be used
	 */
	private int power = 0;

	/**
	 * Adjustment offset: floor((cellsize / 2) ^ (power + 1))
	 */
	private double preOffset = 0.0; 

	/**
	 * Modulo factor: cellsize ^ (power + 1)
	 */
	private double moduloFactor = 0.0;

	/**
	 * Division factory: cellsize ^ power
	 */
	private double reductionFactor = 0.0;

	/**
	 * Fast accessor for power < 1 test.
	 */
	private boolean subdivide = false;

	/**
	 * Each static map has a certain capacity for elements before
	 * enforcing spatial subdivision, to reduce hierarchy overhead
	 * until necessary. Change this to 0 to force hierarchy.
	 */
	private int nonSpatialCapacity = 10;

	/**
	 * Fast accessor for if mode has transitioned to subdivision
	 * spatial only (not using non-spatial store).
	 */
	private boolean fullSpatial = false;

	/*               *
	 * Data Objects  *
	 *               */

	/**
	 * Non spatial storage variable
	 */
	private HashMap<StaticSpatialMap.Key,V> nonSpatial;

	/**
	 * Spatial storage variable.
	 */
	private HashMap<Integer,SpatialMap<Integer, V>> spatial;

	/*               *
	 * Constructors  *
	 *               */

	/**
	 * Creates a new StaticSpatialMap with the given cell size and
	 * reduction power. These together determine index manipulation
	 * at this level, and the likelihood for transitioning from 
	 * list to sub-maps.
	 *
	 * @param cellsize The number of "cells" on one "side" of this map.
	 *    Total cells represented will be cellsize ^ 2.
	 *
	 * @param power The reduction applied to an index before transforming
	 *    to spiral index equivalent
	 *
	 * @param capacity The initial capacity of the map before moving from 
	 *    a simple list to a collection of static spatial maps.
	 */
	public StaticSpatialMap(int cellsize, int power, int capacity) {
		if (cellsize > 0) {
			this.cellsize = cellsize;
		}

		if (power > 0) {
			this.power = power;
		}


		if (capacity >= 0) {
			this.nonSpatialCapacity = capacity;
		}

		nonSpatial = null;
		spatial = null;

		resetInternals();

	}


	/*               *
	 *   Functions   *
	 *               */

	/**
	 * Configures the offset, modulo, reduction, subdivision, and
	 * fullSpatial mode variables. All internal settings.
	 */
	private void resetInternals() {
		preOffset = Math.pow(Math.floor( cellsize / 2.0 ), power + 1.0);
		moduloFactor = Math.pow( cellsize, power + 1.0 );
		reductionFactory = Math.pow( cellsize, power );
		subdivide = power > 0;
		fullSpatial = nonSpatialCapacity < 1;

		if (fullSpatial) {
			if (spatial == null) {
				spatial = new HashMap<Integer, SpatialMap<Integer, V>>();
			}
		} else {
			if (nonSpatial == null) {
				nonSpatial = new HashMap<StaticSpatialMap.Key, V>();
			}
		}
	}

	public V put(Integer x, Integer y, Integer z, V value) {
		
	}

	/*               *
	 *  Subclasses   *
	 *               */

	/**
	 * Key is a nice object representation of the natural indices
	 */
	public static class Key implements SpatialMap.Key<Integer>{
		private Integer x;
		private Integer y;
		private Integer z;

		private Integer i;

		public Integer getX() {
			return x;
		}

		public Integer getY() {
			return y;
		}

		public Integer getZ() {
			return z;
		}

		public Integer getIdx() {
			return i;
		}

		public Key(Integer x, Integer y, Integer z, Integer idx) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.i = idx;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Key) {
				Key objk = (Key) obj;
				if (this.i == objk.getIdx() || (this.x == objk.getX() && 
					this.y == objk.getY() && this.z == objk.getZ() ) ) {
					return true;
				}
			}
			return false;
		}

		@Override
		public int hashCode() {
			return i;
		}
	}

	/**
	 * Entry for this map
	 */
	public static class Entry<V> implements SpatialMap.Entry<Integer, V> {
		private Key<Integer> key;
		private V value;


		public StaticSpatialMap.Key<Integer> getKey() {
			return k;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
	}
}
