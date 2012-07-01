package qtree;

/**
 * The object which encapsulates collision result. Contains not only the resulting 
 * state but also the sphereset of related spheres.
 * @author Petr Benes
 */

public class CollideResult {
    // constants for state representation
    public static int INSIDE = 0;
    public static int INTERSECT = 1;
    public static int OUTSIDE = 2;

    public final int state;
    public final SphereSet sphereSet;

    public CollideResult(int state, SphereSet s) {
        this.state = state;
        this.sphereSet = s;
    }
}
