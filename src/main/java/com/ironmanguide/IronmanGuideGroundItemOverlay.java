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
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

public class IronmanGuideGroundItemOverlay extends Overlay
{
private final Client client;
private final GuideManager guideManager;
private final IronmanGuideConfig config;
private final ModelOutlineRenderer modelOutlineRenderer;

public IronmanGuideGroundItemOverlay(
Client client,
GuideManager guideManager,
IronmanGuideConfig config,
ModelOutlineRenderer modelOutlineRenderer)
{
this.client = client;
this.guideManager = guideManager;
this.config = config;
this.modelOutlineRenderer = modelOutlineRenderer;

setPosition(OverlayPosition.DYNAMIC);
setLayer(OverlayLayer.ABOVE_SCENE);
}

@Override
public Dimension render(
Graphics2D graphics)
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

if (!matchesLocation(
tile,
target
))
{
continue;
}

ItemLayer itemLayer =
tile.getItemLayer();

if (itemLayer == null)
{
continue;
}

if (config.itemOutlineOnly())
{
renderItemOutlines(
itemLayer,
target
);

continue;
}

if (!containsTargetItem(
itemLayer,
target
))
{
continue;
}

renderTileHighlight(
graphics,
tile
);
}
}

return null;
}

private void renderItemOutlines(
ItemLayer itemLayer,
GuideTarget target)
{
Node current =
itemLayer.getTop();

while (current instanceof TileItem)
{
TileItem item =
(TileItem) current;

if (isTargetItem(
item,
target
))
{
modelOutlineRenderer.drawOutline(
itemLayer,
item,
config.highlightOutlineWidth(),
config.highlightColor(),
config.highlightFeather()
);
}

current =
current.getNext();
}
}

private void renderTileHighlight(
Graphics2D graphics,
Tile tile)
{
Polygon polygon =
Perspective.getCanvasTilePoly(
client,
tile.getLocalLocation()
);

if (polygon == null)
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

private GuideTarget findGroundItemTarget(
GuideStep step)
{
for (GuideTarget target :
step.getTargets())
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
ItemLayer itemLayer,
GuideTarget target)
{
Node current =
itemLayer.getTop();

while (current instanceof TileItem)
{
TileItem item =
(TileItem) current;

if (isTargetItem(
item,
target
))
{
return true;
}

current =
current.getNext();
}

return false;
}

private boolean isTargetItem(
TileItem item,
GuideTarget target)
{
for (int itemId :
target.getIds())
{
if (item.getId() == itemId)
{
return true;
}
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
Math.max(
0,
target.getRadius()
);

return tilePoint.distanceTo2D(
reference
) <= radius;
}
}