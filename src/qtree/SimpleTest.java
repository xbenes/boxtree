package qtree;

import javax.vecmath.Vector4f;

/**
 * Test file and example
 * @author Petr Benes
 */
public class SimpleTest {

    public static void main(String[] args) {
        SphereSet ss = new SphereSet();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                Vector4f sphere = new Vector4f(
                            (float) i,
                            (float) j,
                            (float) 0,
                            0.1f
                            //  0,0,0,1
                        );

                ss.addSphere(sphere);
            }
        }

        BoxTree bt = new BoxTree(ss);
        Context ctx = new Context();
        
        // should we build the tree or perform volume computation only
        
        // Note: if we don't build the tree, only first node is kept and the 
        // volume computed via bt.computeVolume returns the volume of the bounding box
        // gathered volume is computed correctly
        ctx.setConstructTree(false);
        ctx.setMaxLevel(12);
        bt.split(ctx);
        System.out.println("volume gathered: " + ctx.getVolume());

    }
}
