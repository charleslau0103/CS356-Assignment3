/*  
Author:     Charles Lau
Date:       10/28/2017
Due Date:   11/7/2017
Course:     CS 356.01
Professor:  Yu Sun
Assignment: 2
*/
package driver;

import java.util.List;

public interface User {

    public void subscribe(User target);

    public void tweetPost(String message);

    public void notifySubscribers();

    public void update(User user);

    public String getID();

    public String getTweetMessage();

    public List<User> getSubscribers();
}
