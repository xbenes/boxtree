package qtree;

/**
 * BoxTree implementation. The tree is built for a set of spheres.
 * @author Petr Benes
 */
public class BoxTree {

    // root tree item
    private BoxTreeItem root;
    // sphere set
    private SphereSet sphereSet;

    /**
     * Init BoxTree as one box enclosing all the spheres
     * @param s 
     */
    public BoxTree(SphereSet s) {
        this.sphereSet = s;
        Box b = s.getBoundingBox();
        BoxTreeItem bti = new BoxTreeItem(b);
        this.root = bti;
    }

    /**
     * Get the item which points to the root of the tree
     * @return root item
     */
    public BoxTreeItem getRoot() {
        return this.root;
    }
    
    /**
     * Split the tree. 
     * Pass shared context through all levels of splitting
     * @param ctx shared context object
     */
    public void split(Context ctx) {
        int level = 0;
        this.split(this.root, ctx, level);
    }

    /**
     * Split the tree
     * check the level against maximum allowed level
     * @param node the item which is to be split
     * @param ctx shared ctx object
     * @param level level at which we are currently splitting
     */
    private void split(BoxTreeItem node, Context ctx, int level) {
        // do not nest deeper
        if (level > ctx.getMaxLevel()) {
            // no nest deeper, still use this box's volume (overestimate)
            ctx.addVolume(node.getBox().getVolume());
            return;
        }

        Box b = node.getBox();
        Box[] boxes = b.split8();

        for (Box child: boxes) {
            int collideResult = this.sphereSet.collidesWith(child);
            // add child only if there is an intersection
            if (collideResult == SphereSet.INSIDE) {
                // add volume
                ctx.addVolume(child.getVolume());
                // add child if tree is to be built
                if (ctx.getConstructTree()) {
                    BoxTreeItem childItem = new BoxTreeItem(child);
                    node.addChild(childItem);
                }
            } else if (collideResult == SphereSet.INTERSECT) {
                BoxTreeItem childItem = new BoxTreeItem(child);
                // add child if tree is to be built
                if (ctx.getConstructTree())
                    node.addChild(childItem);
                // nest deeper only if the item is NOT FULLY contained within
                // one of the spheres in the sphereset
                this.split(childItem, ctx, level + 1);
            } else {
                // outside
            }
        }
    }

    // compute volume of the whole tree
    // if, during the split, the tree was not constructed, the returned value is
    // the volume of the whole bounding box (no child nodes present)
    @Deprecated
    public float computeVolume() {
        return this.computeVolume(this.root);
    }
    
    /**
     * Compute the sum of volumes of all leaf nodes of a box tree item
     * @param node item to proceed with
     * @return computed volume
     */
    private float computeVolume(BoxTreeItem node) {
        if (node.isLeaf()) {
            return node.getBox().getVolume();
        }
        // process children
        float sum = 0;
        for (BoxTreeItem bti: node.getChildren()) {
            float vol = computeVolume(bti);
            sum += vol;
        }
        return sum;
    }
}
