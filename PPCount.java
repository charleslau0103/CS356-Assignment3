/*  
Author:     Charles Lau
Date:       10/28/2017
Due Date:   11/7/2017
Course:     CS 356.01
Professor:  Yu Sun
Assignment: 2
*/
package driver;

public class PPCount implements TwitterCountFunction {

    private int total, count;
    private String[] positiveList = {"good", "fun", "excellent", "great", "awesome"};

    public PPCount() {
        setTotal(0);
        setCount(0);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPercentage() {
        return (double)count / total * 100;        
    }

    @Override
    public void countTwitterUser(TwitterUser u) {
        for(Object all: u.getNewsfeed().toArray()) {
            for (int i = 0; i < positiveList.length; i++)
            {
                if (all.toString().toLowerCase().contains(positiveList[i])) {
                    setCount(getCount()+1);
                }             
            }
            setTotal(getTotal()+1);
        }
    }

    @Override
    public void countGroup(UserGroup g) {
            return;
    }
}
