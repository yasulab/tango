package tango.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class TitleCommandBar extends Composite{
	private HTML titleLabel;
	private HorizontalPanel titlePanel = new HorizontalPanel();
	private Widget lastCommand;
	public TitleCommandBar( String title ){
		initWidget( titlePanel );
		titlePanel.setWidth("100%");
		setStyleName("gwtapps-TitleBar");
		titleLabel = new HTML( title );
		titleLabel.setStyleName("gwtapps-TitleBarTitle");
		titleLabel.setWordWrap( false );
		titlePanel.add( titleLabel );
	}
	public void addWidget( Widget widget )
	{
		if( lastCommand != null )
			titlePanel.setCellWidth(lastCommand, "");
		lastCommand = widget;
		titlePanel.add( lastCommand );
		titlePanel.setCellWidth(lastCommand, "100%");
		titlePanel.setCellVerticalAlignment( lastCommand, HasVerticalAlignment.ALIGN_MIDDLE );
	}
	
	public void addCommand( String name, ClickListener command )
	{
		Hyperlink hyperlink = new Hyperlink( name, name );
		hyperlink.addClickListener( command );
		hyperlink.setStyleName("gwtapps-TitleBarCommand");
		addWidget( hyperlink );
		
	}
	public void setText( String text )
	{
		titleLabel.setText(text);
	}
}
