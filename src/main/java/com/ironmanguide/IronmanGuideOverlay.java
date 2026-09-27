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

NpcGuideStep npcStep = (NpcGuideStep) step;

for (NPC npc : client.getNpcs())
{
if (npc.getId() == npcStep.getNpcId())
{
modelOutlineRenderer.drawOutline(
npc,
2,
config.highlightColor(),
4
);

break;
}
}

return null;
}
}