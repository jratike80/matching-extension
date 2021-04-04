/*
 * (C) 2021 Michaël Michaud
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * For more information, contact:
 *
 * m.michael.michaud@orange.fr
 */

package fr.michaelm.jump.plugin.match.matcher;

import org.locationtech.jts.geom.Geometry;


/**
 * Matcher measuring geometry centroids distance and setting a match value of
 * 0 for distances over a distance tolerance.
 *
 * @author Michaël Michaud
 */
public class CentroidDistanceMatcher extends GeometryMatcher {
    
    private static final CentroidDistanceMatcher CENTROID_DISTANCE =
        new CentroidDistanceMatcher(1.0);
    
    public static CentroidDistanceMatcher instance() {
        return CENTROID_DISTANCE;
    }

    public CentroidDistanceMatcher(double max_dist) {
        this.max_dist = max_dist;
    }
    
    /**
     * {@inheritDoc}.
     */
    public double match(Geometry source, Geometry target, Object context)
                                                              throws Exception {
        source = source.getCentroid();
        target = target.getCentroid();
        double dist = source.distance(target);
        if (dist > max_dist) return 0.0;
        else return (1.0 - dist/max_dist);
    }
    
    /**
     * Sets the maximum distance returning a non null value.
     * @see #getMaximumDistance
     */
    public void setMaximumDistance(double max_dist) {
        if (Double.isNaN(max_dist)) return; // Never set maxDistance to NaN
        this.max_dist = max_dist;
    }
    
}
