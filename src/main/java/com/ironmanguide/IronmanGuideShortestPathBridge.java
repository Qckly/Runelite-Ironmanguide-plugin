package com.ironmanguide;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.events.PluginMessage;

public class IronmanGuideShortestPathBridge
{
private static final String NAMESPACE =
"shortestpath";

private final Client client;
private final GuideManager guideManager;
private final IronmanGuideConfig config;
private final EventBus eventBus;

private WorldPoint activeTarget;

public IronmanGuideShortestPathBridge(
Client client,
GuideManager guideManager,
IronmanGuideConfig config,
EventBus eventBus)
{
this.client = client;
this.guideManager = guideManager;
this.config = config;
this.eventBus = eventBus;
}

public void update()
{
if (!config.useShortestPath())
{
clear();
return;
}

GuideTarget target =
findTarget(
guideManager.getCurrentStep()
);

WorldPoint worldPoint =
target != null
? target.getWorldPoint()
: null;

if (Objects.equals(
activeTarget,
worldPoint))
{
return;
}

if (worldPoint == null)
{
clear();
return;
}

Map<String, Object> data =
new HashMap<>();

data.put(
"target",
worldPoint
);

eventBus.post(
new PluginMessage(
NAMESPACE,
"path",
data
)
);

activeTarget = worldPoint;
}

public void clear()
{
if (activeTarget == null)
{
return;
}

eventBus.post(
new PluginMessage(
NAMESPACE,
"clear"
)
);

activeTarget = null;
}

private GuideTarget findTarget(
GuideStep step)
{
if (step == null)
{
return null;
}

GuideTarget fallback = null;

for (GuideTarget target :
step.getTargets())
{
WorldPoint worldPoint =
target.getWorldPoint();

if (worldPoint == null)
{
continue;
}

if (fallback == null)
{
fallback = target;
}

if (worldPoint.getPlane()
== client.getPlane())
{
return target;
}
}

return fallback;
}
}
