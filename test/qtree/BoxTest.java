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
public class BoxTest {

    public BoxTest() {
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
     * Test of updateMinMax method, of class Box.
     */
    @Test
    public void testUpdateMinMax() {
        Box instance = new Box(
            new Vector3f(0.f, 0.f, 0.f),
            new Vector3f(1.f, 1.f, 1.f)
        );

        // update max/min and test bounds
        instance.updateMinMax(-2.f, -2.f, -2.f);
        assertTrue(instance.getMin().x == -2.f);
        assertTrue(instance.getMin().y == -2.f);
        assertTrue(instance.getMin().z == -2.f);

        // update max/min and test bounds
        instance.updateMinMax(3.f, 3.f, 3.f);
        assertTrue(instance.getMax().x == 3.f);
        assertTrue(instance.getMax().y == 3.f);
        assertTrue(instance.getMax().z == 3.f);

    }

    /**
     * Test of intersect method, of class Box.
     */
    @Test
    public void testIntersect_3args() {
        Box instance = new Box(
            new Vector3f(0.f, 0.f, 0.f),
            new Vector3f(1.f, 1.f, 1.f)
        );

        assertEquals(instance.intersect(0.5f, 0.5f, 0.5f), true);
        assertEquals(instance.intersect(0.0f, 1.0f, 1.0f), true);
        assertEquals(instance.intersect(0.2f, 1.0f, 0.4f), true);
        assertEquals(instance.intersect(0.3f, 0.0f, 0.0f), true);

        assertEquals(instance.intersect(1.5f, 0.5f, 0.5f), false);
        assertEquals(instance.intersect(0.5f, 1.5f, 0.5f), false);
        assertEquals(instance.intersect(0.5f, 0.5f, 1.5f), false);
        assertEquals(instance.intersect(1.1f, 1.1f, 1.1f), false);
        assertEquals(instance.intersect(-2.1f, 0.1f, 2.1f), false);
    }

    /**
     * Test of split8 method, of class Box.
     */
    @Test
    public void testSplit8() {
        Box b1 = new Box(
            new Vector3f(0.f, 0.f, 0.f),
            new Vector3f(1.f, 1.f, 1.f)
        );
        Box[] split = b1.split8();

        assertEquals(split.length, 8, 0);

        // TODO: add checks for correct split coordinates
    }

    /**
     * Test of inside method, of class Box.
     */
    @Test
    public void testInside() {
        // various box/sphere instances for inside test
        Vector4f sph1 = new Vector4f(0.f, 0.f, 0.f, 1.f);
        Vector4f sph2 = new Vector4f(2.f, 2.f, 2.f, 1.f);
        Vector4f sph3 = new Vector4f(-1.f, -1.f, -1.f, 1.f);

        Box b1 = new Box(
            new Vector3f(0.f, 0.f, 0.f),
            new Vector3f(0.1f, 0.1f, 0.1f)
        );
        assertTrue(b1.inside(sph1));
        assertTrue(! b1.inside(sph2));
        assertTrue(! b1.inside(sph3));

        Box b2 = new Box(
            new Vector3f(2.f, 2.f, 2.f),
            new Vector3f(2.1f, 2.1f, 2.1f)
        );
        assertTrue(! b2.inside(sph1));
        assertTrue(b2.inside(sph2));
        assertTrue(! b2.inside(sph3));

    }

    /**
     * Test of intersect method, of class Box.
     */
    @Test
    public void testIntersect_Vector4f() {
        // various box/sphere instances for inside test
        Vector4f sph1 = new Vector4f(0.f, 0.f, 0.f, 1.f);
        Vector4f sph2 = new Vector4f(2.f, 2.f, 2.f, 1.f);
        Vector4f sph3 = new Vector4f(-1.5f, -1.5f, -1.5f, 1.0f);

        Box b1 = new Box(
            new Vector3f(0.f, 0.f, 0.f),
            new Vector3f(4.f, 4.f, 4.f)
        );
        assertTrue(b1.intersect(sph1));
        assertTrue(b1.intersect(sph2));
        assertTrue(!b1.intersect(sph3));
    }

    /**
     * Test of getVolume method, of class Box.
     */
    @Test
    public void testGetVolume() {
        Box b1 = new Box(
            new Vector3f(0.f, 0.f, 0.f),
            new Vector3f(1.f, 1.f, 1.f)
        );

        assertEquals(b1.getVolume(), 1.f, 0.0f);


        Box b2 = new Box(
            new Vector3f(0.f, 0.f, 0.f),
            new Vector3f(2.f, 2.f, 2.f)
        );
        assertEquals(b2.getVolume(), 8.f, 0.0f);

    }

}
