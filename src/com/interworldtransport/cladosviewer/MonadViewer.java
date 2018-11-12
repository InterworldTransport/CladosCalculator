/* <h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport. All rights reserved.<br>
---com.interworldtransport.cladosviewer.MonadViewer-----------------------------
<p>
Interworld Transport grants you ("Licensee") a license to this software
under the terms of the GNU General Public License.<br>
A full copy of the license can be found bundled with this package or code file.
<p>
If the license file has become separated from the package, code file, or binary
executable, the Licensee is still expected to read about the license at the
following URL before accepting this material.
<blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
<p>
Use of this code or executable objects derived from it by the Licensee states
their willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
JP Aerospace for a free copy.
<p>
---com.interworldtransport.cladosviewer.MonadViewer-----------------------------
*/
package com.interworldtransport.cladosviewer;
import com.interworldtransport.clados.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
 * The MonadViewer class will display Monads and allow the user to
 * manipulate them via the Monad's methods.
 *
 * @version  0.80, $Date: 2005/08/25 06:36:13 $
 * @author   Dr Alfred W Differ
 * @since clados 0.5
 */

public class MonadViewer extends JFrame implements ActionListener
{

/**
 * The Menu for the application.
 */
    public		ViewerMenu		TheMenuBar;
/**
 * The EventModel for the application.
 */
    public		ViewerEventModel	EventModel;
/**
 * The Center Display Panel for the application.
 */
    public		ViewerPanel		CenterAll;
/**
 * The Status Display Panel for the application.
 */
    public		UtilityStatusBar	StatusLine;

    public		JPanel			HeaderLine;
    public		JButton			push;
    public		JButton			pop;
    public		JButton			copy;
    public		JButton			remove;
    public		JButton			newone;
    public		JButton			add;
    public		JButton			lmult;
    public		JButton			rmult;
    public		JButton			scale;
    public		JButton			inverse;

    public		Properties		IniProps;
/** This FileWriter points to the actual save file for historical snapshot data
 */
    public		FileWriter		to;
    public		File 			fIni;
    private		JFileChooser 	fc;
/**
 * This is the main constructor the the Monad Viewer
 */
    public MonadViewer(	String pTitle,
    					String pConfig)
    throws 		UtilitiesException,
    			BadSignatureException,
    			CantGetIniException
    {
    	super(pTitle);
    	this.addWindowListener( new WindowAdapter()
    			{
    		public void windowClosing(WindowEvent e)
    		{
    			System.exit(0);
    		}
    			}
    	);
    	this.getConfigProps(pConfig);
    	Container cp=this.getContentPane();
    	
    	this.StatusLine=new UtilityStatusBar();
    	cp.add(StatusLine, "South");
    	
    	ImageIcon temp = createImageIcon(IniProps.getProperty("MonadViewer.Desktop.SplashImage"));
    	SplashWindow splash=new SplashWindow(temp);
    	
    	this.HeaderLine=new JPanel();
    	HeaderLine.setLayout(new GridBagLayout());
    	HeaderLine.setBorder(new EmptyBorder(1, 1, 1, 1));
    	cp.add(HeaderLine,"North");
    	
    	this.HeaderLine.setBackground(new Color(200, 200, 200));
    	
    	GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		cn.fill=GridBagConstraints.HORIZONTAL;
		cn.anchor=GridBagConstraints.NORTHWEST;
		
		cn.gridx = 0;
		cn.gridy = 0;
		cn.weightx=1;
		cn.weighty=0;
    	
    	this.push=new JButton("Push");
    	push.addActionListener(this);
    	HeaderLine.add(push, cn);
    	cn.gridx++;
    	
    	this.pop=new JButton("Pop");
    	pop.addActionListener(this);
    	HeaderLine.add(pop, cn);
    	cn.gridx++;
    	
    	this.copy = new JButton("Copy");
    	copy.addActionListener(this);
    	HeaderLine.add(copy, cn);
    	//copy.setEnabled(false);
    	cn.gridx++;
    	
    	this.remove = new JButton("Remove");
    	remove.addActionListener(this);
    	HeaderLine.add(remove, cn);
    	cn.gridx++;
    	
    	this.newone = new JButton("New");
    	newone.addActionListener(this);
    	HeaderLine.add(newone, cn);
    	//newone.setEnabled(false);;
    	cn.gridx++;
    	
    	cn.gridheight=2;
    	cn.weighty=1;
    	HeaderLine.add(new JLabel(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.HeaderImage2"))),cn);
    	cn.gridheight=1;
    	cn.weighty=0;
    	
    	cn.gridx = 0;
		cn.gridy++;
    	
    	this.add = new JButton("Add");
    	add.addActionListener(this);
    	HeaderLine.add(add, cn);
    	cn.gridx++;
    	
    	this.scale = new JButton("Scale");
    	scale.addActionListener(this);
    	HeaderLine.add(scale, cn);
    	cn.gridx++;
    	
    	this.lmult = new JButton("Left Multiply");
    	lmult.addActionListener(this);
    	HeaderLine.add(lmult, cn);
    	cn.gridx++;
    	
    	this.rmult = new JButton("Right Multiply");
    	rmult.addActionListener(this);
    	HeaderLine.add(rmult, cn);
    	cn.gridx++;
    	
    	this.inverse = new JButton("Inverse");
    	inverse.addActionListener(this);
    	HeaderLine.add(inverse, cn);
    	cn.gridx++;
    	
    	this.TheMenuBar=new ViewerMenu(this);
    	this.setJMenuBar(this.TheMenuBar);
    	
    	this.CenterAll=new ViewerPanel(this);
    	cp.add(CenterAll, "Center");
    	
    	this.EventModel=new ViewerEventModel(this.TheMenuBar);
    	
    	fc = new JFileChooser();
    	fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	
    	splash.dispose();
    	
    	setLocation(100, 10);
    	StatusLine.setStatusMsg("Initialization Complete\n");
    	
    }

    public void actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	
    	if (command.equals("Add"))
    	{
    		MonadPanel temp0=CenterAll.getNyadPanel(0).getMonadPanel(0);
    		MonadPanel temp1=CenterAll.getNyadPanel(1).getMonadPanel(0);
    		Monad Monad0=null;
    		Monad Monad1=null;
    		
    		if (temp0!=null)
    		{
    			Monad0=temp0.getMonad();
    		}
    		if (temp1!=null)
    		{
    			Monad1=temp1.getMonad();
    		}
    		
    		if (Monad0!=null || Monad1!=null)
    		{
    			try
    			{
    				Monad0.Add(Monad1);
    				temp0.setBottomFields();
    				StatusLine.setStatusMsg("Second Monad added to the first.\n");
    			}
    			catch (NoReferenceMatchException e)
    			{
    				StatusLine.setStatusMsg("Reference Match error between second and first monads.\n");
    				StatusLine.setStatusMsg("Second Monad not added to the first.\n");
    			}
    		}
    		else
    		{
    			StatusLine.setStatusMsg("Second Monad not added to the first.\n");
    		}
    	}
    	
    	if (command.equals("Scale"))
    	{
    		MonadPanel MP0=CenterAll.getNyadPanel(0).getMonadPanel(0);
    		Monad Monad0=MP0.getMonad();
    		Monad0.Scale(new Double(StatusLine.stview.getText()).doubleValue());
    		MP0.setBottomFields();
    		StatusLine.setStatusMsg("First Monad has been rescaled by a factor of: "+
    				StatusLine.stview.getText()+"\n");
    	}
    	
    	if (command.equals("Left Multiply"))
    	{
    		MonadPanel temp0=CenterAll.getNyadPanel(0).getMonadPanel(0);
    		MonadPanel temp1=CenterAll.getNyadPanel(1).getMonadPanel(0);
    		Monad Monad0=null;
    		Monad Monad1=null;
    		
    		if (temp0!=null)
    		{
    			Monad0=temp0.getMonad();
    		}
    		if (temp1!=null)
    		{
    			Monad1=temp1.getMonad();
    		}
    		
    		if (Monad0!=null || Monad1!=null)
    		{
    			try
    			{
    				Monad0.LeftMultiply(Monad1);
    				temp0.setBottomFields();
    				StatusLine.setStatusMsg("Second Monad muliplied against the first from the left.\n");
    			}
    			catch (NoReferenceMatchException e)
    			{
    				StatusLine.setStatusMsg("Reference Match error between second and first monads.\n");
    				StatusLine.setStatusMsg("Second Monad not muliplied against the first from the left.\n");
    			}
    		}
    		else
    		{
    			StatusLine.setStatusMsg("Second Monad not muliplied against the first from the left.\n");
    		}
    	}
    	
    	if (command.equals("Right Multiply"))
    	{
    		MonadPanel temp0=CenterAll.getNyadPanel(0).getMonadPanel(0);
    		MonadPanel temp1=CenterAll.getNyadPanel(1).getMonadPanel(0);
    		Monad Monad0=null;
    		Monad Monad1=null;
    		
    		if (temp0!=null)
    		{
    			Monad0=temp0.getMonad();
    		}
    		if (temp1!=null)
    		{
    			Monad1=temp1.getMonad();
    		}
    		
    		if (Monad0!=null || Monad1!=null)
    		{
    			try
    			{
    				Monad0.RightMultiply(Monad1);
    				temp0.setBottomFields();
    				StatusLine.setStatusMsg("Second Monad muliplied against the first from the right.\n");
    			}
    			catch (NoReferenceMatchException e)
    			{
    				StatusLine.setStatusMsg("Reference Match error between second and first monads.\n");
    				StatusLine.setStatusMsg("Second Monad not muliplied against the first from the right.\n");
    			}
    		}
    		else
    		{
    			StatusLine.setStatusMsg("Second Monad not muliplied against the first from the right.\n");
    		}
    	}
    	
    	if (command.equals("Inverse"))
    	{
    		MonadPanel MP0=CenterAll.getNyadPanel(0).getMonadPanel(0);
    		Monad Monad0=MP0.getMonad();
    		try
    		{
    			Monad0.Inverse();
    			MP0.setBottomFields();
    			StatusLine.setStatusMsg("First Monad has been changed to its inverse.\n");
    		}
    		catch (NoInverseException i)
    		{
    			StatusLine.setStatusMsg("No Inverse error for first monad.\n");
    		}
    		catch (NoInverseCalculationMethodException c)
    		{
    			StatusLine.setStatusMsg("Don't know how to calculate the inverse for first monad.\n");
    		}
    		catch (CladosException e)
    		{
    			StatusLine.setStatusMsg("General clados error for first monad.\n");
    		}
    		
    	}
    	
    	if (command.equals("Push"))
    	{
    		CenterAll.push();
    	}
    	
    	if (command.equals("Pop"))
    	{
    		CenterAll.pop();
    	}
    	
    	if (command.equals("Copy"))
    	{
    		int temp2=CenterAll.getNyadListSize();
    		if (temp2>0)
    		{
    			try
    			{
    				NyadPanel tempN=CenterAll.getNyadPanel(CenterAll.getPaneFocus());
    				Nyad newcopy2=null;
    				for (int j=0; j<tempN.getOrder(); j++)
    				{
    					MonadPanel tempP=tempN.getMonadPanel(j);
        				ArrayList tempC=tempP.getCoeffs();
        				double coeffs[]=new double[tempP.getMonad().getLinearDimension()+1];
        				for (int k=1; j<coeffs.length; j++)
        				{
        					JTextField temp= (JTextField)tempC.get(k);
        					coeffs[k]= new Double(temp.getText()).doubleValue();
        				}
        				
        				Monad newcopy=new Monad(	tempP.name.getText()+"-Copy",
        						tempP.aname.getText(),
        						tempP.frame.getText(),
        						tempP.foot.getText(),
        						tempP.sig.getText(), 
        						coeffs);
        				if (newcopy2==null)
        				{
        					newcopy2=new Nyad("New", newcopy);
        				}
        				else 
        				{
        					newcopy2.addMonad(newcopy);
        				}
    				}
    				CenterAll.addSTab(newcopy2);
    			}
    			catch (UtilitiesException e)
    			{
    				StatusLine.setStatusMsg("Could not create copy from toolbar.\n");
    			}
    			catch (BadSignatureException es)
    			{
    				StatusLine.setStatusMsg("Could not create copy from toolbar due to signature.\n");
    			}
    			catch (ListAppendException lae)
    			{
    				StatusLine.setStatusMsg("Could not create copy from toolbar due to non-unique monad names.\n");
    			}
    		}
    	}
    	
    	if (command.equals("Remove"))
    	{
    		int temp=CenterAll.NyadPanes.getTabCount();
    		if (temp>1)
    		{
    			CenterAll.removeTab(CenterAll.NyadPanes.getSelectedIndex());
    		}
    	}
    	
    	if (command.equals("New"))
    	{
    		try
    		{
    			CreateDialog create = new CreateDialog(this, " ");
    		}
    		catch (UtilitiesException e)
    		{
    			//Do nothing.  Exception implies user doesn't get to create
    			//a new Monad, so nothing is the correct action.
    			System.out.println("Couldn't construct create dialog.");
    		}
    		catch (BadSignatureException es)
    		{
    			//Do nothing.  Exception implies user doesn't get to create
    			//a new Monad, so nothing is the correct action.
    			System.out.println("Couldn't construct create dialog.");
    		}
    	}
    }

    protected ImageIcon createImageIcon(String path)
    {
    	ImageIcon temp = new ImageIcon(path);
    	if (temp != null) 
    	{
    		return temp;
    	} 
    	else 
    	{
    		StatusLine.setStatusMsg("(Calculator)Could not get: "+path+"\n");
    		return null;
    	}
    }
    
    protected void getConfigProps(String pConfName)
    	throws CantGetIniException
	{
	 this.fIni=null;
	 boolean bIni = false;
	 try
	 {
		 fIni=new File(pConfName);
		 bIni=fIni.exists();
		 if (bIni) bIni=fIni.isFile();
		 if (bIni) bIni=fIni.canRead();
		 if (!bIni) throw new CantGetIniException("The configuration file is not present or ready.");
		 //Getting here implied the configuration file has been found and readable

		 this.IniProps=new Properties(System.getProperties());
		 this.IniProps.load(new BufferedInputStream(new FileInputStream(fIni)));

	 }//end of try block
	 catch(Exception e)
	 {
		 System.out.println("IO Problem:  Incomplete Access to Associated INI files.");
		 throw new CantGetIniException("No Access to INI file.");
	 }
	} //end of getConfigProps

/**
 * This method does the initial file handling for the snapshot save file.
 * It doesn't do much right now except check to see if the file is there.
 */
    protected void getSaveFile(String pSaveName)
    throws 	IOException, 
    		CantGetSaveException
    {
    	String SaveName=null;
    	if (pSaveName==null)
    	{
    		SaveName=IniProps.getProperty("MonadViewer.Desktop.Snapshot");
    	}
    	else
    	{
    		SaveName=pSaveName;
    	}

    	File fSave=null;
    	boolean bSave=false;
    	
    	fSave=new File(SaveName);
    	bSave=fSave.exists();
    	if (bSave) bSave=fSave.isFile();
    	if (bSave) bSave=fSave.canWrite();
    	if (!bSave)
    	{
    		throw new CantGetSaveException("No access to snapshot save file.");
    	}
    	//Getting here implies the Save file has been found and is writeable
    	this.to=new FileWriter(fSave);
    	if (to!=null) System.out.println("Write file is not null");
    	else System.out.println("Write file is null");

    }

/**
 * This method saves snapshot data to the save file.
 */
    protected void saveSnapshot(String pType)
    	throws 	IOException, 
    			CantGetSaveException
    {
    	String content=makeSnapshotContent();
    	String SaveName=IniProps.getProperty("MonadViewer.Desktop.Snapshot");
    	if (pType==null)
    	{
    		if (to==null)
    		{
    			File fIni=null;
    	    	boolean bSave=false;
    	    	
    	    	fIni=new File(SaveName);
    	    	bSave=fIni.exists();
    	    	if (bSave) bSave=fIni.isFile();
    	    	if (bSave) bSave=fIni.canWrite();
    	    	if (!bSave)
    	    	{
    	    		throw new CantGetSaveException("No access to snapshot save file.");
    	    	}
    	    	//Getting here implies the Save file has been found and is writeable
    	    	this.to=new FileWriter(fIni, true);    	    	
    		}
    	}
    	else
    	{
    		int returnVal = fc.showSaveDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) 
    		{
    			fIni = fc.getSelectedFile();
    			SaveName=fIni.getName();
    			//This is where a real application would open the file.
    			boolean bSave=false;
    			bSave=fIni.exists();
    			if (bSave) bSave=fIni.isFile();
    			if (bSave) bSave=fIni.canWrite();
    			if (!bSave)
    			{
    				throw new CantGetSaveException("No access to snapshot save file.");
    			}
    			//Getting here implies the Save file has been found and is writeable
    			this.to=new FileWriter(fIni, true);
    			//Change the Snapshot property so it can be used for 'Save' next
    			//time.  No chooser dialog should be needed then.
    			IniProps.setProperty("MonadViewer.Desktop.Snapshot", SaveName);
    		} 
    	}
	
	this.to.write(content);
	this.to.write("\r\n");
	this.to.flush();
	StatusLine.setStatusMsg("Snapshot of stack is saved.\n");

    }

    private String makeSnapshotContent()
    {
    	StringBuffer content=new StringBuffer();

    	content.append("<Application Name=\"clados Calculator\", ");
    	content.append("Rights=\"Copyright 2010 Interworld Transport\", ");
    	content.append("Licensee=\" ");
    	content.append(IniProps.getProperty("MonadViewer.User.Name"));
    	content.append("\" />");
    	
    	content.append("\r\n");

    	int listsize=CenterAll.getNyadListSize();
    	content.append("<NyadCount size=\"");
    	content.append(new String(new Integer(listsize).toString()));
    	content.append("\" />");

    	content.append("\r\n");

    	for (int j=0; j<listsize; j++)
    	{
    		
    		NyadPanel tempNp=CenterAll.getNyadPanel(j);
    		    		
    		content.append("<Nyad");
    		
    		content.append(" Name=\"");
    		content.append(tempNp.name.getText());
    		content.append("\", ");
    		
    		content.append(" Order=\"");
    		content.append(tempNp.order.getText());
    		content.append("\", ");
    		
    		content.append(" Algebra=\"");
    		content.append(tempNp.aname.getText());
    		content.append("\", ");
    		
    		content.append(" Foot=\"");
    		content.append(tempNp.foot.getText());
    		
    		content.append("\">\r\n");
    		
    		//Monad temp=tempNp.getMonadPanel(0).getMonad();
    		//Start another loop here when more than one monad is in a nyad
    		int ordersize=tempNp.getOrder();
    		for (int m=0; m<ordersize; m++)
    		{
    			MonadPanel tempMp=tempNp.getMonadPanel(m);
    			
    			content.append("<Monad");

        		content.append(" name=\"");
        		content.append(tempMp.name.getText());
        		content.append("\", ");

        		content.append("algebra=\"");
        		content.append(tempMp.aname.getText());
        		content.append("\", ");
        		
        		content.append("signature=\"");
        		content.append(tempMp.sig.getText());
        		content.append("\", ");

        		content.append("frame=\"");
        		content.append(tempMp.frame.getText());
        		content.append("\", ");

        		content.append("foot=\"");
        		content.append(tempMp.foot.getText());
        		content.append("\">");
        		content.append("\r\n");

        		
        		Monad temp=tempMp.getMonad();
        		double[] tc=temp.getCoeff();
        		content.append("\t<Coefficients>");
        		content.append("\r\n");
        		for (int k=1; k<temp.getLinearDimension()+1; k++)
        		{
        		    content.append("\t\t<Item>");
        		    content.append(new String(new Double(tc[k]).toString()));
        		    content.append("</Item>");
        		    content.append("\r\n");
        		}
        		content.append("\t</Coefficients>");
        		content.append("\r\n");
        		content.append("</Monad>");
        		content.append("\r\n");
    		}
    		content.append("</Nyad>");
    		content.append("\r\n");
    	}

    	String contentstring = new String(content);
    	return contentstring;
    }
    
    protected void terminateModel()
    {
	this.to=null;
	System.exit(0);
    }

    public static void main(String[] args)
	{
	 String TitleName="Monad Viewer Utility";
	 String ConfName="./conf/MonadViewer.conf";

	 if (args.length%2==1)
	 {
	  System.out.println("Usage: MonadViewer [-t TitleString -c ConfigFile] ");
	  System.exit(0);
	 }

	 for (int i=0; i<args.length; i=i+2)
	 {
	  if (args[i].equals("-t")) TitleName=args[i+1];
	  if (args[i].equals("-c")) ConfName=args[i+1];
	 }
	 try
	 {
		 JFrame fr = new MonadViewer(TitleName, ConfName);
		 fr.pack();
		 fr.setVisible(true);
	 }
	 catch (BadSignatureException be)
	 {
	 	 System.out.println("Bad Signature Exception occured while constructing a Monad");
	 	 System.exit(0);
	 }
	 catch (UtilitiesException e)
	 {
	 	 System.out.println("Exception occured while constructing main GUI");
	 	 System.exit(0);
	 }

	}

} 
