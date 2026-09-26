package com.ironmanguide;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import net.runelite.client.ui.PluginPanel;

public class IronmanGuidePanel extends PluginPanel
{
public IronmanGuidePanel()
{
setLayout(new BorderLayout());

JLabel title = new JLabel("Ironman Guide", SwingConstants.CENTER);
add(title, BorderLayout.NORTH);
}
}