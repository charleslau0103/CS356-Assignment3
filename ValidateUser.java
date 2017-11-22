/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package driver;

import java.util.Vector;

public class ValidateUser implements TwitterCountFunction{

    Vector<String> invalid;
    
    public ValidateUser(){
        invalid = new Vector<String>();
    }
    
    public Vector<String> result(){
        if(invalid.isEmpty()){
            invalid.add("No Invalid User Found");
        } else{
            invalid.add(0, "List of Invalid Users: ");
        }
        return invalid;
    }
    
    @Override
    public void countTwitterUser(TwitterUser user) {
        if(user.getID().contains(" ") || user.getID().contains("\t")){
            invalid.add(user.getID());
        }
    }

    @Override
    public void countGroup(UserGroup group) {
        if(group.getGroupID().contains(" ") || group.getGroupID().contains("\t")){
            invalid.add(group.getGroupID());
        }
    }
}
