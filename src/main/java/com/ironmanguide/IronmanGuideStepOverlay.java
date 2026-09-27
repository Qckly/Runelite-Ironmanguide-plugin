package com.ironmanguide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.LineComponent;

public class IronmanGuideStepOverlay extends OverlayPanel
{
private static final Color DESCRIPTION_COLOR =
new Color(190, 190, 190);

private static final Color HAVE_COLOR =
new Color(80, 200, 120);

private static final Color MISSING_COLOR =
new Color(220, 80, 80);

private final GuideManager guideManager;
private final IronmanGuideItemChecker itemChecker;
private final IronmanGuideItemNameResolver itemNameResolver;
private final GuideRuleEvaluator ruleEvaluator;

public IronmanGuideStepOverlay(
GuideManager guideManager,
IronmanGuideItemChecker itemChecker,
IronmanGuideItemNameResolver itemNameResolver)
{
this(
guideManager,
itemChecker,
itemNameResolver,
null
);
}

public IronmanGuideStepOverlay(
GuideManager guideManager,
IronmanGuideItemChecker itemChecker,
IronmanGuideItemNameResolver itemNameResolver,
GuideRuleEvaluator ruleEvaluator)
{
this.guideManager = guideManager;
this.itemChecker = itemChecker;
this.itemNameResolver = itemNameResolver;
this.ruleEvaluator = ruleEvaluator;

setLayer(OverlayLayer.UNDER_WIDGETS);
setPriority(PRIORITY_HIGHEST);
}

@Override
public Dimension render(Graphics2D graphics)
{
if (guideManager == null)
{
return null;
}

GuideStep step =
guideManager.getCurrentStep();

if (step == null)
{
return null;
}

panelComponent.getChildren().add(
LineComponent.builder()
.left(
"Ironman Guide  •  "
+ (guideManager.getCurrentStepIndex() + 1)
+ "/"
+ guideManager.getTotalSteps()
)
.build()
);

panelComponent.getChildren().add(
LineComponent.builder()
.left(step.getTitle())
.build()
);

panelComponent.getChildren().add(
LineComponent.builder()
.left(step.getDescription())
.leftColor(DESCRIPTION_COLOR)
.build()
);

addRules(step);
addItemRequirements(step);

return super.render(graphics);
}

private void addRules(GuideStep step)
{
if (ruleEvaluator == null
|| itemNameResolver == null)
{
return;
}

for (GuideRule rule : step.getRules())
{
if (rule.getItemId() <= 0)
{
continue;
}

boolean complete =
ruleEvaluator.isRuleComplete(rule);

String action;

switch (rule.getType())
{
case SELL:
action = "Sell";
break;

case BUY:
action = "Buy";
break;

case HAVE_ITEM:
action = "Have";
break;

default:
continue;
}

panelComponent.getChildren().add(
LineComponent.builder()
.left(
(complete ? "✓ " : "○ ")
+ action
+ " "
+ itemNameResolver.getName(
rule.getItemId()
)
)
.right(
String.valueOf(
rule.getQuantity()
)
)
.leftColor(
complete
? HAVE_COLOR
: MISSING_COLOR
)
.rightColor(
complete
? HAVE_COLOR
: MISSING_COLOR
)
.build()
);
}
}

private void addItemRequirements(GuideStep step)
{
if (itemChecker == null
|| itemNameResolver == null)
{
return;
}

if (step instanceof ItemGuideStep)
{
ItemGuideStep itemStep =
(ItemGuideStep) step;

addItem(
itemStep.getItemId(),
itemStep.getQuantity()
);

return;
}

if (step instanceof QuestGuideStep)
{
GuideItemRequirement[] requirements =
((QuestGuideStep) step)
.getItemRequirements();

if (requirements == null)
{
return;
}

for (GuideItemRequirement requirement :
requirements)
{
addItem(
requirement.getItemId(),
requirement.getQuantity()
);
}
}
}

private void addItem(
int itemId,
int requiredQuantity)
{
int currentQuantity =
itemChecker.getInventoryQuantity(itemId);

boolean complete =
currentQuantity >= requiredQuantity;

panelComponent.getChildren().add(
LineComponent.builder()
.left(
itemNameResolver.getName(itemId)
)
.right(
currentQuantity
+ "/"
+ requiredQuantity
)
.leftColor(
complete
? HAVE_COLOR
: MISSING_COLOR
)
.rightColor(
complete
? HAVE_COLOR
: MISSING_COLOR
)
.build()
);
}
}