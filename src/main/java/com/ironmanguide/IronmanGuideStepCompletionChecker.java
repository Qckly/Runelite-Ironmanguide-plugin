package com.ironmanguide;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;

public class IronmanGuideStepCompletionChecker
{
private final Client client;
private final IronmanGuideItemChecker itemChecker;

public IronmanGuideStepCompletionChecker(
Client client,
IronmanGuideItemChecker itemChecker)
{
this.client = client;
this.itemChecker = itemChecker;
}

public boolean isComplete(GuideStep step)
{
if (step instanceof LocationGuideStep)
{
LocationGuideStep locationStep =
(LocationGuideStep) step;

Player player = client.getLocalPlayer();
WorldPoint target = locationStep.getWorldPoint();

if (player == null || target == null)
{
return false;
}

WorldPoint current = player.getWorldLocation();

return current.getPlane() == target.getPlane()
&& current.distanceTo2D(target) == 0;
}

if (step instanceof ItemGuideStep)
{
ItemGuideStep itemStep = (ItemGuideStep) step;

return itemChecker.hasRequiredQuantity(
itemStep.getItemId(),
itemStep.getQuantity()
);
}

return false;
}
}