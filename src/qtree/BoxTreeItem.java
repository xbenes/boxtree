package qtree;

import java.util.ArrayList;
import java.util.List;

/**
 * Node in the BoxTree
 * @author Petr Benes
 */
public class BoxTreeItem {
    private Box box;
    private List<BoxTreeItem> children = new ArrayList<BoxTreeItem>();
    
    /**
     * Init BoxTreeItem
     * @param box the bounding box
     */
    public BoxTreeItem(Box box) {
        this.box = box;
    }
    
    /**
     * Add child to this item
     * @param boxItem the child to be added
     */
    public void addChild(BoxTreeItem boxItem) {
        this.children.add(boxItem);
    }
    /**
     * Get bounding box
     * @return bounding box
     */
    public Box getBox() {
        return this.box;
    }
    
    /**
     * Whether the item has any children, i.e. is a leaf
     * @return whether the item is leaf
     */
    public boolean isLeaf() {
        return this.children.isEmpty();
    }
    
    /** 
     * Get all children of the item
     * @return all children
     */
    public List<BoxTreeItem> getChildren() {
        return this.children;
    }
}
