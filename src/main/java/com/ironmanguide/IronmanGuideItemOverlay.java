package com.ironmanguide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

public class IronmanGuideItemOverlay extends WidgetItemOverlay
{
private final GuideManager guideManager;
private final IronmanGuideConfig config;

public IronmanGuideItemOverlay(
GuideManager guideManager,
IronmanGuideConfig config)
{
this.guideManager = guideManager;
this.config = config;

showOnInventory();
}

@Override
public void renderItemOverlay(
Graphics2D graphics,
int itemId,
WidgetItem widgetItem)
{
GuideStep step = guideManager.getCurrentStep();

if (!(step instanceof ItemGuideStep))
{
return;
}

ItemGuideStep itemStep = (ItemGuideStep) step;

if (itemId != itemStep.getItemId())
{
return;
}

Rectangle bounds = widgetItem.getCanvasBounds();

if (bounds == null)
{
return;
}

Color color = config.highlightColor();

graphics.setColor(new Color(
color.getRed(),
color.getGreen(),
color.getBlue(),
45
));

graphics.fill(bounds);

graphics.setColor(color);
graphics.setStroke(new BasicStroke(2));
graphics.draw(bounds);

graphics.setStroke(new BasicStroke(1));
}
}