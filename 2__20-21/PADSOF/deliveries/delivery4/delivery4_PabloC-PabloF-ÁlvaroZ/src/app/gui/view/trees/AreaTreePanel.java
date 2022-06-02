package app.gui.view.trees;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Class AreaTreePanel
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AreaTreePanel extends JPanel {

    DefaultTreeModel dataModel;
    DefaultMutableTreeNode root;
    JTree tree;

    /** constructor */
    public AreaTreePanel() {

        root = new DefaultMutableTreeNode("TheaterMainArea");

        // Create the data model of the tree, using its root as argument
        dataModel = new DefaultTreeModel(root);

        // Create the tree, using the data model as argument
        tree = new JTree(dataModel);

        // The size of the tree can be fixed
        tree.setPreferredSize(new Dimension(200, 40));

        this.setPreferredSize(new Dimension(400, 100));
        JScrollPane scrollbar = new JScrollPane(tree);
        scrollbar.setViewportView(tree);
        scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scroll bar
        JPanel treeExample = new JPanel();
        treeExample.add(scrollbar);

        this.add(treeExample);

        this.setVisible(true);

    }

    /**
     * Set the controller
     * 
     * @param listener the tree selection listener
     */
    void setController(TreeSelectionListener listener) {
        tree.addTreeSelectionListener(listener);
    }

    /**
     * getter: data model
     * 
     * @return the data model
     */
    public DefaultTreeModel getDataModel() {
        return dataModel;
    }

    /**
     * root
     * 
     * @return the root
     */
    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    /**
     * Getter: tree
     * 
     * @return the tree
     */
    public JTree getTree() {
        return tree;
    }

    /**
     * reload the data model
     */
    public void reload() {
        dataModel.reload();
    }
}
