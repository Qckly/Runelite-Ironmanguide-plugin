package com.ironmanguide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.worldmap.WorldMapOverlay;

public class IronmanGuideWorldMapAreaOverlay extends Overlay
{
private final Client client;
private final GuideManager guideManager;
private final IronmanGuideConfig config;
private final WorldMapOverlay worldMapOverlay;

public IronmanGuideWorldMapAreaOverlay(
Client client,
GuideManager guideManager,
IronmanGuideConfig config,
WorldMapOverlay worldMapOverlay)
{
this.client = client;
this.guideManager = guideManager;
this.config = config;
this.worldMapOverlay = worldMapOverlay;

setPosition(OverlayPosition.DYNAMIC);
setPriority(PRIORITY_HIGHEST);
setLayer(OverlayLayer.MANUAL);

drawAfterInterface(
InterfaceID.WORLDMAP
);
}

@Override
public Dimension render(Graphics2D graphics)
{
if (!config.showWorldMapGuidance())
{
return null;
}

GuideStep step =
guideManager.getCurrentStep();

GuideTarget target =
findAreaTarget(step);

if (target == null)
{
return null;
}

Widget mapWidget =
client.getWidget(
InterfaceID.Worldmap.MAP_CONTAINER
);

if (mapWidget == null)
{
return null;
}

WorldPoint center =
target.getWorldPoint();

int radius =
target.getRadius();

Point northWest =
toMapPoint(
new WorldPoint(
center.getX() - radius,
center.getY() + radius,
0
)
);

Point northEast =
toMapPoint(
new WorldPoint(
center.getX() + radius,
center.getY() + radius,
0
)
);

Point southEast =
toMapPoint(
new WorldPoint(
center.getX() + radius,
center.getY() - radius,
0
)
);

Point southWest =
toMapPoint(
new WorldPoint(
center.getX() - radius,
center.getY() - radius,
0
)
);

if (northWest == null
|| northEast == null
|| southEast == null
|| southWest == null)
{
return null;
}

Polygon area =
new Polygon();

area.addPoint(
northWest.getX(),
northWest.getY()
);

area.addPoint(
northEast.getX(),
northEast.getY()
);

area.addPoint(
southEast.getX(),
southEast.getY()
);

area.addPoint(
southWest.getX(),
southWest.getY()
);

Color color =
config.highlightColor();

Graphics2D copy =
(Graphics2D) graphics.create();

try
{
Rectangle mapBounds =
mapWidget.getBounds();

copy.clip(mapBounds);

int alpha =
(int) Math.round(
255.0
* config.highlightFillOpacity()
/ 100.0
);

copy.setColor(
new Color(
color.getRed(),
color.getGreen(),
color.getBlue(),
alpha
)
);

copy.fillPolygon(area);

copy.setColor(color);
copy.setStroke(
new BasicStroke(
config.highlightOutlineWidth()
)
);

copy.drawPolygon(area);
}
finally
{
copy.dispose();
}

return null;
}

private GuideTarget findAreaTarget(
GuideStep step)
{
if (step == null)
{
return null;
}

for (GuideTarget target :
step.getTargets())
{
if (target.getWorldPoint() != null
&& target.getRadius() > 0)
{
return target;
}
}

return null;
}

private Point toMapPoint(
WorldPoint worldPoint)
{
return worldMapOverlay
.mapWorldPointToGraphicsPoint(
worldPoint
);
}
}