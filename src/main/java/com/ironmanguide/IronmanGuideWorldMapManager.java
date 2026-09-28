package com.ironmanguide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import net.runelite.api.coords.WorldPoint;

import net.runelite.client.ui.overlay.worldmap.WorldMapPoint;
import net.runelite.client.ui.overlay.worldmap.WorldMapPointManager;

public class IronmanGuideWorldMapManager
{
private final GuideManager guideManager;
private final IronmanGuideConfig config;
private final WorldMapPointManager worldMapPointManager;

private WorldMapPoint currentPoint;

public IronmanGuideWorldMapManager(
GuideManager guideManager,
IronmanGuideConfig config,
WorldMapPointManager worldMapPointManager)
{
this.guideManager = guideManager;
this.config = config;
this.worldMapPointManager = worldMapPointManager;
}

public void update()
{
remove();

GuideStep step =
guideManager.getCurrentStep();

GuideTarget target =
findExactWorldTarget(step);

if (step == null
|| target == null)
{
return;
}

WorldPoint worldPoint =
target.getWorldPoint();

if (worldPoint == null)
{
return;
}

currentPoint =
new WorldMapPoint(
worldPoint,
createMarker(
config.highlightColor()
)
);

currentPoint.setSnapToEdge(true);
currentPoint.setTooltip(
step.getTitle()
);

worldMapPointManager.add(
currentPoint
);
}

public void remove()
{
if (currentPoint != null)
{
worldMapPointManager.remove(
currentPoint
);

currentPoint = null;
}
}

private GuideTarget findExactWorldTarget(
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
&& target.getRadius() == 0)
{
return target;
}
}

return null;
}

private BufferedImage createMarker(
Color color)
{
BufferedImage image =
new BufferedImage(
22,
22,
BufferedImage.TYPE_INT_ARGB
);

Graphics2D graphics =
image.createGraphics();

graphics.setRenderingHint(
RenderingHints.KEY_ANTIALIASING,
RenderingHints.VALUE_ANTIALIAS_ON
);

graphics.setColor(
Color.BLACK
);

graphics.fillOval(
1,
1,
20,
20
);

graphics.setColor(color);

graphics.fillOval(
4,
4,
14,
14
);

graphics.setColor(
Color.WHITE
);

graphics.setStroke(
new BasicStroke(2)
);

graphics.drawOval(
4,
4,
14,
14
);

graphics.dispose();

return image;
}
}