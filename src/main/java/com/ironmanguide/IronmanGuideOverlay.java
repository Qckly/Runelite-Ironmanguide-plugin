package com.ironmanguide;

import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
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

if (!(step instanceof NpcGuideStep))
{
return null;
}

Player player = client.getLocalPlayer();

if (player == null)
{
return null;
}

NpcGuideStep npcStep = (NpcGuideStep) step;

NPC closestNpc = null;
int closestDistance = Integer.MAX_VALUE;

for (NPC npc : client.getNpcs())
{
if (npc.getId() != npcStep.getNpcId())
{
continue;
}

int distance = npc.getWorldLocation()
.distanceTo2D(player.getWorldLocation());

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