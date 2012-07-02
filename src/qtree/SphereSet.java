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
     * @return the collide result. If there is intersection (not complete inclusion nor outside)
     *         the subset of spheres which participate in the intersection are returned
     */
    public CollideResult collidesWith(Box b) {

        // outside by default
        int state = CollideResult.OUTSIDE;
        SphereSet ss = new SphereSet();

        for (Vector4f sph : this.spheres) {
            if (b.inside(sph)) {
                state = CollideResult.INSIDE;
            } else if (b.intersectSphere(sph) && state != CollideResult.INSIDE) {
                state = CollideResult.INTERSECT;
                ss.addSphere(sph);
            }
        }

        CollideResult cr = new CollideResult(state, ss);
        return cr;
    }
}
