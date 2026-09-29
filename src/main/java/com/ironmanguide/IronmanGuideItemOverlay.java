package com.ironmanguide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.runelite.api.Client;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.api.widgets.WidgetUtil;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

public class IronmanGuideItemOverlay
extends WidgetItemOverlay
{
private final Client client;
private final GuideManager guideManager;
private final IronmanGuideConfig config;
private final GuideRuleEvaluator ruleEvaluator;
private final ItemManager itemManager;

public IronmanGuideItemOverlay(
Client client,
GuideManager guideManager,
IronmanGuideConfig config,
GuideRuleEvaluator ruleEvaluator,
ItemManager itemManager)
{
this.client = client;
this.guideManager = guideManager;
this.config = config;
this.ruleEvaluator = ruleEvaluator;
this.itemManager = itemManager;

showOnInventory();

showOnInterfaces(
InterfaceID.SHOPMAIN
);

showOnBank();
}

@Override
public void renderItemOverlay(
Graphics2D graphics,
int itemId,
WidgetItem widgetItem)
{
GuideStep step =
guideManager.getCurrentStep();

if (step == null)
{
return;
}

Widget widget =
widgetItem.getWidget();

if (widget == null)
{
return;
}

int interfaceId =
WidgetUtil.componentToInterface(
widget.getId()
);

if (!shouldHighlightRuleItem(
step,
itemId,
interfaceId
))
{
return;
}

Rectangle bounds =
widgetItem.getCanvasBounds();

if (bounds == null)
{
return;
}

Color color =
config.highlightColor();

if (config.itemOutlineOnly())
{
BufferedImage outline =
itemManager.getItemOutline(
itemId,
widgetItem.getQuantity(),
color
);

graphics.drawImage(
outline,
(int) bounds.getX(),
(int) bounds.getY(),
null
);

return;
}

int alpha =
(int) Math.round(
255.0
* config.highlightFillOpacity()
/ 100.0
);

graphics.setColor(
new Color(
color.getRed(),
color.getGreen(),
color.getBlue(),
alpha
)
);

graphics.fill(bounds);

graphics.setColor(color);

graphics.setStroke(
new BasicStroke(
config.highlightOutlineWidth()
)
);

graphics.draw(bounds);

graphics.setStroke(
new BasicStroke(1)
);
}

private boolean shouldHighlightRuleItem(
GuideStep step,
int itemId,
int interfaceId)
{
if (ruleEvaluator == null)
{
return false;
}


/*
 * Inventory-empty guidance.
 *
 * Hidden rules are completion-only and must never
 * produce inventory/bank/shop highlighting.
 */
for (GuideRule rule :
step.getRules())
{
if (rule.isHidden())
{
continue;
}

if (rule.getType()
== GuideRuleType.INVENTORY_EMPTY
&& !ruleEvaluator.isRuleComplete(rule))
{
return interfaceId
== InterfaceID.INVENTORY
|| interfaceId
== InterfaceID.BANKSIDE
|| interfaceId
== InterfaceID.BANK_DEPOSITBOX;
}
}


/*
 * Item-on-item guidance.
 */
for (GuideRule rule :
step.getRules())
{
if (rule.isHidden())
{
continue;
}

if (rule.getType()
!= GuideRuleType.ITEM_ON_ITEM)
{
continue;
}

if (interfaceId
!= InterfaceID.INVENTORY)
{
continue;
}

int sourceItemId =
rule.getItemId();

int targetItemId =
rule.getSecondaryItemId();

if (itemId != sourceItemId
&& itemId != targetItemId)
{
continue;
}

Widget selectedWidget =
client.getSelectedWidget();

int selectedItemId =
selectedWidget != null
? selectedWidget.getItemId()
: -1;

if (selectedItemId
== sourceItemId)
{
return itemId
== targetItemId;
}

if (selectedItemId
== targetItemId)
{
return itemId
== sourceItemId;
}

return true;
}


/*
 * Generic item rules.
 */
for (GuideRule rule :
step.getRules())
{
if (rule.isHidden())
{
continue;
}

if (rule.getItemId()
!= itemId)
{
continue;
}

if (ruleEvaluator.isRuleComplete(rule))
{
continue;
}

switch (rule.getType())
{
case SELL:
return interfaceId
== InterfaceID.SHOPSIDE;

case BUY:
return interfaceId
== InterfaceID.SHOPMAIN;

case DROP:
return interfaceId
== InterfaceID.INVENTORY;

case HAVE_ITEM:
return interfaceId
== InterfaceID.INVENTORY
|| interfaceId
== InterfaceID.SHOPSIDE
|| interfaceId
== InterfaceID.BANKMAIN;

case BANK_HAS_ITEM:
return interfaceId
== InterfaceID.BANKMAIN
|| interfaceId
== InterfaceID.BANKSIDE
|| interfaceId
== InterfaceID.BANK_DEPOSITBOX;

case BANK_DEPOSIT:
return interfaceId
== InterfaceID.BANKSIDE
|| interfaceId
== InterfaceID.BANK_DEPOSITBOX;

case BANK_WITHDRAW:
return interfaceId
== InterfaceID.BANKMAIN;

case EQUIP:
return interfaceId
== InterfaceID.INVENTORY;

default:
break;
}
}

return false;
}
}