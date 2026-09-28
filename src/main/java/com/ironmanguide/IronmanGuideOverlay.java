package com.ironmanguide;

import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

public class IronmanGuideOverlay extends Overlay
{
private final Client client;
private final GuideManager guideManager;
private final ModelOutlineRenderer modelOutlineRenderer;
private final IronmanGuideConfig config;

public IronmanGuideOverlay(
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

GuideTarget npcTarget =
findNpcTarget(step);

if (npcTarget != null)
{
highlightTarget(npcTarget);
return null;
}

highlightLegacyStep(step);

return null;
}

private GuideTarget findNpcTarget(
GuideStep step)
{
for (GuideTarget target :
step.getTargets())
{
if (target.getType()
== GuideTargetType.NPC)
{
return target;
}
}

return null;
}

private void highlightTarget(
GuideTarget target)
{
Player player =
client.getLocalPlayer();

if (player == null)
{
return;
}

NPC closestNpc = null;
int closestDistance =
Integer.MAX_VALUE;

for (NPC npc : client.getNpcs())
{
if (!contains(
target.getIds(),
npc.getId()))
{
continue;
}

WorldPoint reference =
target.getWorldPoint() != null
? target.getWorldPoint()
: player.getWorldLocation();

int distance =
npc.getWorldLocation()
.distanceTo2D(reference);

if (distance < closestDistance)
{
closestDistance = distance;
closestNpc = npc;
}
}

if (closestNpc != null)
{
modelOutlineRenderer.drawOutline(
closestNpc,
config.highlightOutlineWidth(),
config.highlightColor(),
config.highlightFeather()
);
}
}

private void highlightLegacyStep(
GuideStep step)
{
int npcId;
WorldPoint targetPoint;
if (step
instanceof DialogueGuideStep)
{
DialogueGuideStep dialogueStep =
(DialogueGuideStep) step;

npcId =
dialogueStep.getNpcId();

targetPoint =
dialogueStep.getWorldPoint();
}
else if (step
instanceof QuestGuideStep)
{
QuestGuideStep questStep =
(QuestGuideStep) step;

npcId =
questStep.getNpcId();

targetPoint =
questStep.getWorldPoint();
}
else
{
return;
}

if (npcId < 0)
{
return;
}

Player player =
client.getLocalPlayer();

if (player == null)
{
return;
}

NPC closestNpc = null;
int closestDistance =
Integer.MAX_VALUE;

for (NPC npc : client.getNpcs())
{
if (npc.getId() != npcId)
{
continue;
}

WorldPoint reference =
targetPoint != null
? targetPoint
: player.getWorldLocation();

int distance =
npc.getWorldLocation()
.distanceTo2D(reference);

if (distance < closestDistance)
{
closestDistance = distance;
closestNpc = npc;
}
}

if (closestNpc != null)
{
modelOutlineRenderer.drawOutline(
closestNpc,
config.highlightOutlineWidth(),
config.highlightColor(),
config.highlightFeather()
);
}
}

private boolean contains(
int[] ids,
int id)
{
if (ids == null)
{
return false;
}

for (int candidate : ids)
{
if (candidate == id)
{
return true;
}
}

return false;
}
}