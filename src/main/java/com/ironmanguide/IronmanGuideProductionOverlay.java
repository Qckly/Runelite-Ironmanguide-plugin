package com.ironmanguide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class IronmanGuideProductionOverlay
extends Overlay
{
private static final int PRODUCTION_INTERFACE = 270;
private static final int MAX_CHILD_ID = 200;

private final Client client;
private final GuideManager guideManager;
private final IronmanGuideConfig config;
private final GuideRuleEvaluator ruleEvaluator;

public IronmanGuideProductionOverlay(
Client client,
GuideManager guideManager,
IronmanGuideConfig config,
GuideRuleEvaluator ruleEvaluator)
{
this.client = client;
this.guideManager = guideManager;
this.config = config;
this.ruleEvaluator = ruleEvaluator;

setPosition(OverlayPosition.DYNAMIC);
setLayer(OverlayLayer.ABOVE_WIDGETS);
setPriority(PRIORITY_HIGHEST);
}

@Override
public Dimension render(Graphics2D graphics)
{
GuideStep step =
guideManager.getCurrentStep();

if (step == null)
{
return null;
}

for (GuideRule rule : step.getRules())
{
if (rule.getType()
!= GuideRuleType.HAVE_ITEM)
{
continue;
}

if (rule.getItemId() <= 0)
{
continue;
}

if (ruleEvaluator != null
&& ruleEvaluator.isRuleComplete(rule))
{
continue;
}

if (highlightProductionItem(
graphics,
rule.getItemId()
))
{
break;
}
}

return null;
}

private boolean highlightProductionItem(
Graphics2D graphics,
int targetItemId)
{
for (int childId = 0;
childId < MAX_CHILD_ID;
childId++)
{
Widget widget =
client.getWidget(
PRODUCTION_INTERFACE,
childId
);

if (widget == null)
{
continue;
}

Widget matched =
findItemWidget(
widget,
targetItemId,
0
);

if (matched == null)
{
continue;
}

Rectangle bounds =
findProductionSlotBounds(
matched
);

drawHighlight(
graphics,
bounds
);

return true;
}

return false;
}

private Widget findItemWidget(
Widget widget,
int targetItemId,
int depth)
{
if (widget == null
|| depth > 10)
{
return null;
}

if (widget.getItemId()
== targetItemId)
{
return widget;
}

Widget found =
findItemWidgetArray(
widget.getChildren(),
targetItemId,
depth + 1
);

if (found != null)
{
return found;
}

found =
findItemWidgetArray(
widget.getDynamicChildren(),
targetItemId,
depth + 1
);

if (found != null)
{
return found;
}

return findItemWidgetArray(
widget.getStaticChildren(),
targetItemId,
depth + 1
);
}

private Widget findItemWidgetArray(
Widget[] widgets,
int targetItemId,
int depth)
{
if (widgets == null)
{
return null;
}

for (Widget widget : widgets)
{
Widget found =
findItemWidget(
widget,
targetItemId,
depth
);

if (found != null)
{
return found;
}
}

return null;
}

private Rectangle findProductionSlotBounds(
Widget itemWidget)
{
Rectangle itemBounds =
itemWidget.getBounds();

Widget parent =
itemWidget.getParent();

while (parent != null)
{
Rectangle bounds =
parent.getBounds();

if (bounds != null
&& bounds.width >= 80
&& bounds.width <= 120
&& bounds.height >= 60
&& bounds.height <= 90)
{
return bounds;
}

parent =
parent.getParent();
}

return itemBounds;
}

private void drawHighlight(
Graphics2D graphics,
Rectangle bounds)
{
if (bounds == null
|| bounds.width <= 0
|| bounds.height <= 0)
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
}