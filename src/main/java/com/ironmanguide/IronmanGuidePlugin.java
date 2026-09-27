package com.ironmanguide;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.GameTick;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.widgets.Widget;
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
import net.runelite.client.util.Text;

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
	private IronmanGuideStepCompletionChecker completionChecker;
	private IronmanGuideItemNameResolver itemNameResolver;
	private IronmanGuideDialogueHighlighter dialogueHighlighter;
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
		completionChecker = new IronmanGuideStepCompletionChecker(client, itemChecker);
		itemNameResolver = new IronmanGuideItemNameResolver(client);
		dialogueHighlighter = new IronmanGuideDialogueHighlighter(client, guideManager, config);

		worldMapGuideManager = new IronmanGuideWorldMapManager(
guideManager,
config,
worldMapPointManager
);

worldMapGuideManager.update();

panel = new IronmanGuidePanel(
		guideManager,
		itemChecker,
		itemNameResolver,
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
public void onGameTick(GameTick event)
{
GuideStep currentStep = guideManager.getCurrentStep();

	itemChecker.update(client.getItemContainer(InventoryID.INV));

boolean itemNameChanged =
itemNameResolver.update(currentStep);

dialogueHighlighter.update();

if ((currentStep instanceof LocationGuideStep || currentStep instanceof ItemGuideStep)
&& completionChecker.isComplete(currentStep))
{
guideManager.next();

configManager.setConfiguration(
"ironmanguide",
"currentStep",
guideManager.getCurrentStepIndex()
);

worldMapGuideManager.update();

if (panel != null)
{
panel.refresh();
}

return;
}

if (itemNameChanged && panel != null)
{
panel.refresh();
}
}

@Subscribe
public void onMenuOptionClicked(MenuOptionClicked event)
{
GuideStep step = guideManager.getCurrentStep();

String[] options;

if (step instanceof DialogueGuideStep)
{
options = ((DialogueGuideStep) step).getOptions();
}
else if (step instanceof QuestGuideStep)
{
if (!completionChecker.isComplete(step))
{
return;
}

options = ((QuestGuideStep) step).getDialogueOptions();
}
else
{
return;
}

Widget widget = event.getWidget();

if (widget == null || widget.getText() == null)
{
return;
}

String clickedText = Text.removeTags(widget.getText());

for (String option : options)
{
if (clickedText.equals(option))
{
guideManager.next();

configManager.setConfiguration(
"ironmanguide",
"currentStep",
guideManager.getCurrentStepIndex()
);

worldMapGuideManager.update();

if (panel != null)
{
panel.refresh();
}

return;
}
}
}

@Subscribe
public void onItemContainerChanged(ItemContainerChanged event)
{
if (event.getContainerId() != InventoryID.INV)
{
return;
}

itemChecker.update(event.getItemContainer());

GuideStep currentStep = guideManager.getCurrentStep();

if (currentStep instanceof ItemGuideStep
&& completionChecker.isComplete(currentStep))
{
guideManager.next();

configManager.setConfiguration(
"ironmanguide",
"currentStep",
guideManager.getCurrentStepIndex()
);

worldMapGuideManager.update();
}

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