/*  
Author:     Charles Lau
Date:       10/28/2017
Due Date:   11/7/2017
Course:     CS 356.01
Professor:  Yu Sun
Assignment: 2
*/
package driver;

public class UserCount implements TwitterCountFunction {

    private int counter = 0;

    public UserCount() {
        setCounter(0);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void countTwitterUser(TwitterUser user) {
        this.setCounter(getCounter()+1);
    }

    @Override
    public void countGroup(UserGroup group) {
        return;
    }
}
