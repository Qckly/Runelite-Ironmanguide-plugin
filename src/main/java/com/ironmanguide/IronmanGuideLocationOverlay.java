package com.ironmanguide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

public class IronmanGuideLocationOverlay extends Overlay
{
private final Client client;
private final GuideManager guideManager;
private final IronmanGuideConfig config;

public IronmanGuideLocationOverlay(
Client client,
GuideManager guideManager,
IronmanGuideConfig config)
{
this.client = client;
this.guideManager = guideManager;
this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
}

@Override
public Dimension render(Graphics2D graphics)
{
GuideStep step = guideManager.getCurrentStep();

if (!(step instanceof LocationGuideStep))
{
return null;
}

LocationGuideStep locationStep = (LocationGuideStep) step;
WorldPoint worldPoint = locationStep.getWorldPoint();

if (worldPoint == null)
{
return null;
}

LocalPoint localPoint = LocalPoint.fromWorld(client, worldPoint);

if (localPoint == null)
{
return null;
}

Polygon polygon = Perspective.getCanvasTilePoly(client, localPoint);

if (polygon == null)
{
return null;
}

Color color = config.highlightColor();

int alpha =
(int) Math.round(
255.0
* config.highlightFillOpacity()
/ 100.0
);

Color fill = new Color(
color.getRed(),
color.getGreen(),
color.getBlue(),
alpha
);

OverlayUtil.renderPolygon(
graphics,
polygon,
color,
fill,
new java.awt.BasicStroke(
config.highlightOutlineWidth()
)
);

return null;
}
}