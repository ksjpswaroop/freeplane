package org.freeplane.view.swing.map;

import java.awt.Insets;

import org.freeplane.features.nodestyle.NodeStyleModel;
import org.freeplane.features.nodestyle.NodeStyleModel.Shape;

@SuppressWarnings("serial")
public class SmallBubbleMainView extends BubbleMainView {
    final private static Insets insets = new Insets(0,  HORIZONTAL_MARGIN,  0, HORIZONTAL_MARGIN);
    
	@Override
    public
    Shape getShape() {
		return NodeStyleModel.Shape.small_bubble;
	}

    @Override
    public Insets getInsets() {
        return SmallBubbleMainView.insets;
    }
}