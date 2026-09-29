package com.ironmanguide;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import net.runelite.client.ui.overlay.worldmap.WorldMapPointManager;
import net.runelite.client.ui.overlay.worldmap.WorldMapOverlay;
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
private ClientThread clientThread;

@Inject
private ClientToolbar clientToolbar;

@Inject
private OverlayManager overlayManager;

@Inject
private ModelOutlineRenderer modelOutlineRenderer;

@Inject
private ItemManager itemManager;

@Inject
private WorldMapPointManager worldMapPointManager;

@Inject
private WorldMapOverlay worldMapOverlay;

@Inject
private ConfigManager configManager;

	@Inject
	private PluginManager pluginManager;

@Inject
private IronmanGuideConfig config;


private GuideManager guideManager;
	private IronmanGuideItemChecker itemChecker;
	private GuideStateTracker guideStateTracker;
	private GuideRuleEvaluator guideRuleEvaluator;
	private IronmanGuideItemNameResolver itemNameResolver;
	private IronmanGuideDialogueHighlighter dialogueHighlighter;
private IronmanGuideWorldMapManager worldMapGuideManager;
private QuestHelperIntegration questHelperIntegration;
	private QuestHelperBridge questHelperBridge;
private IronmanGuideQuestStateChecker questStateChecker;

private IronmanGuidePanel panel;
private IronmanGuideOverlay overlay;
private IronmanGuideStepOverlay stepOverlay;
private IronmanGuideObjectOverlay objectOverlay;
private IronmanGuideLocationOverlay locationOverlay;
private IronmanGuideMinimapOverlay minimapOverlay;
private IronmanGuideWorldMapAreaOverlay worldMapAreaOverlay;
private IronmanGuideGroundItemOverlay groundItemOverlay;
	private IronmanGuideItemOverlay itemOverlay;
private IronmanGuideProductionOverlay productionOverlay;
private NavigationButton navButton;

@Override
protected void startUp()
{guideManager = new GuideManager(
GuideData.getSteps(),
config.currentStepId(),
config.currentStep()
);

saveCurrentStepProgress();

questHelperIntegration = new QuestHelperIntegration(configManager);
		questHelperBridge = new QuestHelperBridge(pluginManager);
questStateChecker = new IronmanGuideQuestStateChecker(client);

if (config.questHelperIntegration())
{
questHelperIntegration.disableAutoStart();
}

itemChecker = new IronmanGuideItemChecker(
client,
configManager
);
		guideStateTracker = new GuideStateTracker(
client,
itemChecker,
guideManager,
configManager
);

guideRuleEvaluator = new GuideRuleEvaluator(
client,
itemChecker,
guideStateTracker,
questStateChecker
);
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
{saveCurrentStepProgress();

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

worldMapAreaOverlay = new IronmanGuideWorldMapAreaOverlay(
client,
guideManager,
config,
worldMapOverlay
);
overlayManager.add(worldMapAreaOverlay);

groundItemOverlay = new IronmanGuideGroundItemOverlay(
client,
guideManager,
config,
modelOutlineRenderer
);
overlayManager.add(groundItemOverlay);

		itemOverlay = new IronmanGuideItemOverlay(
client,
guideManager,
config,
guideRuleEvaluator,
itemManager
);
		overlayManager.add(itemOverlay);

productionOverlay =
new IronmanGuideProductionOverlay(
client,
guideManager,
config,
guideRuleEvaluator
);

overlayManager.add(
productionOverlay
);

stepOverlay = new IronmanGuideStepOverlay(
client,
guideManager,
itemChecker,
itemNameResolver,
guideRuleEvaluator
);
overlayManager.add(stepOverlay);

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
if (questHelperIntegration != null)
{
questHelperIntegration.restore();
}
if (worldMapGuideManager != null)
{
worldMapGuideManager.remove();
}

overlayManager.remove(overlay);
overlayManager.remove(objectOverlay);
overlayManager.remove(locationOverlay);
overlayManager.remove(minimapOverlay);
overlayManager.remove(worldMapAreaOverlay);
overlayManager.remove(groundItemOverlay);
		overlayManager.remove(itemOverlay);
overlayManager.remove(productionOverlay);
overlayManager.remove(stepOverlay);

clientToolbar.removeNavigation(navButton);
}

@Subscribe
public void onWidgetLoaded(WidgetLoaded event)
{if (event.getGroupId() != InterfaceID.CHATMENU)
{
return;
}

clientThread.invokeLater(() ->
{
if (dialogueHighlighter != null)
{
dialogueHighlighter.update();
}
});
}

@Subscribe
public void onGameTick(GameTick event)
{

GuideStep currentStep = guideManager.getCurrentStep();

syncQuestHelper(currentStep);

	itemChecker.update(client.getItemContainer(InventoryID.INV));

boolean itemNameChanged =
itemNameResolver.update(currentStep);

if (advanceCurrentStepIfComplete())
{
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
guideStateTracker.onMenuOptionClicked(event);
}

@Subscribe
public void onItemContainerChanged(ItemContainerChanged event)
{
int containerId =
event.getContainerId();

if (containerId == InventoryID.INV)
{
itemChecker.update(
event.getItemContainer()
);

guideStateTracker.onInventoryUpdated();
}
else if (containerId == InventoryID.BANK)
{
itemChecker.updateBank(
event.getItemContainer()
);
}
else
{
return;
}

GuideStep currentStep =
guideManager.getCurrentStep();

if (advanceCurrentStepIfComplete())
{
return;
}

syncQuestHelper(currentStep);

if (panel != null)
{
panel.refresh();
}
}

private void syncQuestHelper(
GuideStep currentStep)
{
if (!config.questHelperIntegration())
{
questHelperBridge.stopManagedQuest();
return;
}
questHelperIntegration.disableAutoStart();

if (currentStep != null
&& currentStep.getQuestHelperQuestName() != null
&& !currentStep.getQuestHelperQuestName().isEmpty())
{
questHelperBridge.startQuest(
currentStep.getQuestHelperQuestName()
);
return;
}

questHelperBridge.stopManagedQuest();
}

@Subscribe
public void onConfigChanged(ConfigChanged event)
{
if (!"ironmanguide".equals(event.getGroup()))
{
return;
}

if ("questHelperIntegration".equals(event.getKey()))
{
boolean enabled = Boolean.parseBoolean(event.getNewValue());

if (enabled)
{
questHelperIntegration.disableAutoStart();
}
else
{
questHelperIntegration.restore();
}

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

private boolean advanceCurrentStepIfComplete()
{
if (guideManager == null
|| guideRuleEvaluator == null)
{
return false;
}

GuideStep currentStep =
guideManager.getCurrentStep();

if (currentStep == null
|| !guideRuleEvaluator.isStepComplete(currentStep))
{
return false;
}


/*
 * Important:
 *
 * GuideManager.next() returns false at the final step.
 * In that case we do NOT save progress, refresh UI,
 * restart Quest Helper, or update map state every tick.
 */
if (!guideManager.next())
{
return false;
}

GuideStep nextStep =
guideManager.getCurrentStep();

saveCurrentStepProgress();

syncQuestHelper(nextStep);

if (worldMapGuideManager != null)
{
worldMapGuideManager.update();
}

if (panel != null)
{
panel.refresh();
}

return true;
}
private void saveCurrentStepProgress()
{
if (guideManager == null)
{
return;
}

String stepId =
guideManager.getCurrentStepId();

if (stepId != null
&& !stepId.isBlank())
{
configManager.setConfiguration(
"ironmanguide",
"currentStepId",
stepId
);
}

configManager.setConfiguration(
"ironmanguide",
"currentStep",
guideManager.getCurrentStepIndex()
);
}
@Provides
IronmanGuideConfig provideConfig(ConfigManager configManager)
{
return configManager.getConfig(IronmanGuideConfig.class);
}

}