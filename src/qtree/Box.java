/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qtree;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

/**
 * Box class, defined by two vertices min & max
 * @author Petr Benes
 */
public class Box {
    private Vector3f min = new Vector3f();
    private Vector3f max = new Vector3f();

    public Vector3f getMin() { return min; }
    public Vector3f getMax() { return max; }

    /**
     * Init box and DO check that min is less or equal to max
     * @param min min corner
     * @param max max corner
     */
    public Box(Vector3f min, Vector3f max) {
        this(min, max, true);
    }
    /**
     * Init box. Optionally check the validity of the box
     * @param min min vertex
     * @param max max vertex
     * @param check check validity of the bounding box
     */
    public Box(Vector3f min, Vector3f max, boolean check) {
        this.min = min;
        this.max = max;
        if (check) this.check();
    }

    /**
     * Perform bounding box validity check (min <= max)
     */
    private void check() {
        // TODO: assert
        if (this.max.x <= this.min.x || this.max.y <= this.min.y || this.max.z <= this.min.z) {
            System.err.println("!! Warning: bad bbox " + this.min.toString() + " " + this.max.toString());
        }
    }

    /**
     * Update box min/max with given x/y/z coordinates
     * @param x x
     * @param y y
     * @param z z
     */
    public void updateMinMax(float x, float y, float z) {
        min.x = Math.min(x, min.x);
        min.y = Math.min(y, min.y);
        min.z = Math.min(z, min.z);

        max.x = Math.max(x, max.x);
        max.y = Math.max(y, max.y);
        max.z = Math.max(z, max.z);
    }

    /**
     * Point-inside box test
     * @param x point x
     * @param y point y
     * @param z point z
     * @return true if the point is inside a box
     */
    public boolean pointInside(float x, float y, float z) {
        return x <= max.x && y <= max.y && z <= max.z &&
               x >= min.x && y >= min.y && z >= min.z;
    }

    /**
     * Split a box into eight boxes in the middle of each plane
     */
    public Box[] split8() {
        Box[] result = new Box[8];

        float middlex = (min.x + max.x) / 2.f;
        float middley = (min.y + max.y) / 2.f;
        float middlez = (min.z + max.z) / 2.f;

        Vector3f a1 = min;
        Vector3f a2 = new Vector3f(middlex, middley, middlez);

        Vector3f b1 = new Vector3f(middlex, min.y, min.z);
        Vector3f b2 = new Vector3f(max.x, middley, middlez);

        Vector3f c1 = new Vector3f(min.x, middley, min.z);
        Vector3f c2 = new Vector3f(middlex, max.y, middlez);

        Vector3f d1 = new Vector3f(middlex, middley, min.z);
        Vector3f d2 = new Vector3f(max.x, max.y, middlez);

        Vector3f e1 = new Vector3f(min.x, min.y, middlez);
        Vector3f e2 = new Vector3f(middlex, middley, max.z);

        Vector3f f1 = new Vector3f(middlex, min.y, middlez);
        Vector3f f2 = new Vector3f(max.x, middley, max.z);

        Vector3f g1 = new Vector3f(min.x, middley, middlez);
        Vector3f g2 = new Vector3f(middlex, max.y, max.z);

        Vector3f h1 = a2;
        Vector3f h2 = max;

        result[0] = new Box(a1, a2);
        result[1] = new Box(b1, b2);
        result[2] = new Box(c1, c2);
        result[3] = new Box(d1, d2);
        result[4] = new Box(e1, e2);
        result[5] = new Box(f1, f2);
        result[6] = new Box(g1, g2);
        result[7] = new Box(h1, h2);

        return result;
    }

    /**
     * Test whether the box is fully contained within a sphere
     * @param sphere sphere to be tested
     * @return true if the full box is inside the sphere, false otherwise
     */
    public boolean inside(Vector4f sphere) {
        float radius = sphere.w;
        Vector3f minSph = new Vector3f(this.min.x - sphere.x, this.min.y - sphere.y, this.min.z - sphere.z);
        Vector3f maxSph = new Vector3f(this.max.x - sphere.x, this.max.y - sphere.y, this.max.z - sphere.z);
        if (minSph.length() < radius && maxSph.length() < radius)
            return true;
        else
            return false;
    }
    /**
     * Test whether the box pointInside a sphere
     * @param sphere sphere to be tested
     * @return true if there is an intersection, false otherwise
     */
    public boolean intersectSphere(Vector4f sphere) {
        // whether sphere center is in the box
        if(this.pointInside(sphere.x, sphere.y, sphere.z)) {
             return true;
        }

        // get closest point on box from sphere centre
        float closestPointX = (sphere.x < min.x)? min.x : (sphere.x > max.x)? max.x : sphere.x;
        float closestPointY = (sphere.y < min.y)? min.y : (sphere.y > max.y)? max.y : sphere.y;
        float closestPointZ = (sphere.z < min.z)? min.z : (sphere.z > max.z)? max.z : sphere.z;


        // find the separation
        float xDiffX = sphere.x - closestPointX;
        float xDiffY = sphere.y - closestPointY;
        float xDiffZ = sphere.z - closestPointZ;

        // check if points are far enough, length squared sufficient for comparison
        float fDistSquared = (xDiffX * xDiffX + xDiffY * xDiffY + xDiffZ * xDiffZ);

        if (fDistSquared > (sphere.w * sphere.w)) {
            return false;
        }
        return true;

        //float fDist = (float) Math.sqrt(fDistSquared);
        // collision depth
        //float fDcoll = sphere.w - fDist;
        // normal of collision (going towards the sphere centre)
        //float xNcoll = xDiff  / fDist;
        //return true;
    }

    /**
     * Get box volume
     */
    public float getVolume() {
        return (max.x - min.x) * (max.y - min.y) * (max.z - min.z);
    }

    @Override
    public String toString() {
        return "[" + min.toString() + ", " + max.toString() + "]";
    }
}
