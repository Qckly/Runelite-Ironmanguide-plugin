package com.ironmanguide;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;

public class IronmanGuideDialogueHighlighter
{
private static final int DIALOG_GROUP = 219;
private static final int DIALOG_OPTIONS_CHILD = 1;

private final Client client;
private final GuideManager guideManager;
private final IronmanGuideConfig config;

public IronmanGuideDialogueHighlighter(
Client client,
GuideManager guideManager,
IronmanGuideConfig config)
{
this.client = client;
this.guideManager = guideManager;
this.config = config;
}

public void update()
{
GuideStep step = guideManager.getCurrentStep();
String[] options;

if (step instanceof DialogueGuideStep)
{
options = ((DialogueGuideStep) step).getOptions();
}
else if (step instanceof QuestGuideStep)
{
options = ((QuestGuideStep) step).getDialogueOptions();
}
else
{
return;
}

Widget container =
client.getWidget(
DIALOG_GROUP,
DIALOG_OPTIONS_CHILD
);

if (container == null)
{
return;
}

highlight(container.getChildren(), options);
highlight(container.getNestedChildren(), options);
}

private void highlight(
Widget[] widgets,
String[] options)
{
if (widgets == null || options == null)
{
return;
}

for (Widget widget : widgets)
{
if (widget == null || widget.getText() == null)
{
continue;
}

for (String option : options)
{
if (widget.getText().equals(option))
{
widget.setTextColor(
config.highlightColor().getRGB()
);

return;
}
}
}
}
}