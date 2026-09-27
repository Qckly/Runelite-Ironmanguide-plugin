package com.ironmanguide;

import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.client.config.ConfigManager;

public class GuideStateTracker
{
private static final String CONFIG_GROUP = "ironmanguide";

private final IronmanGuideItemChecker itemChecker;
private final GuideManager guideManager;
private final ConfigManager configManager;

private PendingItemAction pendingItemAction;

public GuideStateTracker(
IronmanGuideItemChecker itemChecker,
GuideManager guideManager,
ConfigManager configManager)
{
this.itemChecker = itemChecker;
this.guideManager = guideManager;
this.configManager = configManager;
}

public void onMenuOptionClicked(
MenuOptionClicked event)
{
String option = event.getMenuOption();

if (option == null)
{
return;
}

int interfaceId =
WidgetUtil.componentToInterface(
event.getParam1()
);

int itemId = event.getItemId();

if (itemId <= 0)
{
Widget widget = event.getWidget();

if (widget != null)
{
itemId = widget.getItemId();
}
}

if (itemId <= 0)
{
return;
}

if (interfaceId == InterfaceID.SHOPSIDE
&& option.startsWith("Sell"))
{
beginItemAction(
GuideRuleType.SELL,
itemId
);

return;
}

if (interfaceId == InterfaceID.SHOPMAIN
&& option.startsWith("Buy"))
{
beginItemAction(
GuideRuleType.BUY,
itemId
);
}
}

public void onInventoryUpdated()
{
if (pendingItemAction == null)
{
return;
}

int afterQuantity =
itemChecker.getInventoryQuantity(
pendingItemAction.itemId
);

int changedQuantity = 0;

if (pendingItemAction.type
== GuideRuleType.SELL)
{
changedQuantity =
pendingItemAction.beforeQuantity
- afterQuantity;
}
else if (pendingItemAction.type
== GuideRuleType.BUY)
{
changedQuantity =
afterQuantity
- pendingItemAction.beforeQuantity;
}

if (changedQuantity > 0)
{
String key = key(
pendingItemAction.stepIndex,
pendingItemAction.type,
pendingItemAction.itemId
);

int previous =
getStoredQuantity(key);

configManager.setConfiguration(
CONFIG_GROUP,
key,
previous + changedQuantity
);
}

pendingItemAction = null;
}

public int getConfirmedQuantity(
GuideRuleType type,
int itemId)
{
String key = key(
guideManager.getCurrentStepIndex(),
type,
itemId
);

return getStoredQuantity(key);
}

private void beginItemAction(
GuideRuleType type,
int itemId)
{
pendingItemAction =
new PendingItemAction(
guideManager.getCurrentStepIndex(),
type,
itemId,
itemChecker.getInventoryQuantity(
itemId
)
);
}

private int getStoredQuantity(String key)
{
String value =
configManager.getConfiguration(
CONFIG_GROUP,
key
);

if (value == null)
{
return 0;
}

try
{
return Integer.parseInt(value);
}
catch (NumberFormatException ignored)
{
return 0;
}
}

private String key(
int stepIndex,
GuideRuleType type,
int itemId)
{
return "progress."
+ stepIndex
+ "."
+ type.name()
+ "."
+ itemId;
}

private static class PendingItemAction
{
private final int stepIndex;
private final GuideRuleType type;
private final int itemId;
private final int beforeQuantity;

private PendingItemAction(
int stepIndex,
GuideRuleType type,
int itemId,
int beforeQuantity)
{
this.stepIndex = stepIndex;
this.type = type;
this.itemId = itemId;
this.beforeQuantity =
beforeQuantity;
}
}
}