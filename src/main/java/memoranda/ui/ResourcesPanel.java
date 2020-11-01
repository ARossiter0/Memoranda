package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.Resource;
import main.java.memoranda.util.AppList;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.MimeType;
import main.java.memoranda.util.MimeTypesList;
import main.java.memoranda.util.Util;

import java.io.*;

/*$Id: ResourcesPanel.java,v 1.13 2007/03/20 08:22:41 alexeya Exp $*/
public class ResourcesPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar toolBar = new JToolBar();
	JButton newResB = new JButton();
	ResourcesTable resourcesTable = new ResourcesTable();
	JButton removeResB = new JButton();
	JScrollPane scrollPane = new JScrollPane();
	JButton refreshB = new JButton();
	JPopupMenu resPPMenu = new JPopupMenu();
	JMenuItem ppRun = new JMenuItem();
	JMenuItem ppRemoveRes = new JMenuItem();
	JMenuItem ppNewRes = new JMenuItem();
	JMenuItem ppRefresh = new JMenuItem();

	public ResourcesPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	void jbInit() throws Exception {
		toolBar.setFloatable(false);
		this.setLayout(borderLayout1);

		// Style new resource button
		newResB.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/addresource.png")));
		newResB.setEnabled(true);
		newResB.setMaximumSize(new Dimension(24, 24));
		newResB.setMinimumSize(new Dimension(24, 24));
		newResB.setToolTipText(Local.getString("New resource"));
		newResB.setRequestFocusEnabled(false);
		newResB.setPreferredSize(new Dimension(24, 24));
		newResB.setFocusable(false);
		newResB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newResB_actionPerformed(e);
			}
		});
		newResB.setBorderPainted(false);

		// Set up the resources table
		resourcesTable.setMaximumSize(new Dimension(32767, 32767));
		resourcesTable.setRowHeight(24);

		// Style the remove resource button
		removeResB.setBorderPainted(false);
		removeResB.setFocusable(false);
		removeResB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeResB_actionPerformed(e);
			}
		});
		removeResB.setPreferredSize(new Dimension(24, 24));
		removeResB.setRequestFocusEnabled(false);
		removeResB.setToolTipText(Local.getString("Remove resource"));
		removeResB.setMinimumSize(new Dimension(24, 24));
		removeResB.setMaximumSize(new Dimension(24, 24));
		removeResB.setIcon(
				new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/removeresource.png")));
		removeResB.setEnabled(false);

		// Style the scroll pane
		scrollPane.getViewport().setBackground(Color.white);

		// Add two separators to the top toolbar
		toolBar.addSeparator(new Dimension(8, 24));
		toolBar.addSeparator(new Dimension(8, 24));

		// Create a popup listener, and add it to the scroll pane and resources table
		PopupListener ppListener = new PopupListener();
		scrollPane.addMouseListener(ppListener);
		resourcesTable.addMouseListener(ppListener);

		// Add list selection event tracker to resources table
		resourcesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				boolean enbl = (resourcesTable.getRowCount() > 0) && (resourcesTable.getSelectedRow() > -1);

				removeResB.setEnabled(enbl);
				ppRemoveRes.setEnabled(enbl);
				ppRun.setEnabled(enbl);
			}
		});

		// Style the refresh button
		refreshB.setBorderPainted(false);
		refreshB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshB_actionPerformed(e);
			}
		});
		refreshB.setFocusable(false);
		refreshB.setPreferredSize(new Dimension(24, 24));
		refreshB.setRequestFocusEnabled(false);
		refreshB.setToolTipText(Local.getString("Refresh"));
		refreshB.setMinimumSize(new Dimension(24, 24));
		refreshB.setMaximumSize(new Dimension(24, 24));
		refreshB.setEnabled(true);
		refreshB.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/refreshres.png")));

		// Style the resource dialog
		resPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
		
		// Create/style an 'open resource' menu item
		ppRun.setFont(new java.awt.Font("Dialog", 1, 11));
		ppRun.setText(Local.getString("Open resource") + "...");
		ppRun.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppRun_actionPerformed(e);
			}
		});
		ppRun.setEnabled(false);

		// Create/style a 'remove resource' menu item
		ppRemoveRes.setFont(new java.awt.Font("Dialog", 1, 11));
		ppRemoveRes.setText(Local.getString("Remove resource"));
		ppRemoveRes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppRemoveRes_actionPerformed(e);
			}
		});
		ppRemoveRes.setIcon(
				new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/removeresource.png")));
		ppRemoveRes.setEnabled(false);
		
		// Create/style a 'new resource' menu item
		ppNewRes.setFont(new java.awt.Font("Dialog", 1, 11));
		ppNewRes.setText(Local.getString("New resource") + "...");
		ppNewRes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppNewRes_actionPerformed(e);
			}
		});
		ppNewRes.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/addresource.png")));

		// Create/style a 'refresh' menu item
		ppRefresh.setFont(new java.awt.Font("Dialog", 1, 11));
		ppRefresh.setText(Local.getString("Refresh"));
		ppRefresh.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppRefresh_actionPerformed(e);
			}
		});
		ppRefresh.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/refreshres.png")));

		// Add menu items to the toolbar
		toolBar.add(newResB, null);
		toolBar.add(removeResB, null);
		toolBar.addSeparator();
		toolBar.add(refreshB, null);
		
		// Add the scrollpane (and resources table inside of it) to this
		this.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add(resourcesTable, null);
		
		// Add the toolbar to this
		this.add(toolBar, BorderLayout.NORTH);
		
		// Add menu items to the context menu
		resPPMenu.add(ppRun);
		resPPMenu.addSeparator();
		resPPMenu.add(ppNewRes);
		resPPMenu.add(ppRemoveRes);
		resPPMenu.addSeparator();
		resPPMenu.add(ppRefresh);

		// remove resources using the DEL key
		resourcesTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (resourcesTable.getSelectedRows().length > 0 && e.getKeyCode() == KeyEvent.VK_DELETE)
					ppRemoveRes_actionPerformed(null);
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * Add a new resource when clicking the new resource button
	 * @param e the new resource button action event
	 */
	void newResB_actionPerformed(ActionEvent e) {
		// Open the Add Resource Dialog and style it
		AddResourceDialog dlg = new AddResourceDialog(App.getFrame(), Local.getString("New resource"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
				(frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		
		
		if (dlg.CANCELLED)
			return;
		if (dlg.localFileRB.isSelected()) {
			// Add the resource type if it doesn't exist
			String fpath = dlg.pathField.getText();
			MimeType mt = MimeTypesList.getMimeTypeForFile(fpath);
			if (mt.getMimeTypeId().equals("__UNKNOWN")) {
				mt = addResourceType(fpath);
				if (mt == null)
					return;
			}
			
			
			if (!checkApp(mt))
				return;
			
			// if file if projectFile, than copy the file and change url.
			if (dlg.projectFileCB.isSelected()) {
				fpath = copyFileToProjectDir(fpath);
				CurrentProject.getResourcesList().addResource(fpath, false, true);
			} else
				CurrentProject.getResourcesList().addResource(fpath);

			resourcesTable.tableChanged();
		} else {
			if (!Util.checkBrowser())
				return;
			CurrentProject.getResourcesList().addResource(dlg.urlField.getText(), true, false);
			resourcesTable.tableChanged();
		}
	}

	void removeResB_actionPerformed(ActionEvent e) {
		int[] toRemove = resourcesTable.getSelectedRows();
		String msg = "";
		if (toRemove.length == 1)
			msg = Local.getString("Remove the shortcut to resource") + "\n'"
					+ resourcesTable.getModel().getValueAt(toRemove[0], 0) + "'";

		else
			msg = Local.getString("Remove") + " " + toRemove.length + " " + Local.getString("shortcuts");
		msg += "\n" + Local.getString("Are you sure?");
		int n = JOptionPane.showConfirmDialog(App.getFrame(), msg, Local.getString("Remove resource"),
				JOptionPane.YES_NO_OPTION);
		if (n != JOptionPane.YES_OPTION)
			return;
		for (int i = 0; i < toRemove.length; i++) {
			CurrentProject.getResourcesList().removeResource(
					((Resource) resourcesTable.getModel().getValueAt(toRemove[i], ResourcesTable._RESOURCE)).getPath());
		}
		resourcesTable.tableChanged();
	}

	/**
	 * Add a new resource type 
	 * @param fpath the location of the resource type
	 * @return a mime type updated for the new resource type
	 */
	MimeType addResourceType(String fpath) {
		// Create and style a resource type dialog
		ResourceTypeDialog dlg = new ResourceTypeDialog(App.getFrame(), Local.getString("Resource type"));
		Dimension dlgSize = new Dimension(420, 300);
		dlg.setSize(dlgSize);
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.ext = MimeTypesList.getExtension(fpath);
		dlg.setVisible(true);
		
		// If select cancel, return null
		if (dlg.CANCELLED)
			return null;
		
		// Get the selected type
		int ix = dlg.getTypesList().getSelectedIndex();
		
		// Add the selected type to mime type list and save it
		MimeType mt = (MimeType) MimeTypesList.getAllMimeTypes().toArray()[ix];
		mt.addExtension(MimeTypesList.getExtension(fpath));
		CurrentStorage.get().storeMimeTypesList();
		
		// Return the mime type
		return mt;
	}

	/**
	 * Check to make sure the app exists. 
	 * @param mt the mime type
	 * @return true if the app exists, false if otherwise (or cancelled)
	 */
	boolean checkApp(MimeType mt) {
		// If the selected app id is not stored in the mime types list, 
		// generate a new id for it. 
		String appId = mt.getAppId();
		AppList appList = MimeTypesList.getAppList();
		File d;
		if (appId == null) {
			appId = Util.generateId();
			d = new File("/");
		} else {
			// If the app execution file exists, then return true. If not, then
			// get it's parent. 
			File exe = new File(appList.getFindPath(appId) + "/" + appList.getExec(appId));
			if (exe.isFile())
				return true;
			d = new File(exe.getParent());
			while (!d.exists())
				d = new File(d.getParent());
		}
		
		// Style app selection dialog
		SetAppDialog dlg = new SetAppDialog(App.getFrame(), Local.getString(
				Local.getString("Select the application to open files of type") + " '" + mt.getLabel() + "'"));
		Dimension dlgSize = new Dimension(420, 300);
		dlg.setSize(dlgSize);
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setDirectory(d);
		dlg.appPanel.argumentsField.setText("$1");
		dlg.setVisible(true);
		
		// If cancel, then return false. Else, get the application name from the 
		// application text field and set it in mime type. 
		if (dlg.CANCELLED)
			return false;
		File f = new File(dlg.appPanel.applicationField.getText());

		appList.addOrReplaceApp(appId, f.getParent().replace('\\', '/'), f.getName().replace('\\', '/'),
				dlg.appPanel.argumentsField.getText());
		mt.setApp(appId);
		/*
		 * appList.setFindPath(appId,
		 * chooser.getSelectedFile().getParent().replace('\\','/'));
		 * appList.setExec(appId,
		 * chooser.getSelectedFile().getName().replace('\\','/'));
		 */
		CurrentStorage.get().storeMimeTypesList();
		return true;
	}

	/**
	 * Run the application
	 * @param fpath the path of the file to run
	 */
	void runApp(String fpath) {
		MimeType mt = MimeTypesList.getMimeTypeForFile(fpath);
		if (mt.getMimeTypeId().equals("__UNKNOWN")) {
			mt = addResourceType(fpath);
			if (mt == null)
				return;
		}
		if (!checkApp(mt))
			return;
		String[] command = MimeTypesList.getAppList().getCommand(mt.getAppId(), fpath);
		if (command == null)
			return;
		/* DEBUG */
		System.out.println("Run: " + command[0]);
		try {
			Runtime.getRuntime().exec(command); // Try running the file with the external application
		} catch (Exception ex) {
			new ExceptionDialog(ex, "Failed to run an external application <br><code>" + command[0] + "</code>",
					"Check the application path and command line parameters for this resource type "
							+ "(File-&gt;Preferences-&gt;Resource types).");
		}
	}

	/**
	 * Run in a browser 
	 * @param url the url to run
	 */
	void runBrowser(String url) {
		Util.runBrowser(url);
	}

	class PopupListener extends MouseAdapter {
		/**
		 * Select resource in table
		 * @param e a mouse click event
		 */
		public void mouseClicked(MouseEvent e) {
			if ((e.getClickCount() == 2) && (resourcesTable.getSelectedRow() > -1)) {
				String path = (String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 3);
				if (path.length() > 0)
					runApp(path);
				else
					runBrowser((String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 0));
			}
			// editTaskB_actionPerformed(null);
		}

		/**
		 * Maybe show the context menu
		 * @param e a mouse press event
		 */
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		/**
		 * Maybe show the context menu
		 * @param e a mouse press event
		 */
		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		/**
		 * If the mouse event is a popup trigger, show the context menu
		 * @param e a mouse event
		 */
		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				resPPMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

	}

	/**
	 * Refresh the table
	 * @param e pressing the refresh button
	 */
	void refreshB_actionPerformed(ActionEvent e) {
		resourcesTable.tableChanged();
	}

	/**
	 * Open the file by running an app/browser
	 * @param e the run resource button
	 */
	void ppRun_actionPerformed(ActionEvent e) {
		String path = (String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 3);
		if (path.length() > 0)
			runApp(path);
		else
			runBrowser((String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 0));
	}

	/**
	 * Remove the resource
	 * @param e the remove resource button
	 */
	void ppRemoveRes_actionPerformed(ActionEvent e) {
		removeResB_actionPerformed(e);
	}

	/**
	 * Add a new resource
	 * @param e the new resource button
	 */
	void ppNewRes_actionPerformed(ActionEvent e) {
		newResB_actionPerformed(e);
	}

	/**
	 * Refresh the list of resources
	 * @param e the refresh button
	 */
	void ppRefresh_actionPerformed(ActionEvent e) {
		resourcesTable.tableChanged();
	}

	/**
	 * Copy a file to the directory of the current project
	 * 
	 * @param srcStr  The path of the source file.
	 * @param destStr The destination path.
	 * @return The new path of the file.
	 */
	String copyFileToProjectDir(String srcStr) {

		String JN_DOCPATH = Util.getEnvDir();

		String baseName;
		int i = srcStr.lastIndexOf(File.separator);
		if (i != -1) {
			baseName = srcStr.substring(i + 1);
		} else
			baseName = srcStr;

		String destStr = JN_DOCPATH + CurrentProject.get().getID() + File.separator + "_projectFiles" + File.separator
				+ baseName;

		File f = new File(JN_DOCPATH + CurrentProject.get().getID() + File.separator + "_projectFiles");
		if (!f.exists()) {
			f.mkdirs();
		}
		System.out.println("[DEBUG] Copy file from: " + srcStr + " to: " + destStr);

		try {
			FileInputStream in = new FileInputStream(srcStr);
			FileOutputStream out = new FileOutputStream(destStr);
			byte[] buf = new byte[4096];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			System.err.println(e.toString());
		}

		return destStr;
	}
}