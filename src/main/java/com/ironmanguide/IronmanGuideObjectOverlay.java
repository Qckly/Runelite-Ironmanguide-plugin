package com.ironmanguide;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

public class IronmanGuideObjectOverlay extends Overlay
{
private final Client client;
private final GuideManager guideManager;
private final ModelOutlineRenderer modelOutlineRenderer;
private final IronmanGuideConfig config;

public IronmanGuideObjectOverlay(
Client client,
GuideManager guideManager,
ModelOutlineRenderer modelOutlineRenderer,
IronmanGuideConfig config)
{
this.client = client;
this.guideManager = guideManager;
this.modelOutlineRenderer = modelOutlineRenderer;
this.config = config;
}

@Override
public Dimension render(Graphics2D graphics)
{
GuideStep step = guideManager.getCurrentStep();

if (!(step instanceof ObjectGuideStep))
{
return null;
}

Player player = client.getLocalPlayer();

if (player == null)
{
return null;
}

ObjectGuideStep objectStep = (ObjectGuideStep) step;
List<TileObject> matches = new ArrayList<>();

for (Tile[] row : client.getScene().getTiles()[client.getPlane()])
{
for (Tile tile : row)
{
if (tile == null)
continue;

for (TileObject object : tile.getGameObjects())
addIfMatch(matches, object, objectStep);

addIfMatch(matches, tile.getWallObject(), objectStep);
addIfMatch(matches, tile.getDecorativeObject(), objectStep);
addIfMatch(matches, tile.getGroundObject(), objectStep);
}
}

TileObject closest = null;
int closestDistance = Integer.MAX_VALUE;

WorldPoint reference = objectStep.getWorldPoint() != null
? objectStep.getWorldPoint()
: player.getWorldLocation();

for (TileObject object : matches)
{
int distance = object.getWorldLocation().distanceTo2D(reference);

if (distance < closestDistance)
{
closestDistance = distance;
closest = object;
}
}

if (closest != null)
{
modelOutlineRenderer.drawOutline(
closest,
2,
config.highlightColor(),
4
);
}

return null;
}

private void addIfMatch(
List<TileObject> matches,
TileObject object,
ObjectGuideStep step)
{
if (object != null && object.getId() == step.getObjectId())
{
matches.add(object);
}
}
}