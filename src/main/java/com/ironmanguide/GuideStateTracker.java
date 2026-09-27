package com.ironmanguide;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.util.Text;

public class GuideStateTracker
{
private static final String CONFIG_GROUP =
"ironmanguide";

private final Client client;
private final IronmanGuideItemChecker itemChecker;
private final GuideManager guideManager;
private final ConfigManager configManager;

private PendingItemAction pendingItemAction;

public GuideStateTracker(
Client client,
IronmanGuideItemChecker itemChecker,
GuideManager guideManager,
ConfigManager configManager)
{
this.client = client;
this.itemChecker = itemChecker;
this.guideManager = guideManager;
this.configManager = configManager;
}

public void onMenuOptionClicked(
MenuOptionClicked event)
{
String option =
event.getMenuOption();

if (option == null)
{
return;
}

MenuAction menuAction =
event.getMenuAction();

Widget widget =
event.getWidget();

if (menuAction
== MenuAction.WIDGET_TARGET_ON_WIDGET)
{
Widget selectedWidget =
client.getSelectedWidget();

Widget targetWidget =
getTargetWidget(event);

if (selectedWidget != null
&& targetWidget != null)
{
int sourceItemId =
selectedWidget.getItemId();

int targetItemId =
targetWidget.getItemId();

if (sourceItemId > 0
&& targetItemId > 0)
{
confirmItemPairAction(
sourceItemId,
targetItemId
);

return;
}
}
}

int interfaceId =
WidgetUtil.componentToInterface(
event.getParam1()
);

if (interfaceId == InterfaceID.CHATMENU
&& widget != null
&& widget.getText() != null)
{
String text =
normalizeText(
widget.getText()
);

if (!text.isEmpty())
{
confirmTextAction(
GuideRuleType.DIALOGUE_OPTION,
text
);
}
}

if ("Take".equals(option)
&& isGroundItemAction(menuAction))
{
int groundItemId =
event.getId();

if (groundItemId > 0)
{
beginItemAction(
GuideRuleType.PICKUP,
groundItemId
);
}

return;
}

if (isNpcAction(menuAction))
{
NPC npc = null;

if (event.getMenuEntry() != null)
{
npc =
event.getMenuEntry().getNpc();
}

if (npc != null)
{
confirmAction(
GuideRuleType.NPC_INTERACT,
npc.getId()
);
}

return;
}

if (isObjectAction(menuAction))
{
int objectId =
event.getId();

if (objectId > 0)
{
confirmAction(
GuideRuleType.OBJECT_INTERACT,
objectId
);
}

return;
}

int itemId =
event.getItemId();

if (itemId <= 0
&& widget != null)
{
itemId =
widget.getItemId();
}

if (itemId <= 0)
{
return;
}

if ("Drop".equals(option))
{
beginItemAction(
GuideRuleType.DROP,
itemId
);

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

return;
}

if ((interfaceId == InterfaceID.BANKSIDE
|| interfaceId == InterfaceID.BANK_DEPOSITBOX)
&& option.startsWith("Deposit"))
{
beginItemAction(
GuideRuleType.BANK_DEPOSIT,
itemId
);

return;
}

if (interfaceId == InterfaceID.BANKMAIN
&& option.startsWith("Withdraw"))
{
beginItemAction(
GuideRuleType.BANK_WITHDRAW,
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
== GuideRuleType.SELL
|| pendingItemAction.type
== GuideRuleType.DROP
|| pendingItemAction.type
== GuideRuleType.BANK_DEPOSIT)
{
changedQuantity =
pendingItemAction.beforeQuantity
- afterQuantity;
}
else if (pendingItemAction.type
== GuideRuleType.BUY
|| pendingItemAction.type
== GuideRuleType.PICKUP
|| pendingItemAction.type
== GuideRuleType.BANK_WITHDRAW)
{
changedQuantity =
afterQuantity
- pendingItemAction.beforeQuantity;
}

if (changedQuantity > 0)
{
String key =
key(
pendingItemAction.stepId,
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
int id)
{
GuideStep step =
guideManager.getCurrentStep();

if (step == null
|| step.getId() == null
|| step.getId().isBlank())
{
return 0;
}

return getStoredQuantity(
key(
step.getId(),
type,
id
)
);
}

public boolean isTextConfirmed(
GuideRuleType type,
String text)
{
GuideStep step =
guideManager.getCurrentStep();

if (step == null
|| step.getId() == null
|| step.getId().isBlank()
|| text == null)
{
return false;
}

String value =
configManager.getConfiguration(
CONFIG_GROUP,
textKey(
step.getId(),
type,
normalizeText(text)
)
);

return "1".equals(value);
}

public boolean isItemPairConfirmed(
int firstItemId,
int secondItemId)
{
GuideStep step =
guideManager.getCurrentStep();

if (step == null
|| step.getId() == null
|| step.getId().isBlank())
{
return false;
}

String value =
configManager.getConfiguration(
CONFIG_GROUP,
pairKey(
step.getId(),
firstItemId,
secondItemId
)
);

return "1".equals(value);
}

private void confirmItemPairAction(
int firstItemId,
int secondItemId)
{
GuideStep step =
guideManager.getCurrentStep();

if (step == null
|| step.getId() == null
|| step.getId().isBlank())
{
return;
}

configManager.setConfiguration(
CONFIG_GROUP,
pairKey(
step.getId(),
firstItemId,
secondItemId
),
1
);
}

private Widget getTargetWidget(
MenuOptionClicked event)
{
Widget widget =
event.getWidget();

if (widget != null
&& widget.getItemId() > 0)
{
return widget;
}

Widget parent =
client.getWidget(
event.getParam1()
);

if (parent == null)
{
return null;
}

int childIndex =
event.getParam0();

if (childIndex < 0)
{
return parent;
}

return parent.getChild(
childIndex
);
}

private void beginItemAction(
GuideRuleType type,
int itemId)
{
GuideStep step =
guideManager.getCurrentStep();

if (step == null
|| step.getId() == null
|| step.getId().isBlank())
{
return;
}

pendingItemAction =
new PendingItemAction(
step.getId(),
type,
itemId,
itemChecker.getInventoryQuantity(
itemId
)
);
}

private void confirmAction(
GuideRuleType type,
int id)
{
GuideStep step =
guideManager.getCurrentStep();

if (step == null
|| step.getId() == null
|| step.getId().isBlank())
{
return;
}

String key =
key(
step.getId(),
type,
id
);

int previous =
getStoredQuantity(key);

configManager.setConfiguration(
CONFIG_GROUP,
key,
previous + 1
);
}

private void confirmTextAction(
GuideRuleType type,
String text)
{
GuideStep step =
guideManager.getCurrentStep();

if (step == null
|| step.getId() == null
|| step.getId().isBlank())
{
return;
}

configManager.setConfiguration(
CONFIG_GROUP,
textKey(
step.getId(),
type,
text
),
1
);
}

private boolean isGroundItemAction(
MenuAction action)
{
return action
== MenuAction.GROUND_ITEM_FIRST_OPTION
|| action
== MenuAction.GROUND_ITEM_SECOND_OPTION
|| action
== MenuAction.GROUND_ITEM_THIRD_OPTION
|| action
== MenuAction.GROUND_ITEM_FOURTH_OPTION
|| action
== MenuAction.GROUND_ITEM_FIFTH_OPTION;
}

private boolean isNpcAction(
MenuAction action)
{
if (action == null)
{
return false;
}

int id =
action.getId();

return id
>= MenuAction.NPC_FIRST_OPTION.getId()
&& id
<= MenuAction.NPC_FIFTH_OPTION.getId();
}

private boolean isObjectAction(
MenuAction action)
{
return action
== MenuAction.GAME_OBJECT_FIRST_OPTION
|| action
== MenuAction.GAME_OBJECT_SECOND_OPTION
|| action
== MenuAction.GAME_OBJECT_THIRD_OPTION
|| action
== MenuAction.GAME_OBJECT_FOURTH_OPTION
|| action
== MenuAction.GAME_OBJECT_FIFTH_OPTION;
}

private int getStoredQuantity(
String key)
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

private String normalizeText(
String text)
{
if (text == null)
{
return "";
}

return Text.removeTags(text).trim();
}

private String key(
String stepId,
GuideRuleType type,
int id)
{
return "progress."
+ stepId
+ "."
+ type.name()
+ "."
+ id;
}

private String pairKey(
String stepId,
int firstItemId,
int secondItemId)
{
int low =
Math.min(
firstItemId,
secondItemId
);

int high =
Math.max(
firstItemId,
secondItemId
);

return "progress."
+ stepId
+ "."
+ GuideRuleType.ITEM_ON_ITEM.name()
+ "."
+ low
+ "."
+ high;
}

private String textKey(
String stepId,
GuideRuleType type,
String text)
{
return "progress."
+ stepId
+ "."
+ type.name()
+ ".text."
+ Integer.toUnsignedString(
text.hashCode()
);
}

private static class PendingItemAction
{
private final String stepId;
private final GuideRuleType type;
private final int itemId;
private final int beforeQuantity;

private PendingItemAction(
String stepId,
GuideRuleType type,
int itemId,
int beforeQuantity)
{
this.stepId = stepId;
this.type = type;
this.itemId = itemId;
this.beforeQuantity =
beforeQuantity;
}
}
}