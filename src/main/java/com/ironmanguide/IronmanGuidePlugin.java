package com.ironmanguide;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(
name = "Ironman Guide"
)
public class IronmanGuidePlugin extends Plugin
{
@Inject
private ClientToolbar clientToolbar;

private IronmanGuidePanel panel;
private NavigationButton navButton;

@Override
protected void startUp()
{
panel = new IronmanGuidePanel();

BufferedImage icon = ImageUtil.loadImageResource(getClass(), "icon.png");

navButton = NavigationButton.builder()
.tooltip("Ironman Guide")
.icon(icon)
.panel(panel)
.build();

clientToolbar.addNavigation(navButton);
}

@Override
protected void shutDown()
{
clientToolbar.removeNavigation(navButton);
}

@Provides
IronmanGuideConfig provideConfig(ConfigManager configManager)
{
return configManager.getConfig(IronmanGuideConfig.class);
}
}