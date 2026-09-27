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
GuideStep step =
guideManager.getCurrentStep();

if (step == null)
{
return null;
}

Player player =
client.getLocalPlayer();

if (player == null)
{
return null;
}

GuideTarget target =
findObjectTarget(step);

if (target != null)
{
renderUniversalTarget(
target,
player
);

return null;
}

if (step instanceof ObjectGuideStep)
{
renderLegacyTarget(
(ObjectGuideStep) step,
player
);
}

return null;
}

private void renderUniversalTarget(
GuideTarget target,
Player player)
{
List<TileObject> matches =
new ArrayList<>();

for (Tile[] row :
client.getScene()
.getTiles()[client.getPlane()])
{
for (Tile tile : row)
{
if (tile == null)
{
continue;
}

for (TileObject object :
tile.getGameObjects())
{
addIfMatch(
matches,
object,
target.getIds()
);
}

addIfMatch(
matches,
tile.getWallObject(),
target.getIds()
);

addIfMatch(
matches,
tile.getDecorativeObject(),
target.getIds()
);

addIfMatch(
matches,
tile.getGroundObject(),
target.getIds()
);
}
}

WorldPoint reference =
target.getWorldPoint() != null
? target.getWorldPoint()
: player.getWorldLocation();

TileObject closest =
findClosest(
matches,
reference
);

if (closest != null)
{
modelOutlineRenderer.drawOutline(
closest,
config.highlightOutlineWidth(),
config.highlightColor(),
config.highlightFeather()
);
}
}

private void renderLegacyTarget(
ObjectGuideStep step,
Player player)
{
List<TileObject> matches =
new ArrayList<>();

for (Tile[] row :
client.getScene()
.getTiles()[client.getPlane()])
{
for (Tile tile : row)
{
if (tile == null)
{
continue;
}

for (TileObject object :
tile.getGameObjects())
{
if (object != null
&& object.getId()
== step.getObjectId())
{
matches.add(object);
}
}

addLegacyIfMatch(
matches,
tile.getWallObject(),
step
);

addLegacyIfMatch(
matches,
tile.getDecorativeObject(),
step
);

addLegacyIfMatch(
matches,
tile.getGroundObject(),
step
);
}
}

WorldPoint reference =
step.getWorldPoint() != null
? step.getWorldPoint()
: player.getWorldLocation();

TileObject closest =
findClosest(
matches,
reference
);

if (closest != null)
{
modelOutlineRenderer.drawOutline(
closest,
config.highlightOutlineWidth(),
config.highlightColor(),
config.highlightFeather()
);
}
}

private GuideTarget findObjectTarget(
GuideStep step)
{
for (GuideTarget target :
step.getTargets())
{
if (target.getType()
== GuideTargetType.OBJECT)
{
return target;
}
}

return null;
}

private void addIfMatch(
List<TileObject> matches,
TileObject object,
int[] ids)
{
if (object == null)
{
return;
}

for (int id : ids)
{
if (object.getId() == id)
{
matches.add(object);
return;
}
}
}

private void addLegacyIfMatch(
List<TileObject> matches,
TileObject object,
ObjectGuideStep step)
{
if (object != null
&& object.getId()
== step.getObjectId())
{
matches.add(object);
}
}

private TileObject findClosest(
List<TileObject> matches,
WorldPoint reference)
{
TileObject closest = null;
int closestDistance =
Integer.MAX_VALUE;

for (TileObject object :
matches)
{
int distance =
object.getWorldLocation()
.distanceTo2D(reference);

if (distance < closestDistance)
{
closestDistance = distance;
closest = object;
}
}

return closest;
}
}