package com.ironmanguide;

import net.runelite.api.Client;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.JavaScriptCallback;
import net.runelite.api.widgets.Widget;

public class IronmanGuideDialogueHighlighter
{
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
GuideStep step =
guideManager.getCurrentStep();

String[] options =
getDialogueOptions(step);

if (options == null
|| options.length == 0)
{
return;
}

Widget container =
client.getWidget(
InterfaceID.Chatmenu.OPTIONS
);

if (container == null
|| container.isHidden())
{
return;
}

highlight(
container.getChildren(),
options
);

highlight(
container.getNestedChildren(),
options
);
}

private String[] getDialogueOptions(
GuideStep step)
{
if (step == null)
{
return null;
}

for (GuideTarget target :
step.getTargets())
{
if (target.getType()
== GuideTargetType.DIALOGUE)
{
return target.getTexts();
}
}

return null;
}

private void highlight(
Widget[] widgets,
String[] options)
{
if (widgets == null
|| options == null)
{
return;
}

for (Widget widget : widgets)
{
if (widget == null
|| widget.getText() == null)
{
continue;
}

String widgetText =
widget.getText().trim();

for (String option : options)
{
if (widgetText.equals(option))
{
int highlightColor =
config.highlightColor()
.getRGB();

widget.setTextColor(
highlightColor
);

widget.setOnMouseLeaveListener(
(JavaScriptCallback) event ->
widget.setTextColor(
highlightColor
)
);

return;
}
}
}
}
}