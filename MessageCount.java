/*  
Author:     Charles Lau
Date:       10/28/2017
Due Date:   11/7/2017
Course:     CS 356.01
Professor:  Yu Sun
Assignment: 2
*/
package driver;

public class MessageCount implements TwitterCountFunction {

    private int counter = 0;

    public MessageCount() {
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
        this.setCounter(getCounter()+u.getNewsfeed().size());
    }

    @Override
    public void countGroup(UserGroup g) {
        return;
    }
}
