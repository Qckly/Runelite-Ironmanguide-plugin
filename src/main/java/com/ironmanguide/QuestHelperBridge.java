package com.ironmanguide;

import java.lang.reflect.Method;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginManager;

public class QuestHelperBridge
{
private static final String QUEST_HELPER_PLUGIN =
"com.questhelper.QuestHelperPlugin";

private static final String QUEST_HELPER_QUEST =
"com.questhelper.questinfo.QuestHelperQuest";

private static final long RETRY_DELAY_MS =
10_000L;

private final PluginManager pluginManager;

private String managedQuestName;

private String failedQuestName;
private long retryAfterMillis;

public QuestHelperBridge(PluginManager pluginManager)
{
this.pluginManager = pluginManager;
}

public boolean startQuest(String questName)
{
if (questName == null || questName.isEmpty())
{
return false;
}

if (questName.equalsIgnoreCase(managedQuestName))
{
return true;
}

if (isRetryBlocked(questName))
{
return false;
}

Plugin questHelperPlugin = findQuestHelper();

if (questHelperPlugin == null)
{
scheduleRetry(questName);
return false;
}

try
{
ClassLoader classLoader =
questHelperPlugin.getClass().getClassLoader();

Class<?> questClass =
classLoader.loadClass(QUEST_HELPER_QUEST);

Method getByName =
questClass.getMethod(
"getByName",
String.class
);

Object questHelper =
getByName.invoke(null, questName);

if (questHelper == null)
{
scheduleRetry(questName);
return false;
}

Object questManager =
questHelperPlugin
.getClass()
.getMethod("getQuestManager")
.invoke(questHelperPlugin);

if (questManager == null)
{
scheduleRetry(questName);
return false;
}

for (Method method :
questManager.getClass().getMethods())
{
if (!"startUpQuest".equals(method.getName()))
{
continue;
}

Class<?>[] parameters =
method.getParameterTypes();

if (parameters.length == 2
&& parameters[1] == boolean.class
&& parameters[0].isAssignableFrom(
questHelper.getClass()))
{
method.invoke(
questManager,
questHelper,
false
);

managedQuestName = questName;

clearRetry();

return true;
}
}

scheduleRetry(questName);
}
catch (ReflectiveOperationException
| RuntimeException ignored)
{
scheduleRetry(questName);
}

return false;
}

public void stopManagedQuest()
{
if (managedQuestName == null)
{
clearRetry();
return;
}

Plugin questHelperPlugin = findQuestHelper();

if (questHelperPlugin == null)
{
managedQuestName = null;
clearRetry();
return;
}

try
{
Object questManager =
questHelperPlugin
.getClass()
.getMethod("getQuestManager")
.invoke(questHelperPlugin);

if (questManager != null)
{
questManager
.getClass()
.getMethod(
"shutDownQuest",
boolean.class
)
.invoke(questManager, false);
}
}
catch (ReflectiveOperationException
| RuntimeException ignored)
{
// Quest Helper may have changed its internal API.
// Do not interfere with RuneLite.
}
finally
{
managedQuestName = null;
clearRetry();
}
}

private boolean isRetryBlocked(
String questName)
{
return failedQuestName != null
&& failedQuestName.equalsIgnoreCase(questName)
&& System.currentTimeMillis() < retryAfterMillis;
}

private void scheduleRetry(
String questName)
{
failedQuestName = questName;

retryAfterMillis =
System.currentTimeMillis()
+ RETRY_DELAY_MS;
}

private void clearRetry()
{
failedQuestName = null;
retryAfterMillis = 0L;
}

private Plugin findQuestHelper()
{
if (pluginManager == null)
{
return null;
}

for (Plugin plugin : pluginManager.getPlugins())
{
if (plugin == null)
{
continue;
}

if (QUEST_HELPER_PLUGIN.equals(
plugin.getClass().getName()))
{
if (pluginManager.isPluginEnabled(plugin))
{
return plugin;
}

return null;
}
}

return null;
}
}