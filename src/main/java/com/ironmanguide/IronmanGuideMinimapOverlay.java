package com.ironmanguide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class IronmanGuideMinimapOverlay extends Overlay
{
private final Client client;
private final GuideManager guideManager;
private final IronmanGuideConfig config;

public IronmanGuideMinimapOverlay(
Client client,
GuideManager guideManager,
IronmanGuideConfig config)
{
this.client = client;
this.guideManager = guideManager;
this.config = config;

setPosition(OverlayPosition.DYNAMIC);
setLayer(OverlayLayer.ABOVE_WIDGETS);
}

@Override
public Dimension render(Graphics2D graphics)
{
if (!config.showMinimapArrow())
{
return null;
}

GuideStep step =
guideManager.getCurrentStep();

GuideTarget guideTarget =
findGuideTarget(step);

WorldPoint target =
guideTarget != null
? guideTarget.getWorldPoint()
: findLegacyTarget(step);

Player player =
client.getLocalPlayer();

if (target == null || player == null)
{
return null;
}

if (target.getPlane()
!= player.getWorldLocation().getPlane())
{
return null;
}

if (guideTarget != null
&& guideTarget.getRadius() > 0
&& drawAreaOnMinimap(
graphics,
target,
guideTarget.getRadius()))
{
return null;
}

LocalPoint targetLocal =
LocalPoint.fromWorld(client, target);

if (targetLocal != null)
{
Point minimapPoint =
Perspective.localToMinimap(
client,
targetLocal
);

if (minimapPoint != null)
{
Line2D.Double line =
new Line2D.Double(
minimapPoint.getX(),
minimapPoint.getY() - 18,
minimapPoint.getX(),
minimapPoint.getY() - 8
);

drawArrow(
graphics,
line,
config.highlightColor()
);

return null;
}
}

drawFarDirectionArrow(
graphics,
player.getWorldLocation(),
target,
player.getMinimapLocation()
);

return null;
}

private GuideTarget findGuideTarget(
GuideStep step)
{
if (step == null)
{
return null;
}

for (GuideTarget target :
step.getTargets())
{
if (target.getWorldPoint() != null)
{
return target;
}
}

return null;
}

private WorldPoint findLegacyTarget(
GuideStep step)
{
if (step instanceof LocationGuideStep)
{
return ((LocationGuideStep) step)
.getWorldPoint();
}

if (step instanceof DialogueGuideStep)
{
return ((DialogueGuideStep) step)
.getWorldPoint();
}

if (step instanceof QuestGuideStep)
{
return ((QuestGuideStep) step)
.getWorldPoint();
}

return null;
}

private boolean drawAreaOnMinimap(
Graphics2D graphics,
WorldPoint center,
int radius)
{
WorldPoint northWest =
new WorldPoint(
center.getX() - radius,
center.getY() + radius,
center.getPlane()
);

WorldPoint northEast =
new WorldPoint(
center.getX() + radius,
center.getY() + radius,
center.getPlane()
);

WorldPoint southEast =
new WorldPoint(
center.getX() + radius,
center.getY() - radius,
center.getPlane()
);

WorldPoint southWest =
new WorldPoint(
center.getX() - radius,
center.getY() - radius,
center.getPlane()
);

Point p1 = toMinimap(northWest);
Point p2 = toMinimap(northEast);
Point p3 = toMinimap(southEast);
Point p4 = toMinimap(southWest);

if (p1 == null
|| p2 == null
|| p3 == null
|| p4 == null)
{
return false;
}

Polygon area =
new Polygon();

area.addPoint(
p1.getX(),
p1.getY()
);

area.addPoint(
p2.getX(),
p2.getY()
);

area.addPoint(
p3.getX(),
p3.getY()
);

area.addPoint(
p4.getX(),
p4.getY()
);

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

graphics.fillPolygon(area);

graphics.setColor(color);
graphics.setStroke(
new BasicStroke(
config.highlightOutlineWidth()
)
);

graphics.drawPolygon(area);

graphics.setStroke(
new BasicStroke(1)
);

return true;
}

private Point toMinimap(
WorldPoint worldPoint)
{
LocalPoint localPoint =
LocalPoint.fromWorld(
client,
worldPoint
);

if (localPoint == null)
{
return null;
}

return Perspective.localToMinimap(
client,
localPoint
);
}

private void drawFarDirectionArrow(
Graphics2D graphics,
WorldPoint player,
WorldPoint target,
Point minimapCenter)
{
if (minimapCenter == null)
{
return;
}

double dx =
target.getX() - player.getX();

double dy =
target.getY() - player.getY();

double yaw =
(client.getCameraYawTarget() & 0x3fff)
* (Math.PI * 2.0 / 16384.0);

double rotatedX =
Math.cos(yaw) * dx
+ Math.sin(yaw) * dy;

double rotatedY =
Math.sin(yaw) * dx
- Math.cos(yaw) * dy;

double length =
Math.hypot(
rotatedX,
rotatedY
);

if (length == 0)
{
return;
}

double unitX =
rotatedX / length;

double unitY =
rotatedY / length;

double startX =
minimapCenter.getX()
+ unitX * 52;

double startY =
minimapCenter.getY()
+ unitY * 52;

double endX =
minimapCenter.getX()
+ unitX * 64;

double endY =
minimapCenter.getY()
+ unitY * 64;

drawArrow(
graphics,
new Line2D.Double(
startX,
startY,
endX,
endY
),
config.highlightColor()
);
}

private void drawArrow(
Graphics2D graphics,
Line2D.Double line,
Color color)
{
graphics.setColor(Color.BLACK);
graphics.setStroke(
new BasicStroke(6)
);
graphics.draw(line);
drawArrowHead(
graphics,
line,
8
);

graphics.setColor(color);
graphics.setStroke(
new BasicStroke(3)
);
graphics.draw(line);
drawArrowHead(
graphics,
line,
5
);

graphics.setStroke(
new BasicStroke(1)
);
}

private void drawArrowHead(
Graphics2D graphics,
Line2D.Double line,
int size)
{
Polygon arrow =
new Polygon();

arrow.addPoint(0, size);
arrow.addPoint(-size, -size);
arrow.addPoint(size, -size);

double angle =
Math.atan2(
line.y2 - line.y1,
line.x2 - line.x1
);

AffineTransform transform =
new AffineTransform();

transform.translate(
line.x2,
line.y2
);

transform.rotate(
angle - Math.PI / 2.0
);

Graphics2D copy =
(Graphics2D) graphics.create();

copy.setTransform(transform);
copy.fill(arrow);
copy.dispose();
}
}