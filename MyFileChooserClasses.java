

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import javax.swing.filechooser.*;
import java.beans.*;

class MyFileFilter extends FileFilter {

    private static String TYPE_UNKNOWN = "Type Unknown";
    private static String HIDDEN_FILE = "Hidden File";

    private Hashtable filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    // Creates a file filter. If no filters are added, then all files are accepted.
    public MyFileFilter() {
	this.filters = new Hashtable();
    }

    // Creates a file filter that accepts files with the given extension.
    public MyFileFilter(String extension) {
	this(extension,null);
    }

    // Creates a file filter that accepts the given file type.
    //Example: new ExampleFileFilter("jpg", "JPEG Image Images");
    public MyFileFilter(String extension, String description) {
	this();
	if(extension!=null) addExtension(extension);
 	if(description!=null) setDescription(description);
    }

    // Creates a file filter from the given string array.
    // Example: new ExampleFileFilter(String {"gif", "jpg"});
    public MyFileFilter(String[] filters) {
	this(filters, null);
    }

    // Creates a file filter from the given string array and description.
	// Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
    public MyFileFilter(String[] filters, String description) {
	this();
	for (int i = 0; i < filters.length; i++) {
	    // add filters one by one
	    addExtension(filters[i]);
	}
 	if(description!=null) setDescription(description);
    }

    // Return true if this file should be shown in the directory pane,
    // false if it shouldn't.
    public boolean accept(File f) {
	if(f != null) {
	    if(f.isDirectory()) {
		return true;
	    }
	    String extension = getExtension(f);
	    if(extension != null && filters.get(getExtension(f)) != null) {
		return true;
	    };
	}
	return false;
    }

    // Return the extension portion of the file's name .
     public String getExtension(File f) {
	if(f != null) {
	    String filename = f.getName();
	    int i = filename.lastIndexOf('.');
	    if(i>0 && i<filename.length()-1) {
		return filename.substring(i+1).toLowerCase();
	    };
	}
	return null;
    }

    /* Adds a filetype "dot" extension to filter against.
	 * For example: the following code will create a filter that filters
     * out all files except those that end in ".jpg" and ".tif":
     *
     *   ExampleFileFilter filter = new ExampleFileFilter();
     *   filter.addExtension("jpg");
     *   filter.addExtension("tif");
	 */
    public void addExtension(String extension) {
	if(filters == null) {
	    filters = new Hashtable(5);
	}
	filters.put(extension.toLowerCase(), this);
	fullDescription = null;
    }


    // Returns the human readable description of this filter. For
    // example: "JPEG and GIF Image Files (*.jpg, *.gif)"
    public String getDescription() {
	if(fullDescription == null) {
	    if(description == null || isExtensionListInDescription()) {
 		fullDescription = description==null ? "(" : description + " (";
		// build the description from the extension list
		Enumeration extensions = filters.keys();
		if(extensions != null) {
		    fullDescription += "." + (String) extensions.nextElement();
		    while (extensions.hasMoreElements()) {
			fullDescription += ", ." + (String) extensions.nextElement();
		    }
		}
		fullDescription += ")";
	    } else {
		fullDescription = description;
	    }
	}
	return fullDescription;
    }

    // Sets the human readable description of this filter. For
    public void setDescription(String description) {
	this.description = description;
	fullDescription = null;
    }

    // Determines whether the extension list (.jpg, .gif, etc) should
    // show up in the human readable description.
    public void setExtensionListInDescription(boolean b) {
	useExtensionsInDescription = b;
	fullDescription = null;
    }

    // Returns whether the extension list (.jpg, .gif, etc) should
    // show up in the human readable description.
    public boolean isExtensionListInDescription() {
	return useExtensionsInDescription;
    }
}


class FilePreviewer extends JComponent implements PropertyChangeListener
{
	ImageIcon thumbnail = null;

	public FilePreviewer(JFileChooser fc)
	{
	    setPreferredSize(new Dimension(100, 50));
	    fc.addPropertyChangeListener(this);
	}

	public void loadImage(File f)
	{
		if (f == null)
		{
               thumbnail = null;
        }
		else
		{
			ImageIcon tmpIcon = new ImageIcon(f.getPath());
			if(tmpIcon.getIconWidth() > 90)
			{
				thumbnail = new ImageIcon(
				tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
			}
			else
			{
				thumbnail = tmpIcon;
			}
	    }
	}

	public void propertyChange(PropertyChangeEvent e)
	{
	    String prop = e.getPropertyName();
	    if(prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY))
		{
			if(isShowing())
			{
				loadImage((File) e.getNewValue());
				repaint();
			}
		}
	}

	public void paint(Graphics g)
	{
		if(thumbnail!=null)
		{
			int xpos= (getWidth()- thumbnail.getIconWidth())/2;
			int ypos= (getHeight()- thumbnail.getIconHeight())/2;
			g.drawImage(thumbnail.getImage(), xpos, ypos, null);
		}
	}
}

class MyFileView extends FileView {
    private Hashtable icons = new Hashtable(5);
    private Hashtable fileDescriptions = new Hashtable(5);
    private Hashtable typeDescriptions = new Hashtable(5);

    // The name of the file.  Do nothing special here. Let
    // the system file view handle this.
    public String getName(File f) {
	return null;
    }

    // Adds a human readable description of the file.
    public void putDescription(File f, String fileDescription) {
	fileDescriptions.put(fileDescription, f);
    }

    // A human readable description of the file.
    public String getDescription(File f) {
	return (String) fileDescriptions.get(f);
    };

    // Adds a human readable type description for files. Based on "dot"
	// extension strings, e.g: ".gif". Case is ignored.
    public void putTypeDescription(String extension, String typeDescription) {
	typeDescriptions.put(typeDescription, extension);
    }

    // Adds a human readable type description for files of the type of
    // the passed in file. Based on "dot" extension strings, e.g: ".gif" Case is ignored.
    public void putTypeDescription(File f, String typeDescription) {
	putTypeDescription(getExtension(f), typeDescription);
    }

    // A human readable description of the type of the file.
    public String getTypeDescription(File f) {
	return (String) typeDescriptions.get(getExtension(f));
    }

    // Conveinience method that returnsa the "dot" extension for the given file.
    public String getExtension(File f) {
	String name = f.getName();
	if(name != null) {
	    int extensionIndex = name.lastIndexOf('.');
	    if(extensionIndex < 0) {
		return null;
	    }
	    return name.substring(extensionIndex+1).toLowerCase();
	}
	return null;
    }

    // Adds an icon based on the file type "dot" extension string, e.g: ".gif". Case is ignored.
    public void putIcon(String extension, Icon icon) {
	icons.put(extension, icon);
    }

    // Icon that reperesents this file. Default implementation returns null
    public Icon getIcon(File f) {
	Icon icon = null;
	String extension = getExtension(f);
	if(extension != null) {
	    icon = (Icon) icons.get(extension);
	}
	return icon;
    }

    // Whether the file is hidden or not. This implementation returns
    // true if the filename starts with a "."
    public Boolean isHidden(File f) {
	String name = f.getName();
	if(name != null && !name.equals("") && name.charAt(0) == '.') {
	    return Boolean.TRUE;
	} else {
	    return Boolean.FALSE;
	}
    };
}