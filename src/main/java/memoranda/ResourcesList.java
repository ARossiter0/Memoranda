/**
 * ResourcesList.java
 * Created on 24.03.2003, 18:25:59 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.util.Vector;

import nu.xom.Document;
/**
 * 
 */
/*$Id: ResourcesList.java,v 1.4 2007/03/20 06:21:46 alexeya Exp $*/
public interface ResourcesList {
    
	/**
	 * Retrieve all of the resources
	 * @return
	 */
    Vector getAllResources();
    
    //Vector getResourcesForTask(String taskId);
    
    /**
     * Get a resource at a path
     * @param path the path of the resource
     * @return the resource
     */
    Resource getResource(String path);
    
    /**
     * Add a resource
     * @param path the path of the resource
     * @param isInternetShortcut if the resource is an internet shortcut
     * @param isProjectFile if the resource is a project file
     */
    void addResource(String path, boolean isInternetShortcut, boolean isProjectFile);
    
    /**
     * Set resource student visibility
     * @param path of resource to set visibility
     */
    void setResourceVisibility(String path);
    
    /**
     * Add a resource 
     * @param path the path of the resource
     */
    void addResource(String path);
    
    //void addResource(String path, String taskId);
    
    /**
     * Remove a resource
     * @param path the path of the resource
     */
    void removeResource(String path);
        
    /**
     * Get the number of resources
     * @return the number of resources
     */
    int getAllResourcesCount();
    
    /**
     * Get the xml content
     * @return the xml document containing resources
     */
    Document getXMLContent();

}
