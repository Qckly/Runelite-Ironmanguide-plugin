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
GuideStep step = guideManager.getCurrentStep();

int npcId;
WorldPoint targetPoint;

if (step instanceof NpcGuideStep)
{
NpcGuideStep npcStep = (NpcGuideStep) step;
npcId = npcStep.getNpcId();
targetPoint = npcStep.getWorldPoint();
}
else if (step instanceof DialogueGuideStep)
{
DialogueGuideStep dialogueStep =
(DialogueGuideStep) step;

npcId = dialogueStep.getNpcId();
targetPoint = dialogueStep.getWorldPoint();
}
else if (step instanceof QuestGuideStep)
{
QuestGuideStep questStep =
(QuestGuideStep) step;

npcId = questStep.getNpcId();
targetPoint = questStep.getWorldPoint();
}
else
{
return null;
}

if (npcId < 0)
{
return null;
}

Player player = client.getLocalPlayer();

if (player == null)
{
return null;
}

NPC closestNpc = null;
int closestDistance = Integer.MAX_VALUE;

for (NPC npc : client.getNpcs())
{
if (npc.getId() != npcId)
{
continue;
}

WorldPoint referencePoint =
targetPoint != null
? targetPoint
: player.getWorldLocation();

int distance = npc.getWorldLocation()
.distanceTo2D(referencePoint);

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
2,
config.highlightColor(),
4
);
}

return null;
}
}