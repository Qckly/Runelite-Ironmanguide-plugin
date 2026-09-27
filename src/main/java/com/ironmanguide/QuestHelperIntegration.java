package com.ironmanguide;

import net.runelite.client.config.ConfigManager;

public class QuestHelperIntegration
{
private static final String QUEST_HELPER_GROUP = "questhelper";
private static final String AUTO_START_KEY = "autostartQuests";

private final ConfigManager configManager;

private String previousAutoStart;
private boolean changedByUs;
private Boolean managedAutoStart;

public QuestHelperIntegration(ConfigManager configManager)
{
this.configManager = configManager;
}

public void applyForStep(GuideStep step)
{
if (!(step instanceof QuestGuideStep))
{
enableAutoStart();
return;
}

QuestRouteType type =
((QuestGuideStep) step).getRouteType();

if (type == QuestRouteType.FULL
|| type == QuestRouteType.FINISH)
{
enableAutoStart();
}
else
{
disableAutoStart();
}
}

public void enableAutoStart()
{
setAutoStart(true);
}

public void disableAutoStart()
{
setAutoStart(false);
}

private void setAutoStart(boolean enabled)
{
if (managedAutoStart != null
&& managedAutoStart == enabled)
{
return;
}

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
enabled
);

managedAutoStart = enabled;
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
managedAutoStart = null;
}
}