/*  
Author:     Charles Lau
Date:       10/28/2017
Due Date:   11/7/2017
Course:     CS 356.01
Professor:  Yu Sun
Assignment: 2
*/
package driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class TwitterUser implements ActionListener, TwitterCount, User{

    private String id, tweetMessage;
    private Vector<User> subscribers;
    private DefaultListModel<String> newsfeed, subscriptions;
    private JFrame userFrame, timeFrame;
    private JPanel topPanel, bottomPanel;
    private JTextField userIDField, messageField;
    private JButton subscribe, tweetPost, timeButton;
    private JScrollPane userPane, newsfeedPane;
    private JList<String> userList, newsfeedList, time;
    private GridBagConstraints gbc;
    private long creationTime, lastUpdatedTime;
    
    public TwitterUser(String name) {
        this.setID(name);
        subscribers = new Vector<User>();
        subscriptions = new DefaultListModel<String>();
        newsfeed = new DefaultListModel<String>();
        setCreationTime(System.currentTimeMillis());
        getSubscriptions().addElement("User created: " + Long.toString(creationTime));
    }
    
    public JFrame buildGUI() {
        userFrame = new JFrame(getID() + " 's User View");
        userFrame.setPreferredSize(new Dimension(500, 550));
        userFrame.getContentPane().setLayout(new GridLayout(2, 1, 3, 4));
        userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());

        userIDField = new JTextField(20);
        messageField = new JTextField(20);

        subscribe = new JButton("Subscribe to user");
        subscribe.addActionListener(this);
        tweetPost = new JButton("Post tweet message");
        tweetPost.addActionListener(this);

        userList = new JList<String>(subscriptions);
        newsfeedList = new JList<String>(newsfeed);

        userPane = new JScrollPane(userList);
        newsfeedPane = new JScrollPane(newsfeedList);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(userIDField, gbc);
        bottomPanel.add(messageField, gbc);
        gbc.gridx = 1;
        topPanel.add(subscribe, gbc);
        bottomPanel.add(tweetPost, gbc);     
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        topPanel.add(userPane, gbc);
        bottomPanel.add(newsfeedPane, gbc);
        userFrame.getContentPane().add(topPanel);
        userFrame.getContentPane().add(bottomPanel); 
        userFrame.pack();
        userFrame.setVisible(true);
        return userFrame;
    }

    @Override
    public Vector<User> getSubscribers() {
        return subscribers;
    }

    @Override
    public void subscribe(User target) {
        target.getSubscribers().add(this);
        this.getSubscriptions().addElement(target.getID());
        this.update(target);
        if (userList != null)
            userList.setModel(subscriptions);
    }

    @Override
    public void tweetPost(String message) {
        setTweetMessage(message);
        update(this);
        notifySubscribers();
    }

    @Override
    public void notifySubscribers() {
        for (User subscriber: subscribers) {
            subscriber.update(this);
        }
    }

    @Override
    public void update(User user) {
        
        newsfeed.add(0, user.getID() + ": " + user.getTweetMessage());
        if (newsfeedList != null)
            newsfeedList.setModel(newsfeed);
        setLastUpdateTime(System.currentTimeMillis());
        newsfeed.add(0, "Last Updated: " + Long.toString(getLastUpdateTime()));
    }

    @Override
    public String getTweetMessage() {
        return tweetMessage;
    }

    @Override
    public void accept(TwitterCountFunction u) {
        u.countTwitterUser(this);
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public DefaultListModel<String> getNewsfeed() {
        return newsfeed;
    }

    public DefaultListModel<String> getSubscriptions() {
        return subscriptions;
    }
    
    public String toString() {
        return getID();
    }
    
    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getLastUpdateTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdateTime(long lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        if (action.getActionCommand().equals("Subscribe to user")) {
            if (!subscriptions.contains(userIDField.getText()) && AdminCtrlPanel.getInstance().getUserMap().get(userIDField.getText().toLowerCase()) != null) {
                subscribe(AdminCtrlPanel.getInstance().getUserMap().get(userIDField.getText().toLowerCase()));
            }
        } else if (action.getActionCommand().equals("Post tweet message")) {
            tweetPost(messageField.getText());
        }
    }
}
