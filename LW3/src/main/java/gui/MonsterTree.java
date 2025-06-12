package gui;

import repository.MonsterRepository;
import model.Monster;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.List;
import java.util.Map;

public class MonsterTree extends JTree {
    private final DefaultTreeModel treeModel;
    private final MonsterRepository repository;

    public MonsterTree(MonsterRepository repository) {
        this.repository = repository;
        treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Чудовища"));
        setModel(treeModel);
        updateTree();
        addTreeSelectionListener(e -> showMonsterDetails());
    }

    public void updateTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Источники");
        Map<String, List<Monster>> sources = repository.getSources();

        for (Map.Entry<String, List<Monster>> entry : sources.entrySet()) {
            String sourceName = entry.getKey();
            List<Monster> monsters = entry.getValue();

            DefaultMutableTreeNode sourceNode = new DefaultMutableTreeNode(sourceName);

            for (Monster monster : monsters) {
                sourceNode.add(new DefaultMutableTreeNode(monster));
            }

            root.add(sourceNode);
        }

        treeModel.setRoot(root);
        treeModel.reload();
    }

    private void showMonsterDetails() {
        TreePath path = getSelectionPath();
        if (path != null) {
            Object node = path.getLastPathComponent();
            if (node instanceof DefaultMutableTreeNode) {
                Object userObject = ((DefaultMutableTreeNode) node).getUserObject();

                if (userObject instanceof Monster) {
                    new MonsterDetailFrame((Monster) userObject).setVisible(true);
                }
            }
        }
    }
}