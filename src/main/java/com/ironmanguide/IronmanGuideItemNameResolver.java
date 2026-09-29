package com.ironmanguide;

import java.util.HashMap;
import java.util.Map;
import net.runelite.api.Client;

public class IronmanGuideItemNameResolver
{
private final Client client;
private final Map<Integer, String> names =
new HashMap<>();

public IronmanGuideItemNameResolver(Client client)
{
this.client = client;
}

public boolean update(GuideStep step)
{
boolean changed = false;

if (step == null)
{
return false;
}

GuideRule[] rules = step.getRules();

if (rules != null)
{
for (GuideRule rule : rules)
{
if (rule.getItemId() > 0)
{
changed |= resolve(
rule.getItemId()
);
}
}
}

GuideResourceRequirement[] exactResources =
step.getExactResources();

if (exactResources != null)
{
for (GuideResourceRequirement requirement :
exactResources)
{
changed |= resolve(
requirement.getItemId()
);
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