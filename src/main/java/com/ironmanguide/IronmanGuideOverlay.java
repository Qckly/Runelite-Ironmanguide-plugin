package com.ironmanguide;

import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

public class IronmanGuideOverlay extends Overlay
{
private final Client client;
private final GuideManager guideManager;
private final ModelOutlineRenderer modelOutlineRenderer;

public IronmanGuideOverlay(
Client client,
GuideManager guideManager,
ModelOutlineRenderer modelOutlineRenderer)
{
this.client = client;
this.guideManager = guideManager;
this.modelOutlineRenderer = modelOutlineRenderer;
}

@Override
public Dimension render(Graphics2D graphics)
{
GuideStep step = guideManager.getCurrentStep();

if (!(step instanceof NpcGuideStep))
{
return null;
}

NpcGuideStep npcStep = (NpcGuideStep) step;

for (NPC npc : client.getNpcs())
{
if (npc.getId() == npcStep.getNpcId())
{
modelOutlineRenderer.drawOutline(
npc,
2,
java.awt.Color.YELLOW,
4
);

break;
}
}

return null;
}
}