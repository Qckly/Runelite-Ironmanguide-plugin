package com.ironmanguide;

import net.runelite.api.Client;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;

public class IronmanGuideQuestStateChecker
{
private final Client client;

public IronmanGuideQuestStateChecker(Client client)
{
this.client = client;
}

public boolean isStarted(String questName)
{
QuestState state = getState(questName);

return state == QuestState.IN_PROGRESS
|| state == QuestState.FINISHED;
}

public boolean isFinished(String questName)
{
return getState(questName) == QuestState.FINISHED;
}

private QuestState getState(String questName)
{
if (questName == null || questName.isEmpty())
{
return null;
}

for (Quest quest : Quest.values())
{
if (quest.getName().equalsIgnoreCase(questName))
{
return quest.getState(client);
}
}

return null;
}
}