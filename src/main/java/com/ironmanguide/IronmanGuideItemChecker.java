package com.ironmanguide;

import java.util.HashMap;
import java.util.Map;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.gameval.InventoryID;
import net.runelite.client.config.ConfigManager;

public class IronmanGuideItemChecker
{
private static final String CONFIG_GROUP =
"ironmanguide";

private static final String BANK_SNAPSHOT_KEY =
"bankSnapshot";

private final Client client;
private final ConfigManager configManager;

private final Map<Integer, Integer> inventory =
new HashMap<>();

private final Map<Integer, Integer> bank =
new HashMap<>();

public IronmanGuideItemChecker(
Client client,
ConfigManager configManager)
{
this.client = client;
this.configManager = configManager;

loadBankSnapshot();
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

saveBankSnapshot();
}

public void clearBank()
{
bank.clear();
saveBankSnapshot();
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

private void loadBankSnapshot()
{
String snapshot =
configManager.getConfiguration(
CONFIG_GROUP,
BANK_SNAPSHOT_KEY
);

if (snapshot == null
|| snapshot.isBlank())
{
return;
}

for (String entry : snapshot.split(";"))
{
int separator =
entry.indexOf(':');

if (separator <= 0)
{
continue;
}

try
{
int itemId =
Integer.parseInt(
entry.substring(0, separator)
);

int quantity =
Integer.parseInt(
entry.substring(separator + 1)
);

if (itemId > 0
&& quantity > 0)
{
bank.put(
itemId,
quantity
);
}
}
catch (NumberFormatException ignored)
{
}
}
}

private void saveBankSnapshot()
{
StringBuilder snapshot =
new StringBuilder();

for (Map.Entry<Integer, Integer> entry :
bank.entrySet())
{
if (snapshot.length() > 0)
{
snapshot.append(';');
}

snapshot
.append(entry.getKey())
.append(':')
.append(entry.getValue());
}

configManager.setConfiguration(
CONFIG_GROUP,
BANK_SNAPSHOT_KEY,
snapshot.toString()
);
}
}