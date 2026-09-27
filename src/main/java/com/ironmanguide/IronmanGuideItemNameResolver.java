package com.ironmanguide;

import java.util.HashMap;
import java.util.Map;
import net.runelite.api.Client;

public class IronmanGuideItemNameResolver
{
private final Client client;
private final Map<Integer, String> names = new HashMap<>();

public IronmanGuideItemNameResolver(Client client)
{
this.client = client;
}

public boolean update(GuideStep step)
{
boolean changed = false;

if (step instanceof ItemGuideStep)
{
ItemGuideStep itemStep = (ItemGuideStep) step;
changed |= resolve(itemStep.getItemId());
}
else if (step instanceof QuestGuideStep)
{
QuestGuideStep questStep = (QuestGuideStep) step;

GuideItemRequirement[] requirements =
questStep.getItemRequirements();

if (requirements != null)
{
for (GuideItemRequirement requirement : requirements)
{
changed |= resolve(
requirement.getItemId()
);
}
}
}

return changed;
}

private boolean resolve(int itemId)
{
if (names.containsKey(itemId))
{
return false;
}

String name = client
.getItemDefinition(itemId)
.getName();

if (name == null
|| name.isEmpty()
|| "null".equalsIgnoreCase(name))
{
return false;
}

names.put(itemId, name);
return true;
}

public String getName(int itemId)
{
return names.getOrDefault(
itemId,
"Item " + itemId
);
}
}