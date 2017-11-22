/*  
Author:     Charles Lau
Date:       10/28/2017
Due Date:   11/7/2017
Course:     CS 356.01
Professor:  Yu Sun
Assignment: 2
*/
package driver;

public class GroupCount implements TwitterCountFunction {
    
    private int counter;

    public GroupCount() {
        setCounter(0);
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    public int getCounter() {
        return counter;
    }

    @Override
    public void countTwitterUser(TwitterUser u) {
    }

    @Override
    public void countGroup(UserGroup g) {
        this.setCounter(getCounter()+1);
    }
}
