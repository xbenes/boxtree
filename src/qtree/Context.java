package qtree;

/**
 * Context object
 * used to configure BoxTree split
 * @author Petr Benes
 */
public class Context {
    // whether the tree should be constructed (true) or whether only volume should be computed (false)
    private boolean constructTree = true;
    // maximum split depth
    private int maxLevel = 6;
    // initial computed volume. Default 0.
    private float volume = 0;
    
    public void setConstructTree(boolean construct) {
        this.constructTree = construct;
    }
    public boolean getConstructTree() {
        return this.constructTree;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
    
    /**
     * Add volume to current volume
     * @param v number to be added to currently stored volume
     */
    public void addVolume(float v) {
        this.volume += v;
    }
    public float getVolume() {
        return this.volume;
    }
}
