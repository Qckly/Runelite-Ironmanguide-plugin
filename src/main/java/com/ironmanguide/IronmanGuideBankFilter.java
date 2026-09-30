package com.ironmanguide;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import net.runelite.api.Client;
import net.runelite.api.ScriptEvent;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.SpriteID;
import net.runelite.api.widgets.JavaScriptCallback;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetType;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.plugins.banktags.BankTagsService;
import net.runelite.client.plugins.banktags.TagManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IronmanGuideBankFilter
{
private static final Logger log =
LoggerFactory.getLogger(
IronmanGuideBankFilter.class
);

private static final String TAG =
"ironman-guide-current";

private static final int BUTTON_SIZE = 25;

/*
 * Quest Helper uses the button immediately to the right.
 * Keep both integrations usable at the same time.
 */
private static final int BUTTON_X = 376;
private static final int BUTTON_Y = 5;

private final Client client;
private final ClientThread clientThread;
private final GuideManager guideManager;
private final GuideRuleEvaluator ruleEvaluator;
private final IronmanGuideItemChecker itemChecker;
private final TagManager tagManager;
private final BankTagsService bankTagsService;

private volatile Set<Integer> currentItemIds =
Collections.emptySet();

private Widget buttonBackground;
private Widget buttonIcon;

public IronmanGuideBankFilter(
Client client,
ClientThread clientThread,
GuideManager guideManager,
GuideRuleEvaluator ruleEvaluator,
IronmanGuideItemChecker itemChecker,
TagManager tagManager,
BankTagsService bankTagsService)
{
this.client = client;
this.clientThread = clientThread;
this.guideManager = guideManager;
this.ruleEvaluator = ruleEvaluator;
this.itemChecker = itemChecker;
this.tagManager = tagManager;
this.bankTagsService = bankTagsService;
}

public void startUp()
{
clientThread.invokeLater(() ->
{
try
{
tagManager.unregisterTag(TAG);

tagManager.registerTag(
TAG,
itemId ->
currentItemIds.contains(itemId)
);

syncNow();
}
catch (Exception e)
{
log.warn(
"Could not initialize Ironman Guide bank filter",
e
);
}
});
}

public void shutDown()
{
clientThread.invokeLater(() ->
{
try
{
if (TAG.equals(
bankTagsService.getActiveTag()
))
{
bankTagsService.closeBankTag();
}

tagManager.unregisterTag(TAG);
}
catch (Exception e)
{
log.warn(
"Could not clean up Ironman Guide bank filter",
e
);
}

currentItemIds =
Collections.emptySet();

hideButton();
});
}

public void onBankOpened()
{
hideButton();
createButton();

syncNow();
updateButtonState();
}

public void onGameTick()
{
syncNow();
updateButtonState();
}

private void syncNow()
{
GuideStep step =
guideManager != null
? guideManager.getCurrentStep()
: null;

Set<Integer> nextItems =
Collections.unmodifiableSet(
collectBankItems(step)
);

if (nextItems.equals(
currentItemIds
))
{
return;
}

currentItemIds =
nextItems;

try
{
if (TAG.equals(
bankTagsService.getActiveTag()
))
{
if (currentItemIds.isEmpty())
{
bankTagsService.closeBankTag();
}
else
{
openFilter();
}
}

updateButtonState();
}
catch (Exception e)
{
log.warn(
"Could not refresh Ironman Guide bank filter",
e
);
}
}

private Set<Integer> collectBankItems(
GuideStep step)
{
Set<Integer> itemIds =
new LinkedHashSet<>();

if (step == null)
{
return itemIds;
}

for (GuideRule rule :
step.getRules())
{
if (rule == null
|| rule.isHidden()
|| ruleEvaluator.isRuleComplete(rule))
{
continue;
}

switch (rule.getType())
{
/*
 * Needs to be physically in inventory.
 *
 * includeBank() is deliberately excluded:
 * if bank ownership counts for the guide rule,
 * withdrawing it is not required.
 */
case HAVE_ITEM:
if (!rule.isBankIncluded())
{
addItem(
itemIds,
rule.getItemId()
);
}
break;

/*
 * Explicit withdrawal semantics.
 */
case ALL_OWNED_IN_INVENTORY:
case BANK_WITHDRAW:
addItem(
itemIds,
rule.getItemId()
);
break;

/*
 * Action needs an item.
 * Only show it in the bank if inventory does not
 * already cover the remaining action quantity.
 */
case SELL:
case DROP:
{
int remaining =
Math.max(
0,
rule.getQuantity()
- ruleEvaluator.getRuleProgressQuantity(
rule
)
);

if (itemChecker.getInventoryQuantity(
rule.getItemId()
) < remaining)
{
addItem(
itemIds,
rule.getItemId()
);
}

break;
}

/*
 * Already carrying it?
 * No reason to keep showing the bank copy.
 */
case EQUIP:
if (itemChecker.getInventoryQuantity(
rule.getItemId()
) < rule.getQuantity())
{
addItem(
itemIds,
rule.getItemId()
);
}
break;

/*
 * Missing tool/materials for use-X-on-Y instructions.
 *
 * guidanceOnly() does NOT remove bank guidance.
 */
case ITEM_ON_ITEM:
if (itemChecker.getInventoryQuantity(
rule.getItemId()
) == 0)
{
addItem(
itemIds,
rule.getItemId()
);
}

if (itemChecker.getInventoryQuantity(
rule.getSecondaryItemId()
) == 0)
{
addItem(
itemIds,
rule.getSecondaryItemId()
);
}
break;

default:
break;
}
}

/*
 * Dynamic exact resources are part of the same generic system.
 *
 * Examples:
 * Logs for remaining Arrow shafts,
 * Logs/Oaks for a target Firemaking level, etc.
 */
for (GuideResourceRequirement resource :
step.getExactResources())
{
if (resource == null)
{
continue;
}

int itemId =
resource.getItemId();

int required =
resource.resolveQuantity(
client,
itemChecker
);

if (required >
itemChecker.getInventoryQuantity(
itemId
))
{
addItem(
itemIds,
itemId
);
}
}

return itemIds;
}

private static void addItem(
Set<Integer> itemIds,
int itemId)
{
if (itemId > 0)
{
itemIds.add(itemId);
}
}

private void createButton()
{
Widget parent =
client.getWidget(
InterfaceID.Bankmain.UNIVERSE
);

if (parent == null
|| parent.isHidden())
{
return;
}

buttonBackground =
createGraphic(
parent,
"ironman-guide",
SpriteID.Miscgraphics3.UNKNOWN_BUTTON_SQUARE_SMALL,
BUTTON_SIZE,
BUTTON_SIZE,
BUTTON_X,
BUTTON_Y
);

buttonBackground.setAction(
1,
"Filter Ironman Guide items"
);

buttonBackground.setOnOpListener(
(JavaScriptCallback)
this::handleButtonOp
);

buttonIcon =
createGraphic(
parent,
"",
SpriteID.GeSmallicons.SEARCH,
BUTTON_SIZE - 6,
BUTTON_SIZE - 6,
BUTTON_X + 3,
BUTTON_Y + 3
);
}

private void handleButtonOp(
ScriptEvent event)
{
if (event.getOp() != 2)
{
return;
}

try
{
if (TAG.equals(
bankTagsService.getActiveTag()
))
{
bankTagsService.closeBankTag();
}
else if (!currentItemIds.isEmpty())
{
openFilter();
}

updateButtonState();
}
catch (Exception e)
{
log.warn(
"Could not toggle Ironman Guide bank filter",
e
);
}
}

private void openFilter()
{
bankTagsService.openBankTag(
TAG,
BankTagsService.OPTION_HIDE_TAG_NAME
| BankTagsService.OPTION_NO_LAYOUT
);
}

private void updateButtonState()
{
if (buttonBackground == null)
{
return;
}

boolean active =
TAG.equals(
bankTagsService.getActiveTag()
);

buttonBackground.setSpriteId(
active
? SpriteID.Miscgraphics3.UNKNOWN_BUTTON_SQUARE_SMALL_SELECTED
: SpriteID.Miscgraphics3.UNKNOWN_BUTTON_SQUARE_SMALL
);

buttonBackground.revalidate();
}

private Widget createGraphic(
Widget parent,
String name,
int spriteId,
int width,
int height,
int x,
int y)
{
Widget widget =
parent.createChild(
-1,
WidgetType.GRAPHIC
);

widget.setOriginalWidth(width);
widget.setOriginalHeight(height);
widget.setOriginalX(x);
widget.setOriginalY(y);

widget.setSpriteId(spriteId);
widget.setHasListener(true);
widget.setName(name);
widget.revalidate();

return widget;
}

private void hideButton()
{
if (buttonBackground != null)
{
buttonBackground.setHidden(true);
buttonBackground = null;
}

if (buttonIcon != null)
{
buttonIcon.setHidden(true);
buttonIcon = null;
}
}
}