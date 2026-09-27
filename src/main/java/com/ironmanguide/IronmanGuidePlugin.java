package com.ironmanguide;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.gameval.InventoryID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import net.runelite.client.ui.overlay.worldmap.WorldMapPointManager;
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
private WorldMapPointManager worldMapPointManager;

@Inject
private ConfigManager configManager;

@Inject
private IronmanGuideConfig config;

private GuideManager guideManager;
	private IronmanGuideItemChecker itemChecker;
private IronmanGuideWorldMapManager worldMapGuideManager;

private IronmanGuidePanel panel;
private IronmanGuideOverlay overlay;
private IronmanGuideObjectOverlay objectOverlay;
private IronmanGuideLocationOverlay locationOverlay;
private IronmanGuideMinimapOverlay minimapOverlay;
	private IronmanGuideItemOverlay itemOverlay;
private NavigationButton navButton;

@Override
protected void startUp()
{
guideManager = new GuideManager(
GuideData.getSteps(),
config.currentStep()
);

itemChecker = new IronmanGuideItemChecker(client);

		worldMapGuideManager = new IronmanGuideWorldMapManager(
guideManager,
config,
worldMapPointManager
);

worldMapGuideManager.update();

panel = new IronmanGuidePanel(
guideManager,
itemChecker,
			step ->
{
configManager.setConfiguration(
"ironmanguide",
"currentStep",
step
);

worldMapGuideManager.update();
}
);

overlay = new IronmanGuideOverlay(
client,
guideManager,
modelOutlineRenderer,
config
);
overlayManager.add(overlay);

objectOverlay = new IronmanGuideObjectOverlay(
client,
guideManager,
modelOutlineRenderer,
config
);
overlayManager.add(objectOverlay);

locationOverlay = new IronmanGuideLocationOverlay(
client,
guideManager,
config
);
overlayManager.add(locationOverlay);

minimapOverlay = new IronmanGuideMinimapOverlay(
client,
guideManager,
config
);
overlayManager.add(minimapOverlay);

		itemOverlay = new IronmanGuideItemOverlay(guideManager, config);
		overlayManager.add(itemOverlay);

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
if (worldMapGuideManager != null)
{
worldMapGuideManager.remove();
}

overlayManager.remove(overlay);
overlayManager.remove(objectOverlay);
overlayManager.remove(locationOverlay);
overlayManager.remove(minimapOverlay);
		overlayManager.remove(itemOverlay);

clientToolbar.removeNavigation(navButton);
}

@Subscribe
public void onItemContainerChanged(ItemContainerChanged event)
{
if (event.getContainerId() != InventoryID.INV)
{
return;
}

itemChecker.update(event.getItemContainer());

if (panel != null)
{
panel.refresh();
}
}
@Subscribe
public void onConfigChanged(ConfigChanged event)
{
if (!"ironmanguide".equals(event.getGroup()))
{
return;
}

if ("showWorldMapGuidance".equals(event.getKey()))
{
boolean enabled = Boolean.parseBoolean(event.getNewValue());

if (enabled)
{
worldMapGuideManager.update();
}
else
{
worldMapGuideManager.remove();
}
}
}

@Provides
IronmanGuideConfig provideConfig(ConfigManager configManager)
{
return configManager.getConfig(IronmanGuideConfig.class);
}
}