package com.ironmanguide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.LineComponent;

public class IronmanGuideStepOverlay extends OverlayPanel
{
private final Client client;
private static final Color DESCRIPTION_COLOR =
new Color(190, 190, 190);

private static final Color RESOURCE_COLOR =
new Color(255, 190, 60);

private static final Color HAVE_COLOR =
new Color(80, 200, 120);

private static final Color MISSING_COLOR =
new Color(220, 80, 80);

private final GuideManager guideManager;
private final IronmanGuideItemChecker itemChecker;
private final IronmanGuideItemNameResolver itemNameResolver;
private final GuideRuleEvaluator ruleEvaluator;

public IronmanGuideStepOverlay(
Client client,
GuideManager guideManager,
IronmanGuideItemChecker itemChecker,
IronmanGuideItemNameResolver itemNameResolver)
{
this(
client,
guideManager,
itemChecker,
itemNameResolver,
null
);
}

public IronmanGuideStepOverlay(
Client client,
GuideManager guideManager,
IronmanGuideItemChecker itemChecker,
IronmanGuideItemNameResolver itemNameResolver,
GuideRuleEvaluator ruleEvaluator)
{
this.client = client;
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

addExactResources(step);
addRules(step);

return super.render(graphics);
}

private void addExactResources(GuideStep step)
{
if (itemNameResolver == null)
{
return;
}

GuideResourceRequirement[] requirements =
step.getExactResources();

if (requirements == null)
{
return;
}

for (GuideResourceRequirement requirement :
requirements)
{
int requiredQuantity =
requirement.resolveQuantity(client, itemChecker);

if (requiredQuantity <= 0)
{
continue;
}

panelComponent.getChildren().add(
LineComponent.builder()
.left(
"You will need "
+ requiredQuantity
+ " "
+ itemNameResolver.getName(
requirement.getItemId()
)
+ "."
)
.leftColor(RESOURCE_COLOR)
.build()
);

addOwnedResourceProgress(
requirement,
requiredQuantity
);
}
}

private void addOwnedResourceProgress(
GuideResourceRequirement requirement,
int requiredQuantity)
{
if (requirement == null
|| !requirement.isOutputBased()
|| itemChecker == null)
{
return;
}

int ownedQuantity =
requirement.resolveOwnedInputQuantity(
itemChecker
);

int missingQuantity =
requirement.resolveMissingInputQuantity(
client,
itemChecker
);

String progressText;

Color progressColor;

if (missingQuantity > 0)
{
progressText =
"Have: "
+ ownedQuantity
+ " • Collect: "
+ missingQuantity
+ " more";

progressColor =
RESOURCE_COLOR;
}
else
{
progressText =
"Have: "
+ ownedQuantity
+ " • Ready";

progressColor =
HAVE_COLOR;
}

panelComponent.getChildren().add(
LineComponent.builder()
.left(progressText)
.leftColor(progressColor)
.build()
);
}

private String formatRuleProgress(
GuideRule rule)
{
int target =
Math.max(
1,
rule.getQuantity()
);

int current =
Math.max(
0,
ruleEvaluator.getRuleProgressQuantity(
rule
)
);

/*
 * Do not display values such as 9/7 after an action
 * overshoots the requirement.
 */
current =
Math.min(
current,
target
);

return current
+ "/"
+ target;
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
if (rule.isHidden())
{
continue;
}

if (rule.getItemId() <= 0)
{
continue;
}

boolean complete =
ruleEvaluator.isRuleComplete(rule);

if (rule.getType()
== GuideRuleType.ITEM_ON_ITEM)
{
continue;
}

String action;

switch (rule.getType())
{
case SELL:
action = "Sell";
break;

case BUY:
action = "Buy";
break;

case DROP:
action = "Drop";
break;

case PICKUP:
action = "Pick up";
break;

case HAVE_ITEM:
action = "Have";
break;

case ALL_OWNED_IN_INVENTORY:
panelComponent.getChildren().add(
LineComponent.builder()
.left(
(complete ? "✓ " : "○ ")
+ "Withdraw all "
+ itemNameResolver.getName(
rule.getItemId()
)
)
.leftColor(
complete
? HAVE_COLOR
: MISSING_COLOR
)
.build()
);
continue;

case BANK_DEPOSIT:
action = "Deposit";
break;

case BANK_WITHDRAW:
action = "Withdraw";
break;

case EQUIP:
action = "Equip";
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
rule.getType()
== GuideRuleType.ALL_OWNED_IN_INVENTORY
? ""
: formatRuleProgress(
rule
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

}