/**
 * A Static Spatial Map implementation of SpatialMap interface.
 * This class represents a fixed portion of dimensional space,
 * at a fixed power-of-reduction to index size. It supports
 * a configurable tolerance, after which new additions to the 
 * spatial map and existing additions will be moved into 
 * static spatial maps with a lower power-of-reduction, up until
 * the "end" -- no power reduction.
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
public class StaticSpatialMap<K extends Number, V> implements SpatialMap {

	private int cellsize = 10;
	private int power = 0;
	private int nonSpatialCapacity = 10;

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

		if (capacity > 0) {
			this.nonSpatialCapacity = capacity;
		}
	}
}
