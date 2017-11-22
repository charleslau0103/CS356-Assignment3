/*  
Author:     Charles Lau
Date:       10/28/2017
Due Date:   11/7/2017
Course:     CS 356.01
Professor:  Yu Sun
Assignment: 2
*/
package driver;

import java.util.Vector;

public class UserGroup implements TwitterCount{
    private String groupID;
    private Vector<TwitterCount> child;

    public UserGroup(String groupID) {
        this.setGroupID(groupID);
        child = new Vector<TwitterCount>();
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void add(TwitterCount element) {
        if (!child.contains(element))
            child.add(element);
    }

    public String toString() {
        return this.groupID;
    }

    @Override
    public void accept(TwitterCountFunction g) {
        g.countGroup(this);
        for(TwitterCount t: child) {
            t.accept(g);
        }
    }
}
