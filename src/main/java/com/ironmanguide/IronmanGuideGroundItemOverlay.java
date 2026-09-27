package com.ironmanguide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import net.runelite.api.Client;
import net.runelite.api.ItemLayer;
import net.runelite.api.Node;
import net.runelite.api.Perspective;
import net.runelite.api.Tile;
import net.runelite.api.TileItem;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

public class IronmanGuideGroundItemOverlay extends Overlay
{
private final Client client;
private final GuideManager guideManager;
private final IronmanGuideConfig config;

public IronmanGuideGroundItemOverlay(
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
GuideStep step =
guideManager.getCurrentStep();

if (step == null)
{
return null;
}

GuideTarget target =
findGroundItemTarget(step);

if (target == null)
{
return null;
}

Tile[][] tiles =
client.getScene()
.getTiles()[client.getPlane()];

for (Tile[] row : tiles)
{
for (Tile tile : row)
{
if (tile == null)
{
continue;
}

if (!matchesLocation(tile, target))
{
continue;
}

if (!containsTargetItem(tile, target))
{
continue;
}

Polygon polygon =
Perspective.getCanvasTilePoly(
client,
tile.getLocalLocation()
);

if (polygon == null)
{
continue;
}

Color color =
config.highlightColor();

int alpha =
(int) Math.round(
255.0
* config.highlightFillOpacity()
/ 100.0
);

Color fill =
new Color(
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
new BasicStroke(
config.highlightOutlineWidth()
)
);
}
}

return null;
}

private GuideTarget findGroundItemTarget(
GuideStep step)
{
for (GuideTarget target : step.getTargets())
{
if (target.getType()
== GuideTargetType.GROUND_ITEM)
{
return target;
}
}

return null;
}

private boolean containsTargetItem(
Tile tile,
GuideTarget target)
{
ItemLayer itemLayer =
tile.getItemLayer();

if (itemLayer == null)
{
return false;
}

Node current =
itemLayer.getTop();

while (current instanceof TileItem)
{
TileItem item =
(TileItem) current;

for (int itemId : target.getIds())
{
if (item.getId() == itemId)
{
return true;
}
}

current =
current.getNext();
}

return false;
}

private boolean matchesLocation(
Tile tile,
GuideTarget target)
{
WorldPoint reference =
target.getWorldPoint();

if (reference == null)
{
return true;
}

WorldPoint tilePoint =
tile.getWorldLocation();

if (tilePoint == null
|| tilePoint.getPlane()
!= reference.getPlane())
{
return false;
}

int radius =
Math.max(0, target.getRadius());

return tilePoint.distanceTo2D(reference)
<= radius;
}
}