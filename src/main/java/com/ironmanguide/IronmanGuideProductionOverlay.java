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

private boolean diagnosticDumped;

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
Widget productionTitle =
findWidgetByText(
client.getWidgetRoots(),
"What would you like to make?"
);

if (productionTitle != null)
{
if (!diagnosticDumped)
{
diagnosticDumped = true;

int interfaceId =
WidgetUtil.componentToInterface(
productionTitle.getId()
);

System.out.println(
"IRONMAN_GUIDE_PRODUCTION_INTERFACE=" + interfaceId
);

dumpProductionInterface(
client.getWidgetRoots(),
interfaceId
);
}
}
else
{
diagnosticDumped = false;
}

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

private Widget findWidgetByText(
Widget[] widgets,
String targetText)
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

String text = widget.getText();

if (text != null
&& text.contains(targetText))
{
return widget;
}

Widget found =
findWidgetByText(
widget.getChildren(),
targetText
);

if (found != null)
{
return found;
}

found =
findWidgetByText(
widget.getDynamicChildren(),
targetText
);

if (found != null)
{
return found;
}

found =
findWidgetByText(
widget.getStaticChildren(),
targetText
);

if (found != null)
{
return found;
}
}

return null;
}

private void dumpProductionInterface(
Widget[] widgets,
int targetInterfaceId)
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

int interfaceId =
WidgetUtil.componentToInterface(
widget.getId()
);

if (interfaceId == targetInterfaceId)
{
System.out.println(
"IRONMAN_WIDGET"
+ " id=" + widget.getId()
+ " itemId=" + widget.getItemId()
+ " modelType=" + widget.getModelType()
+ " modelId=" + widget.getModelId()
+ " text=[" + widget.getText() + "]"
+ " name=[" + widget.getName() + "]"
);
}

dumpProductionInterface(
widget.getChildren(),
targetInterfaceId
);

dumpProductionInterface(
widget.getDynamicChildren(),
targetInterfaceId
);

dumpProductionInterface(
widget.getStaticChildren(),
targetInterfaceId
);
}
}
}