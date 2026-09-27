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
	private IronmanGuideStepCompletionChecker completionChecker;
	private IronmanGuideItemNameResolver itemNameResolver;
	private IronmanGuideDialogueHighlighter dialogueHighlighter;
private IronmanGuideWorldMapManager worldMapGuideManager;
private QuestHelperIntegration questHelperIntegration;
	private QuestHelperBridge questHelperBridge;
private IronmanGuideQuestStateChecker questStateChecker;
private boolean productionWidgetDiagnosticDumped;

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
{
guideManager = new GuideManager(
GuideData.getSteps(),
config.currentStep()
);

questHelperIntegration = new QuestHelperIntegration(configManager);
		questHelperBridge = new QuestHelperBridge(pluginManager);
questStateChecker = new IronmanGuideQuestStateChecker(client);

if (config.questHelperIntegration())
{
questHelperIntegration.disableAutoStart();
}

itemChecker = new IronmanGuideItemChecker(client);
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
{
if (event.getGroupId() != InterfaceID.CHATMENU)
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
debugProductionWidgetsFromGameTick();

GuideStep currentStep = guideManager.getCurrentStep();

syncQuestHelper(currentStep);

	itemChecker.update(client.getItemContainer(InventoryID.INV));

boolean itemNameChanged =
itemNameResolver.update(currentStep);

if (guideRuleEvaluator.isStepComplete(currentStep))
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

if (currentStep instanceof QuestGuideStep)
{
QuestGuideStep questStep = (QuestGuideStep) currentStep;

boolean complete = false;

if (questStep.getRouteType() == QuestRouteType.FULL
|| questStep.getRouteType() == QuestRouteType.FINISH)
{
complete =
questStateChecker.isFinished(
questStep.getQuestName()
);
}
else if (questStep.getRouteType() == QuestRouteType.START)
{
complete =
questStateChecker.isStarted(
questStep.getQuestName()
);
}
else if (questStep.getRouteType() == QuestRouteType.CONTINUE
|| questStep.getRouteType() == QuestRouteType.UNTIL)
{
complete =
questStateChecker.meetsProgress(
questStep.getProgressRequirement()
);
}

if (complete)
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

if (itemNameChanged && panel != null)
{
panel.refresh();
}
}

@Subscribe
public void onMenuOptionClicked(MenuOptionClicked event)
{
guideStateTracker.onMenuOptionClicked(event);

GuideStep step = guideManager.getCurrentStep();

String[] options;

if (step instanceof DialogueGuideStep)
{
options = ((DialogueGuideStep) step).getOptions();
}
else if (step instanceof QuestGuideStep)
{
return;
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
guideStateTracker.onInventoryUpdated();

GuideStep currentStep = guideManager.getCurrentStep();

if (guideRuleEvaluator.isStepComplete(currentStep))
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

syncQuestHelper(currentStep);

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

private void syncQuestHelper(
GuideStep currentStep)
{
if (!config.questHelperIntegration())
{
questHelperBridge.stopManagedQuest();
return;
}

questHelperIntegration.applyForStep(
currentStep
);

if (currentStep != null
&& currentStep.getQuestHelperQuestName() != null
&& !currentStep.getQuestHelperQuestName().isEmpty())
{
questHelperBridge.startQuest(
currentStep.getQuestHelperQuestName()
);
return;
}

if (currentStep instanceof QuestGuideStep)
{
QuestGuideStep questStep =
(QuestGuideStep) currentStep;

QuestRouteType routeType =
questStep.getRouteType();

if (routeType == QuestRouteType.FULL
|| routeType == QuestRouteType.FINISH)
{
questHelperBridge.startQuest(
questStep.getQuestName()
);
return;
}
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

@Provides
IronmanGuideConfig provideConfig(ConfigManager configManager)
{
return configManager.getConfig(IronmanGuideConfig.class);
}

private void debugProductionWidgetsFromGameTick()
{
Widget title =
findProductionTitle(
client.getWidgetRoots()
);

if (title == null)
{
productionWidgetDiagnosticDumped = false;
return;
}

if (productionWidgetDiagnosticDumped)
{
return;
}

productionWidgetDiagnosticDumped = true;

int groupId =
title.getId() >>> 16;

System.out.println(
"IRONMAN_GUIDE_PRODUCTION_INTERFACE="
+ groupId
);

dumpProductionWidgetGroup(
client.getWidgetRoots(),
groupId
);
}

private Widget findProductionTitle(
Widget[] widgets)
{
if (widgets == null)
{
return null;
}

for (Widget widget : widgets)
{
if (widget == null)
{
continue;
}

String widgetText =
widget.getText();

if (widgetText != null
&& widgetText.contains(
"What would you like to make?"
))
{
return widget;
}

Widget found =
findProductionTitle(
widget.getChildren()
);

if (found != null)
{
return found;
}
}

return null;
}

private void dumpProductionWidgetGroup(
Widget[] widgets,
int groupId)
{
if (widgets == null)
{
return;
}

for (Widget widget : widgets)
{
if (widget == null)
{
continue;
}

int widgetGroupId =
widget.getId() >>> 16;

if (widgetGroupId == groupId)
{
System.out.println(
"IRONMAN_WIDGET"
+ " id=" + widget.getId()
+ " type=" + widget.getType()
+ " itemId=" + widget.getItemId()
+ " itemQty=" + widget.getItemQuantity()
+ " modelType=" + widget.getModelType()
+ " modelId=" + widget.getModelId()
+ " text=[" + widget.getText() + "]"
+ " name=[" + widget.getName() + "]"
+ " bounds=[" + widget.getBounds() + "]"
);
}

dumpProductionWidgetGroup(
widget.getChildren(),
groupId
);
}
}
}