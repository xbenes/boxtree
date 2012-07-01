/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qtree;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author username
 */
public class SphereSetTest {

    public SphereSetTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getBoundingBox method, of class SphereSet.
     */
    @Test
    public void testGetBoundingBox() {
        SphereSet instance = new SphereSet();
        Vector4f v1 = new Vector4f(0.f, 0.f, 0.f, 1.0f);
        Vector4f v2 = new Vector4f(1.f, 0.f, 0.f, 1.0f);
        instance.addSphere(v1);
        instance.addSphere(v2);
        Box bb = instance.getBoundingBox();

        //  TODO: replace with exact sphere-in-box test
        assertTrue(bb.intersect(v1));
        assertTrue(bb.intersect(v2));

    }

    /**
     * Test of collidesWith method, of class SphereSet.
     */
    @Test
    public void testCollidesWith() {

        // prepare sphere set for tests
        Vector4f sphere1 = new Vector4f(0.5f, 0.5f, 0.5f, 0.1f);
        Vector4f sphere2 = new Vector4f(0.3f, 0.3f, 0.3f, 0.1f);
        Vector4f sphere3 = new Vector4f(1.f, 1.f, 0.f, 0.1f);

        SphereSet ss1 = new SphereSet();
        ss1.addSphere(sphere1);
        ss1.addSphere(sphere2);
        ss1.addSphere(sphere3);


        // test collissions against various boxes

        // create a box which has non-empty intersection with the sphere set
        Box b1 = new Box(
            new Vector3f(0.f, 0.f, 0.f),
            new Vector3f(1.f, 1.f, 1.f)
        );

        CollideResult cr1 = ss1.collidesWith(b1);
        assertTrue(cr1.state == CollideResult.INTERSECT);

        // create box that is fully inside one of the spheres
        Box b2 = new Box(
            new Vector3f(0.49f, 0.49f, 0.49f),
            new Vector3f(0.51f, 0.51f, 0.51f)
        );

        CollideResult cr2 = ss1.collidesWith(b2);
        assertTrue(cr2.state == CollideResult.INSIDE);

        // create a box which has empty intersection with the set
        Box b3 = new Box(
            new Vector3f(5.f, 5.f, 5.f),
            new Vector3f(6.f, 6.f, 6.f)
        );

        CollideResult cr3 = ss1.collidesWith(b3);
        assertTrue(cr3.state == CollideResult.OUTSIDE);

        // create a box which has empty intersection with the set, but
        // is located inside the sphereset bounding box
        Box b4 = new Box(
            new Vector3f(0.4f, 0.4f, 0.4f),
            new Vector3f(0.41f, 0.41f, 0.41f)
        );

        CollideResult cr4 = ss1.collidesWith(b4);
        assertTrue(cr4.state == CollideResult.OUTSIDE);

    }
}
