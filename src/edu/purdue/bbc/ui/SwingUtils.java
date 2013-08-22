package edu.purdue.bbc.ui;

import java.awt.*;

/**
 */
public class SwingUtils {

  /**
   * Finds the real location of the component relative to the Frame
   *
   * @param container The container to find the bounds for.
   * @return The real bounds of the container.
   */
  public static Point getRealLocation(Container container) {
    Point point = container.getLocation();
    for (Container c = container.getParent(); !(c instanceof Window);
         c = c.getParent()) {
      Point p = c.getLocation();
      point.x += p.x;
      point.y += p.y;
    }
    return point;
  }

  /**
   * Finds the real bounds of the component relative to the root Window
   *
   * @param container The container to find the bounds for.
   * @return The real bounds of the container.
   */
  public static Rectangle getRealBounds(Container container) {
    return getRealBounds(container, new Rectangle());
  }

  /**
   * Finds the real bounds of the component relative to the root Window
   *
   * @param container The container to find the bounds for.
   * @param rect A Rectangle to store the bounds in. This same object is
   *             returned.
   * @return The real bounds of the container.
   */
  public static Rectangle getRealBounds(Container container, Rectangle rect) {
    container.getBounds(rect);
    for (Container c = container.getParent(); !(c instanceof Window);
         c = c.getParent()) {
      Point p = c.getLocation();
      rect.x += p.x;
      rect.y += p.y;
    }
    return rect;
  }

}
