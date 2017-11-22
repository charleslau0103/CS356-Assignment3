/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package driver;

/**
 *
 * @author Charles Lau
 */
public class LastUpdate implements TwitterCountFunction {

    String id;
    long updateTime;
    
    public LastUpdate(){
        setID(null);
        setUpdateTime(0);
    }
    
    @Override
    public void countTwitterUser(TwitterUser user) {
        if(user.getLastUpdateTime() > getUpdateTime()){
            setUpdateTime(user.getLastUpdateTime());
            setID(user.getID());
        }
    }

    @Override
    public void countGroup(UserGroup group) {
        //
    }
    
    public String getID(){
        return id;
    }
    
    public void setID(String id){
        this.id = id;
    }
    
    public long getUpdateTime(){
        return updateTime;
    }
    
    public void setUpdateTime(long updateTime){
        this.updateTime = updateTime;
    }
}
