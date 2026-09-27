package com.ironmanguide;

import java.util.HashMap;
import java.util.Map;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.gameval.InventoryID;

public class IronmanGuideItemChecker
{
private final Client client;
private final Map<Integer, Integer> inventory = new HashMap<>();

public IronmanGuideItemChecker(Client client)
{
this.client = client;
}

public void refresh()
{
ItemContainer container =
client.getItemContainer(InventoryID.INV);

update(container);
}

public void update(ItemContainer container)
{
inventory.clear();

if (container == null)
{
return;
}

for (Item item : container.getItems())
{
if (item.getId() <= 0)
{
continue;
}

inventory.merge(
item.getId(),
item.getQuantity(),
Integer::sum
);
}
}

public int getInventoryQuantity(int itemId)
{
return inventory.getOrDefault(itemId, 0);
}

public boolean hasRequiredQuantity(
int itemId,
int requiredQuantity)
{
return getInventoryQuantity(itemId) >= requiredQuantity;
}
}