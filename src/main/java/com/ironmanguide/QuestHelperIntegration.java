package com.ironmanguide;

import net.runelite.client.config.ConfigManager;

public class QuestHelperIntegration
{
private static final String QUEST_HELPER_GROUP = "questhelper";
private static final String AUTO_START_KEY = "autostartQuests";

private final ConfigManager configManager;

private String previousAutoStart;
private boolean changedByUs;

public QuestHelperIntegration(ConfigManager configManager)
{
this.configManager = configManager;
}

public void enableAutoStart()
{
if (!changedByUs)
{
previousAutoStart = configManager.getConfiguration(
QUEST_HELPER_GROUP,
AUTO_START_KEY
);

changedByUs = true;
}

configManager.setConfiguration(
QUEST_HELPER_GROUP,
AUTO_START_KEY,
true
);
}

public void restore()
{
if (!changedByUs)
{
return;
}

if (previousAutoStart == null)
{
configManager.unsetConfiguration(
QUEST_HELPER_GROUP,
AUTO_START_KEY
);
}
else
{
configManager.setConfiguration(
QUEST_HELPER_GROUP,
AUTO_START_KEY,
previousAutoStart
);
}

changedByUs = false;
previousAutoStart = null;
}
}