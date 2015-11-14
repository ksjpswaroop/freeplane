package org.freeplane.plugin.script;

import static org.freeplane.plugin.script.ScriptingMenuUtils.noScriptsAvailableMessage;
import static org.freeplane.plugin.script.ScriptingMenuUtils.scriptNameToMenuItemTitle;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.Action;

import org.freeplane.core.ui.AFreeplaneAction;
import org.freeplane.core.ui.menubuilders.generic.Entry;
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor;
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor;
import org.freeplane.core.util.ActionUtils;
import org.freeplane.core.util.TextUtils;
import org.freeplane.plugin.script.ExecuteScriptAction.ExecutionMode;
import org.freeplane.plugin.script.ScriptingConfiguration.ScriptMetaData;

public class ScriptingMenuEntryVisitor implements EntryVisitor {
	private ScriptingConfiguration configuration;
	private ExecutionModeSelector modeSelector;

	public ScriptingMenuEntryVisitor(ScriptingConfiguration configuration, ExecutionModeSelector modeSelector) {
		this.configuration = configuration;
		this.modeSelector = modeSelector;
	}

	@Override
	public void visit(Entry target) {
		if (configuration.getMenuTitleToPathMap().isEmpty()) {
			target.addChild(createNoScriptsAvailableAction());
		}
		else {
			// add entry for all scripts but disable scripts that don't support selected exec mode  
			final ExecutionMode executionMode = modeSelector.getExecutionMode();
			for (final Map.Entry<String, String> entry : configuration.getMenuTitleToPathMap().entrySet()) {
				target.addChild(createEntry(entry.getKey(), entry.getValue(), executionMode));
			}
		}
	}

	private Entry createNoScriptsAvailableAction() {
		final Entry entry = new Entry();
		entry.setName("NoScriptsAvailableAction");
		@SuppressWarnings("serial")
		final AFreeplaneAction noScriptsAvailableAction = new AFreeplaneAction("NoScriptsAvailableAction", noScriptsAvailableMessage(), null) {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		};
		new  EntryAccessor().setAction(entry, noScriptsAvailableAction);
		return entry;
	}

	private Entry createEntry(final String scriptName, final String scriptPath, ExecutionMode executionMode) {
		final ScriptMetaData metaData = configuration.getMenuTitleToMetaDataMap().get(scriptName);
		final String title = scriptNameToMenuItemTitle(scriptName);
		return createEntry(createAction(scriptName, scriptPath, executionMode, metaData, title));
	}

	private Entry createEntry(AFreeplaneAction action) {
	    final EntryAccessor entryAccessor = new EntryAccessor();
		final Entry scriptEntry = new Entry();
		entryAccessor.addChildAction(scriptEntry, action);
		entryAccessor.setIcon(scriptEntry, ActionUtils.getActionIcon(action));
		return scriptEntry;
    }

	private AFreeplaneAction createAction(final String scriptName, final String scriptPath,
                                          ExecutionMode executionMode, final ScriptMetaData metaData, final String title) {
	    AFreeplaneAction action = new ExecuteScriptAction(scriptName, title, scriptPath, executionMode,
		    metaData.cacheContent(), metaData.getPermissions());
		action.setEnabled(metaData.getExecutionModes().contains(executionMode));
		String tooltip = createTooltip(title, metaData);
		action.putValue(Action.SHORT_DESCRIPTION, tooltip);
		action.putValue(Action.LONG_DESCRIPTION, tooltip);
	    return action;
    }

	private String createTooltip(String title, ScriptMetaData metaData) {
		final StringBuffer tooltip = new StringBuffer("<html>") //
		    .append(TextUtils.format(ScriptingMenuUtils.LABEL_AVAILABLE_MODES_TOOLTIP, title)) //
		    .append("<ul>");
		for (ExecutionMode executionMode : metaData.getExecutionModes()) {
			tooltip.append("<li>");
			tooltip.append(getTitleForExecutionMode(executionMode));
			tooltip.append("</li>");
		}
		tooltip.append("</ul>");
		return tooltip.toString();
	}

    private String getTitleForExecutionMode(ExecutionMode executionMode) {
        final String scriptLabel = TextUtils.getText(ScriptingMenuUtils.LABEL_SCRIPT);
        return TextUtils.format(ScriptingConfiguration.getExecutionModeKey(executionMode), scriptLabel);
    }

	@Override
	public boolean shouldSkipChildren(Entry entry) {
		return true;
	}
}
