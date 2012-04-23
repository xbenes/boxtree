package qtree;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

/**
 * A set of spheres
 * @author Petr Benes
 */
public class SphereSet {

    // base set
    private List<Vector4f> spheres = new ArrayList<Vector4f>();

    // constants
    public static int INSIDE = 0;
    public static int INTERSECT = 1;
    public static int OUTSIDE = 2;
    
    /**
     * Add sphere to the set
     * @param sphere sphere to be added
     */
    public void addSphere(Vector4f sphere) {
        spheres.add(sphere);
    }
    
    /**
     * Get aabb of the set of spheres
     * @return bounding box
     */
    public Box getBoundingBox() {
        // create initial bounding box, and do not check that its min is less or equal to max
        Box b = new Box(
                    new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE), 
                    new Vector3f(-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE),
                    false
                );

        // update bbox with spheres
        for (Vector4f sph: spheres) {
            float radius = sph.w;
            b.updateMinMax(sph.x - radius, sph.y - radius, sph.z - radius);
            b.updateMinMax(sph.x + radius, sph.y + radius, sph.z + radius);
        }
        return b;
    }
    
    /**
     * Determine whether any of the spheres collides with the box given
     * @param b box to be tested
     * @return 
     *   SphereSet.INISIDE if the box is contained within any of the spheres
     *   SphereSet.OUTSIDE if the box does not intersect any of the spheres
     *   SphereSet.INTERSECT if the box intersects any of the spheres
     */
    public int collidesWith(Box b) {
        for (Vector4f sph : this.spheres) {
            if (b.inside(sph))
                return SphereSet.INSIDE;
            else  if (b.intersect(sph))
                return SphereSet.INTERSECT;
        }
        return SphereSet.OUTSIDE;
    }
}
