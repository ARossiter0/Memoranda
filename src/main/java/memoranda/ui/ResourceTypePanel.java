package memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;

import memoranda.util.AppList;
import memoranda.util.CurrentStorage;
import memoranda.util.Local;
import memoranda.util.MimeType;
import memoranda.util.MimeTypesList;
import memoranda.util.Util;

/*$Id: ResourceTypePanel.java,v 1.8 2004/10/18 19:09:10 ivanrise Exp $*/
public class ResourceTypePanel extends JPanel {
    Border border1;
    TitledBorder titledBorder1;
    Border border2;
    TitledBorder titledBorder2;
    public String ext = "";
    boolean CANCELLED = true;
    JPanel jPanel1 = new JPanel();
    JButton newTypeB = new JButton();
    JScrollPane jScrollPane1 = new JScrollPane();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel areaPanel = new JPanel();
    JPanel jPanel2 = new JPanel();
    JButton editB = new JButton();
    JButton deleteB = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    public JList typesList = new JList();
    BorderLayout borderLayout3 = new BorderLayout();
    Border border3;

    /**
     * Create and initialize a resource type panel
     */
    public ResourceTypePanel() {
        super();
        try {
            jbInit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        // Initialize borders
        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 2);
        titledBorder1 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Registered types"));
        border2 = BorderFactory.createLineBorder(Color.gray, 1);
        titledBorder2 = new TitledBorder(BorderFactory.createLineBorder(Color.gray, 1), Local.getString("Details"));
        border3 = BorderFactory.createEmptyBorder(0, 10, 0, 0);

        // Initialize the registered types panel
        jPanel1.setBorder(titledBorder1);
        jPanel1.setLayout(borderLayout1);
        
        // Initialize the new type button
        newTypeB.setMaximumSize(new Dimension(110, 25));
        newTypeB.setPreferredSize(new Dimension(110, 25));
        newTypeB.setText(Local.getString("New"));
        newTypeB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newTypeB_actionPerformed(e);
            }
        });
        
        // Style the area panel
        areaPanel.setLayout(borderLayout2);
        
        // Initialize the details panel
        jPanel2.setMaximumSize(new Dimension(120, 32767));
        jPanel2.setMinimumSize(new Dimension(120, 36));
        jPanel2.setPreferredSize(new Dimension(120, 36));
        jPanel2.setBorder(border3);
        
        
        // Initialize the edit button
        editB.setText(Local.getString("Edit"));
        editB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editB_actionPerformed(e);
            }
        });
        editB.setEnabled(false);
        editB.setMaximumSize(new Dimension(110, 25));
        editB.setPreferredSize(new Dimension(110, 25));
        
        // Initialize the delete button
        deleteB.setEnabled(false);
        deleteB.setMaximumSize(new Dimension(110, 25));
        deleteB.setPreferredSize(new Dimension(110, 25));
        deleteB.setText(Local.getString("Delete"));
        deleteB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteB_actionPerformed(e);
            }
        });
        
        // Style and initialize the types list
        typesList.setCellRenderer(new TypesListRenderer());
        typesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        typesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                typesList_valueChanged(e);
            }
        });
        initTypesList();

        // typesList.setCellRenderer(new TypesListRenderer());
        this.setLayout(borderLayout3);
        
        // Add the area panel to this
        this.add(areaPanel, BorderLayout.CENTER);
        
        // Add the registered types panel, a scrollpane with the types list
        // and the description panel, and the buttons to the area panel. 
        areaPanel.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(typesList, null);
        jPanel1.add(jPanel2, BorderLayout.EAST);
        jPanel2.add(newTypeB, null);
        jPanel2.add(editB, null);
        jPanel2.add(deleteB, null);
        
        // Add all of the types in the mime types list to the
        // types list. 
        typesList.setListData(MimeTypesList.getAllMimeTypes());
        // typesList.updateUI();

    }

    /**
     * Initialize the types list and update the ui
     */
    public void initTypesList() {
        /*
         * Vector v = new Vector(); icons = new Vector(); Vector t =
         * MimeTypesList.getAllMimeTypes(); for (int i = 0; i < t.size(); i++) {
         * MimeType mt = (MimeType)t.get(i); v.add(mt.getLabel());
         * icons.add(mt.getIcon()); }
         */
        typesList.setListData(MimeTypesList.getAllMimeTypes());
        typesList.updateUI();
    }

    /**
     * Create a new type for resources
     * @param e the action event associated with the new type button
     */
    void newTypeB_actionPerformed(ActionEvent e) {
        // Create and style the edit type dialog
        EditTypeDialog dlg = new EditTypeDialog(App.getFrame(), Local.getString("New resource type"));
        Dimension dlgSize = new Dimension(420, 420);
        dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.extField.setText(ext);
        dlg.descField.setText(ext);
        dlg.appPanel.argumentsField.setText("$1");
        dlg.iconLabel.setIcon(
                new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/mimetypes/default.png")));
        dlg.setVisible(true);
        
        // If press cancel, return
        if (dlg.CANCELLED)
            return;
        
        
        // Add the type to the mime types list and update the types list
        String typeId = Util.generateId();
        MimeType mt = MimeTypesList.addMimeType(typeId);
        String[] exts = dlg.extField.getText().trim().split(" ");
        for (int i = 0; i < exts.length; i++)
            mt.addExtension(exts[i]);
        mt.setLabel(dlg.descField.getText());
        AppList appList = MimeTypesList.getAppList();
        if (dlg.appPanel.applicationField.getText().length() > 0) {
            File f = new File(dlg.appPanel.applicationField.getText());
            String appId = Util.generateId();
            appList.addApp(appId, f.getParent().replace('\\', '/'), f.getName().replace('\\', '/'),
                    dlg.appPanel.argumentsField.getText());
            mt.setApp(appId);
        }
        if (dlg.iconPath.length() > 0)
            mt.setIconPath(dlg.iconPath);
        CurrentStorage.get().storeMimeTypesList();
        this.initTypesList();
        typesList.setSelectedValue(mt, true);
    }

    /**
     * Delete a type
     * @param e the action event associated with the delete button
     */
    void deleteB_actionPerformed(ActionEvent e) {
        MimeType mt = (MimeType) typesList.getSelectedValue();
        
        // If confirm delete, then remove the type from mime types list
        // and initialize the types list
        int n = JOptionPane.showConfirmDialog(App.getFrame(),
                Local.getString("Delete resource type") + "\n'" + mt.getLabel() + "'\n"
                        + Local.getString("Are you sure?"),
                Local.getString("Delete resource type"), JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        MimeTypesList.removeMimeType(mt.getMimeTypeId());
        CurrentStorage.get().storeMimeTypesList();
        this.initTypesList();
    }

    /**
     * Edit a type
     * @param e the action event associated with the edit button
     */
    void editB_actionPerformed(ActionEvent e) {
        // Open and style the edit type dialog
        EditTypeDialog dlg = new EditTypeDialog(App.getFrame(), Local.getString("Edit resource type"));
        Dimension dlgSize = new Dimension(420, 450);
        dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        MimeType mt = (MimeType) typesList.getSelectedValue();
        String[] exts = mt.getExtensions();
        String extss = "";
        for (int i = 0; i < exts.length; i++)
            extss += exts[i] + " ";
        dlg.extField.setText(extss);
        dlg.descField.setText(mt.getLabel());
        dlg.iconLabel.setIcon(mt.getIcon());
        AppList appList = MimeTypesList.getAppList();
        dlg.appPanel.applicationField
                .setText(appList.getFindPath(mt.getAppId()) + "/" + appList.getExec(mt.getAppId()));
        dlg.appPanel.argumentsField.setText(appList.getCommandLinePattern(mt.getAppId()));
        dlg.setVisible(true);
        
        // If select cancel
        if (dlg.CANCELLED)
            return;
        
        // Change the type in the mime types list, and update the type list
        String typeId = mt.getMimeTypeId();
        MimeTypesList.removeMimeType(typeId);
        mt = MimeTypesList.addMimeType(typeId);
        exts = dlg.extField.getText().trim().split(" ");
        for (int i = 0; i < exts.length; i++)
            mt.addExtension(exts[i]);
        mt.setLabel(dlg.descField.getText());
        if (dlg.appPanel.applicationField.getText().length() > 0) {
            File f = new File(dlg.appPanel.applicationField.getText());
            String appId = Util.generateId();
            appList.addApp(appId, f.getParent().replace('\\', '/'), f.getName().replace('\\', '/'),
                    dlg.appPanel.argumentsField.getText());
            mt.setApp(appId);
        }
        if (dlg.iconPath.length() > 0)
            mt.setIconPath(dlg.iconPath);
        CurrentStorage.get().storeMimeTypesList();
        this.initTypesList();
        typesList.setSelectedValue(mt, true);

    }

    class TypesListRenderer extends JLabel implements ListCellRenderer {


        public TypesListRenderer() {
            super();
        }

        /**
         * The cell renderer that shows resource types in the list 
         * @param list the list where the resource types are displayed
         * @param value the value of the cell 
         * @param index the cell index
         * @param isSelected whether the cell is selected
         * @param cellHasFocus whether the cell has focus
         * @return
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {

            // Get the extension for all of the resource types as a string
            MimeType mt = (MimeType) value;
            String[] exts = mt.getExtensions();
            String extstr = "";
            for (int j = 0; j < exts.length; j++) {
                extstr += "*." + exts[j];
                if (j != exts.length - 1)
                    extstr += ", ";
            }

            // Show the resource extension with an icon. 
            setOpaque(true);
            setText(mt.getLabel() + " (" + extstr + ")");
            setIcon(mt.getIcon());
            
            // Set the cell as being in the foreground or background
            // depending on whether it is selected. 
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }

    /**
     * When changing the resource type selected in the list,
     * enable editing and deleting the resource. 
     * @param e the list selection event
     */
    void typesList_valueChanged(ListSelectionEvent e) {
        boolean en = typesList.getSelectedValue() != null;
        this.editB.setEnabled(en);
        this.deleteB.setEnabled(en);
    }

}