/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2008 Joerg Mueller, Daniel Polansky, Christian Foltin, Dimitry Polivaev
 *
 *  This file is modified by Dimitry Polivaev in 2008.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.freeplane.features.mindmapmode.text;

import java.awt.Component;
import java.awt.event.ActionEvent;
import org.freeplane.core.controller.Controller;
import org.freeplane.core.frame.ViewController;
import org.freeplane.core.ui.AFreeplaneAction;
import org.freeplane.features.common.map.NodeModel;

class EditDetailsAction extends AFreeplaneAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditDetailsAction() {
		super("EditDetailsAction");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * freeplane.controller.actions.ActorXml#act(freeplane.controller.actions.
	 * generated.instance.XmlAction)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		final Controller controller = Controller.getCurrentController();
		final NodeModel nodeModel = controller.getSelection().getSelected();
		final ViewController viewController = controller.getViewController();
		final Component node = viewController.getComponent(nodeModel);
		node.requestFocus();
		final MTextController textController = (MTextController) MTextController.getController();
		textController.editDetails(nodeModel, null, true);
	}
}
