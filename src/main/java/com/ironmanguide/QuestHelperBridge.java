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

private final PluginManager pluginManager;

private String managedQuestName;

public QuestHelperBridge(PluginManager pluginManager)
{
this.pluginManager = pluginManager;
}

public boolean isAvailable()
{
return findQuestHelper() != null;
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

Plugin questHelperPlugin = findQuestHelper();

if (questHelperPlugin == null)
{
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
return false;
}

Object questManager =
questHelperPlugin
.getClass()
.getMethod("getQuestManager")
.invoke(questHelperPlugin);

if (questManager == null)
{
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

return true;
}
}
}
catch (ReflectiveOperationException
| RuntimeException ignored)
{
return false;
}

return false;
}

public void stopManagedQuest()
{
if (managedQuestName == null)
{
return;
}

Plugin questHelperPlugin = findQuestHelper();

if (questHelperPlugin == null)
{
managedQuestName = null;
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
// Do not interfere with RuneLite if Quest Helper changed.
}
finally
{
managedQuestName = null;
}
}

public String getManagedQuestName()
{
return managedQuestName;
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