package com.ironmanguide;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import net.runelite.client.util.ImageUtil;

@PluginDescriptor(
name = "Ironman Guide"
)
public class IronmanGuidePlugin extends Plugin
{
@Inject
private Client client;

@Inject
private ClientToolbar clientToolbar;

@Inject
private OverlayManager overlayManager;

	@Inject
	private ModelOutlineRenderer modelOutlineRenderer;

@Inject
private ConfigManager configManager;

@Inject
private IronmanGuideConfig config;

private GuideManager guideManager;
private IronmanGuidePanel panel;
private IronmanGuideOverlay overlay;
	private IronmanGuideObjectOverlay objectOverlay;
	private IronmanGuideLocationOverlay locationOverlay;
	private IronmanGuideMinimapOverlay minimapOverlay;
private NavigationButton navButton;

@Override
protected void startUp()
{
guideManager = new GuideManager(
GuideData.getSteps(),
config.currentStep()
);

panel = new IronmanGuidePanel(
guideManager,
step -> configManager.setConfiguration(
"ironmanguide",
"currentStep",
step
)
);

overlay = new IronmanGuideOverlay(client, guideManager, modelOutlineRenderer, config);
overlayManager.add(overlay);

		objectOverlay = new IronmanGuideObjectOverlay(client, guideManager, modelOutlineRenderer, config);
		overlayManager.add(objectOverlay);

		locationOverlay = new IronmanGuideLocationOverlay(client, guideManager, config);
		overlayManager.add(locationOverlay);

		minimapOverlay = new IronmanGuideMinimapOverlay(client, guideManager, config);
		overlayManager.add(minimapOverlay);

BufferedImage icon =
ImageUtil.loadImageResource(getClass(), "icon.png");

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
overlayManager.remove(overlay);
		overlayManager.remove(objectOverlay);
		overlayManager.remove(locationOverlay);
		overlayManager.remove(minimapOverlay);
clientToolbar.removeNavigation(navButton);
}

@Provides
IronmanGuideConfig provideConfig(ConfigManager configManager)
{
return configManager.getConfig(IronmanGuideConfig.class);
}
}