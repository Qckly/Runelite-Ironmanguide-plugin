package com.ironmanguide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetModelType;
import net.runelite.api.widgets.WidgetUtil;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class IronmanGuideProductionOverlay
extends Overlay
{
private static final int MAKE_X_INTERFACE = 270;

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

drawAfterInterface(
MAKE_X_INTERFACE
);
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

Widget[] roots =
client.getWidgetRoots();

if (roots == null)
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

for (Widget root : roots)
{
highlightWidgetTree(
graphics,
root,
rule.getItemId()
);
}
}

return null;
}

private void highlightWidgetTree(
Graphics2D graphics,
Widget widget,
int targetItemId)
{
if (widget == null)
{
return;
}

int interfaceId =
WidgetUtil.componentToInterface(
widget.getId()
);

if (interfaceId == MAKE_X_INTERFACE
&& matchesItem(
widget,
targetItemId
))
{
drawHighlight(
graphics,
getHighlightBounds(widget)
);
}

Widget[] children =
widget.getChildren();

if (children != null)
{
for (Widget child : children)
{
highlightWidgetTree(
graphics,
child,
targetItemId
);
}
}

Widget[] dynamicChildren =
widget.getDynamicChildren();

if (dynamicChildren != null)
{
for (Widget child : dynamicChildren)
{
highlightWidgetTree(
graphics,
child,
targetItemId
);
}
}

Widget[] staticChildren =
widget.getStaticChildren();

if (staticChildren != null)
{
for (Widget child : staticChildren)
{
highlightWidgetTree(
graphics,
child,
targetItemId
);
}
}
}

private boolean matchesItem(
Widget widget,
int targetItemId)
{
if (widget.getItemId()
== targetItemId)
{
return true;
}

return widget.getModelType()
== WidgetModelType.ITEM
&& widget.getModelId()
== targetItemId;
}

private Rectangle getHighlightBounds(
Widget widget)
{
Rectangle best =
widget.getBounds();

Widget parent =
widget.getParent();

for (int i = 0;
i < 2 && parent != null;
i++)
{
Rectangle bounds =
parent.getBounds();

if (bounds != null
&& bounds.width >= 50
&& bounds.height >= 40
&& bounds.width <= 220
&& bounds.height <= 170)
{
best = bounds;
}

parent =
parent.getParent();
}

return best;
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