/*  
Author:     Charles Lau
Date:       10/28/2017
Due Date:   11/7/2017
Course:     CS 356.01
Professor:  Yu Sun
Assignment: 2
*/
package driver;

import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;


public class AdminCtrlPanel implements ActionListener {

    private static AdminCtrlPanel instance = null;
    private HashMap<String, TwitterUser> userMap;
    private HashMap<String, UserGroup> groupMap;
    private JFrame miniTwitterFrame;
    private JPanel top, mid, bottom, treePanel, buttonPanel;
    private JScrollPane treePane;
    private JTextField userName, groupName;
    private JButton addUser, addGroup, userView, userTotal, groupTotal, messageTotal, positivePercentage, validateUser, lastUpdated;
    private JLabel actionLabel;
    private JTree tree;
    private DefaultTreeModel modelTree;
    private DefaultMutableTreeNode root, currentNode, temp;
    private GridBagConstraints gbc;

    private AdminCtrlPanel() {
        userMap = new HashMap<String, TwitterUser>();
        groupMap = new HashMap<String, UserGroup>();
        generateUI();
    }
    
    public void generateUI() {
        miniTwitterFrame = new JFrame("Admin Control Panel.");
        miniTwitterFrame.setPreferredSize(new Dimension(800, 500));
        miniTwitterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        miniTwitterFrame.getContentPane().setLayout(new GridBagLayout());

        treePanel = new JPanel();
        buttonPanel = new JPanel();
        top = new JPanel();
        mid = new JPanel();
        bottom = new JPanel();
        treePanel.setLayout(new GridLayout());
        buttonPanel.setLayout(new GridBagLayout());
        top.setLayout(new GridBagLayout());
        mid.setLayout(new GridBagLayout());
        bottom.setLayout(new GridBagLayout());

        addUser = new JButton("Add New User");
        addUser.addActionListener(this);
        addGroup= new JButton("Add New Group");
        addGroup.addActionListener(this);
        userView = new JButton("Open User View");
        userView.addActionListener(this);
        userTotal = new JButton("Show User Total");
        userTotal.addActionListener(this);
        groupTotal = new JButton("Show Group Total");
        groupTotal.addActionListener(this);
        messageTotal = new JButton("Show Message Total");
        messageTotal.addActionListener(this);
        positivePercentage = new JButton("Show Positive Percentage");
        positivePercentage.addActionListener(this);
        validateUser = new JButton("Validate User");
        validateUser.addActionListener(this);
        lastUpdated = new JButton("Find Last Updated User");
        lastUpdated.addActionListener(this);
        
        root = new DefaultMutableTreeNode(new UserGroup("root"));
        modelTree = new DefaultTreeModel(root);
        tree = new JTree(modelTree);
        tree.setCellRenderer(new CustomRenderer());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                TreePath tp = event.getPath();
                actionLabel.setText("Expansion: " + tp.getLastPathComponent());
            }
            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                TreePath tp = event.getPath();
                actionLabel.setText("Collapse: " + tp.getLastPathComponent());
            }
        });
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                currentNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
            }
        });
        treePane = new JScrollPane(tree);
        treePanel.add(treePane);

        userName = new JTextField(20);
        groupName = new JTextField(20);

        actionLabel = new JLabel("Hello World");
        actionLabel.setHorizontalAlignment(SwingConstants.CENTER);


        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        miniTwitterFrame.getContentPane().add(treePanel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        miniTwitterFrame.getContentPane().add(buttonPanel, gbc);
        gbc.weighty = 0.2;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(top, gbc);
        gbc.weighty = 0.3;
        gbc.gridy = 2;
        buttonPanel.add(bottom, gbc);
        gbc.weighty = 0.6;
        gbc.gridy = 1;
        buttonPanel.add(mid, gbc);
        gbc.insets = new Insets(1, 1, 1, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 0.5;
        top.add(userName, gbc);
        bottom.add(userTotal, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.4;
        top.add(addUser, gbc);
        bottom.add(groupTotal, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        top.add(addGroup, gbc);
        bottom.add(positivePercentage, gbc);
        gbc.gridx = 0;
        gbc.weightx = 0.6;
        top.add(groupName, gbc);
        bottom.add(messageTotal, gbc);
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 0.5;
        mid.add(userView, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.5;
        mid.add(validateUser, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.5;
        mid.add(actionLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.5;
        mid.add(lastUpdated, gbc);
        miniTwitterFrame.pack();
        miniTwitterFrame.setVisible(true);
    }

    public static AdminCtrlPanel getInstance() {
        if (instance == null)
            instance = new AdminCtrlPanel();
        return instance;
    }

    public HashMap<String, TwitterUser> getUserMap() {
        return userMap;
    }		

    public void actionPerformed(ActionEvent a) {
        switch (a.getActionCommand()) {

        case "Add New User":
            if (currentNode == null || !(currentNode.getUserObject() instanceof TwitterUser))
                addUser(userName.getText().toLowerCase());
            break;

        case "Add New Group":
            if (currentNode == null || !(currentNode.getUserObject() instanceof TwitterUser))
                addGroup(groupName.getText().toLowerCase());
            break;  

        case "Open User View":
            if (currentNode != null && (currentNode.getUserObject() instanceof TwitterUser))
                openView(currentNode);
            else
                actionLabel.setText("You must select a user.");
            break;

        case "Show User Total":
            UserCount uc = new UserCount();
            ((TwitterCount)root.getUserObject()).accept(uc);
            actionLabel.setText("Total number of users: " + Integer.toString(uc.getCounter()));
            break;

        case "Show Group Total":
            GroupCount gc = new GroupCount();
            ((TwitterCount)root.getUserObject()).accept(gc);
            actionLabel.setText("Total number of groups: " + Integer.toString(gc.getCounter()));
            break;

        case "Show Message Total":
            MessageCount mc = new MessageCount();
            ((TwitterCount)root.getUserObject()).accept(mc);
            actionLabel.setText("Total number of messages: " + Integer.toString(mc.getCounter()));
            break;

        case "Show Positive Percentage":
            PPCount ppc = new PPCount();
            ((TwitterCount)root.getUserObject()).accept(ppc);
            actionLabel.setText("Positive Messages: " + Double.toString(ppc.getPercentage()) + "%");
            break;
            
        case "Validate User":
            validateUser();
            break;
           
        case "Find Last Updated User":
            userUpdate();
            break;

        default:
            break;
        }
    }

    public void addUser(String name) {
        if (name.length() < 2) {
            actionLabel.setText("Username must be at least 2 characters");
        } else if (userMap.get(name) == null) {
            userMap.put(name, new TwitterUser(name));
            temp = new DefaultMutableTreeNode(userMap.get(name));
            temp.setAllowsChildren(false);
            try {
                currentNode.add(temp);
                ((UserGroup)currentNode.getUserObject()).add(userMap.get(name));;
            } catch (NullPointerException e) {
                root.add(temp);
                ((UserGroup)root.getUserObject()).add(userMap.get(name));;
                modelTree.reload(root);
            }
            modelTree.reload(currentNode);
            actionLabel.setText("User: " + name + " has been created");
        } else
            actionLabel.setText("User: " + name + " already exists");
    }

    public void addGroup(String name) {
        if (name.length() < 2) {
            actionLabel.setText("Group name must be at least 2 characters");
        } else if (groupMap.get(name) == null) {
            groupMap.put(name, new UserGroup(name));
            temp = new DefaultMutableTreeNode(groupMap.get(name));
            try {
                currentNode.add(temp);
                ((UserGroup)currentNode.getUserObject()).add(groupMap.get(name));;
            } catch (NullPointerException e){
                root.add(temp);
                ((UserGroup)root.getUserObject()).add(groupMap.get(name));;
                modelTree.reload(root);
            }
            modelTree.reload(currentNode);
            actionLabel.setText("Group: " + name + " has been created");
        } else
            actionLabel.setText("Group: " + name + " already exists");
    }

    public void openView(DefaultMutableTreeNode n) {
        ((TwitterUser) n.getUserObject()).buildGUI();
        actionLabel.setText(n.toString() + "'s user view opened");
    }
    
    public void validateUser() {
        ValidateUser validate = new ValidateUser();
        ((TwitterCount)root.getUserObject()).accept(validate);
        JFrame vFrame = new JFrame("Validate User");
        JList<String> vList = new JList<String>(validate.result());
        vFrame.setPreferredSize(new Dimension(200, 200));
        vFrame.getContentPane().setLayout(new GridLayout());
        vFrame.getContentPane().add(vList);
        vFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vFrame.pack();
        vFrame.setVisible(true);
    }
    
    public void userUpdate() {
        LastUpdate lastUp = new LastUpdate();
        ((TwitterCount)root.getUserObject()).accept(lastUp);
        JFrame lastUpFrame = new JFrame("Find Last Updated User");
        JLabel timeLabel = new JLabel(lastUp.getID());
        lastUpFrame.setPreferredSize(new Dimension(200, 200));
        lastUpFrame.getContentPane().setLayout(new GridLayout());
        lastUpFrame.getContentPane().add(timeLabel);
        lastUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lastUpFrame.pack();
        lastUpFrame.setVisible(true);
    }


    @SuppressWarnings("serial")
    private static class CustomRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode n = (DefaultMutableTreeNode) value;
                if (n.getUserObject() instanceof UserGroup) {
                    setIcon(UIManager.getIcon("Tree.closedIcon"));
                }
            }
            return this;
        }
    }
}