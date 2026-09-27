package com.ironmanguide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

public class IronmanGuideItemOverlay extends WidgetItemOverlay
{
private final GuideManager guideManager;
private final IronmanGuideConfig config;
private final GuideRuleEvaluator ruleEvaluator;

public IronmanGuideItemOverlay(
GuideManager guideManager,
IronmanGuideConfig config,
GuideRuleEvaluator ruleEvaluator)
{
this.guideManager = guideManager;
this.config = config;
this.ruleEvaluator = ruleEvaluator;

showOnInventory();
showOnInterfaces(InterfaceID.SHOPMAIN);
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

Widget widget = widgetItem.getWidget();

if (widget == null)
{
return;
}

int interfaceId =
WidgetUtil.componentToInterface(
widget.getId()
);

boolean shouldHighlight =
shouldHighlightRuleItem(
step,
itemId,
interfaceId
);

if (!shouldHighlight)
{
shouldHighlight =
shouldHighlightLegacyItem(
step,
itemId
);
}

if (!shouldHighlight)
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

for (GuideRule rule : step.getRules())
{
if (rule.getItemId() != itemId)
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
return interfaceId == InterfaceID.SHOPSIDE;

case BUY:
return interfaceId == InterfaceID.SHOPMAIN;

case DROP:
return interfaceId == InterfaceID.INVENTORY;

case HAVE_ITEM:
return interfaceId == InterfaceID.INVENTORY
|| interfaceId == InterfaceID.SHOPSIDE;

default:
break;
}
}

return false;
}

private boolean shouldHighlightLegacyItem(
GuideStep step,
int itemId)
{
if (step instanceof ItemGuideStep)
{
return itemId
== ((ItemGuideStep) step)
.getItemId();
}

if (step instanceof QuestGuideStep)
{
GuideItemRequirement[] requirements =
((QuestGuideStep) step)
.getItemRequirements();

if (requirements != null)
{
for (GuideItemRequirement requirement :
requirements)
{
if (itemId
== requirement.getItemId())
{
return true;
}
}
}
}

return false;
}
}