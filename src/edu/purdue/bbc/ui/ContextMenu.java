
package edu.purdue.bbc.ui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

/**
 * A class for creating a context menu on a component which will show
 * when the component is clicked on. By default the menu is triggered
 * by right-clicking.
 */
public class ContextMenu extends JPopupMenu implements MouseListener {
	protected Component target;
	protected int mouseButton;
	
	/**
	 * Creates a new ContextMenu which will be triggered by right-clicking on
	 * the specified component.
	 * 
	 * @param target The target Component.
	 */
	public ContextMenu(Component target) {
		this(target, MouseEvent.BUTTON3);
	}

	/**
	 * Creates a new ContextMenu which will be triggered by the specified click
	 * on the specified component.
	 * 
	 * @param target The target Component.
	 * @param mouseButton The mouse button which will trigger the popup. This should
	 *	be one of the MouseEvent constants.
	 */
	public ContextMenu(Component target, int mouseButton) {
		super();
		this.target = target;
		this.mouseButton = mouseButton;
		this.target.addMouseListener(this);
	}

	/**
	 * The mouseClicked method of the MouseListener interface. Triggers the popup
	 * if the proper button is pressed.
	 * 
	 * @param e The mouse event which triggered this action.
	 */
	public void mouseClicked(MouseEvent e) { 
		if (e.getButton() == this.mouseButton) {
			this.show((Component)e.getSource(), e.getX(), e.getY());
		}
	}

  /**
   * Unimplemented.
   * @param e
   */
	public void mouseEntered(MouseEvent e) { }

  /**
   * Unimplemented.
   * @param e
   */
	public void mouseExited(MouseEvent e) { }

  /**
   * Unimplemented.
   * @param e
   */
	public void mousePressed(MouseEvent e) { }

  /**
   * Unimplemented.
   * @param e
   */
	public void mouseReleased(MouseEvent e) { }

	/**
	 * Sets a new target Component for this menu.
	 * 
	 * @param target The new target Component.
	 */
	public void setTarget(Component target) {
		this.target.removeMouseListener(this);
		this.target = target;
		target.addMouseListener(this);
	}

	/**
	 * Gets the current target Component of this menu.
	 * 
	 * @return The current target Component.
	 */
	public Component getTarget() {
		return this.target;
	}

}
	
