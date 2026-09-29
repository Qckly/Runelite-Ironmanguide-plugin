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

private final Map<Integer, Integer> inventory =
new HashMap<>();

private final Map<Integer, Integer> bank =
new HashMap<>();

public IronmanGuideItemChecker(Client client)
{
this.client = client;
}

public void refresh()
{
update(
client.getItemContainer(InventoryID.INV)
);

ItemContainer bankContainer =
client.getItemContainer(InventoryID.BANK);

if (bankContainer != null)
{
updateBank(bankContainer);
}
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

public void updateBank(ItemContainer container)
{
if (container == null)
{
return;
}

bank.clear();

for (Item item : container.getItems())
{
if (item.getId() <= 0)
{
continue;
}

bank.merge(
item.getId(),
item.getQuantity(),
Integer::sum
);
}
}

public void clearBank()
{
bank.clear();
}

public int getInventoryQuantity(int itemId)
{
return inventory.getOrDefault(
itemId,
0
);
}

public int getBankQuantity(int itemId)
{
return bank.getOrDefault(
itemId,
0
);
}

public int getOwnedQuantity(int itemId)
{
return getInventoryQuantity(itemId)
+ getBankQuantity(itemId);
}

public boolean hasRequiredQuantity(
int itemId,
int requiredQuantity)
{
return getInventoryQuantity(itemId)
>= requiredQuantity;
}

public boolean hasBankRequiredQuantity(
int itemId,
int requiredQuantity)
{
return getBankQuantity(itemId)
>= requiredQuantity;
}

public boolean hasOwnedQuantity(
int itemId,
int requiredQuantity)
{
return getOwnedQuantity(itemId)
>= requiredQuantity;
}

public boolean isInventoryEmpty()
{
return inventory.isEmpty();
}
}