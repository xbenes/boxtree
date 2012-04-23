package qtree;

import javax.vecmath.Vector4f;

/**
 * Test file and example
 * @author Petr Benes
 */
public class Test {

    public static void main(String[] args) {
        SphereSet ss = new SphereSet();
        for (int i = 0; i < 1000; i++) {
            Vector4f sphere = new Vector4f(
                        (float) Math.random()*50,
                        (float) Math.random()*50,
                        (float) Math.random()*50,
                        1
                        //  0,0,0,1
                    );

            ss.addSphere(sphere);
        }

        BoxTree bt = new BoxTree(ss);
        Context ctx = new Context();
        
        // should we build the tree or perform volume computation only
        
        // Note: if we don't build the tree, only first node is kept and the 
        // volume computed via bt.computeVolume returns the volume of the bounding box
        // gathered volume is computed correctly
        ctx.setConstructTree(false);
        ctx.setMaxLevel(6);
        bt.split(ctx);
        float volume = bt.computeVolume();
        System.out.println("volume: " + volume + " gathered: " + ctx.getVolume());
    }
}
