package com.ironmanguide;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.function.IntConsumer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

public class IronmanGuidePanel extends PluginPanel
{
private final GuideManager guideManager;
private final IntConsumer onStepChanged;

private final JLabel progressLabel =
new JLabel();

private final IronmanGuideStepListPanel stepListPanel;

public IronmanGuidePanel(
GuideManager guideManager,
IntConsumer onStepChanged)
{
super(false);

this.guideManager =
guideManager;

this.onStepChanged =
onStepChanged;

setLayout(
new BorderLayout(
0,
8
)
);

setBackground(
ColorScheme.DARK_GRAY_COLOR
);

setBorder(
new EmptyBorder(
6,
6,
6,
6
)
);

progressLabel.setBorder(
new EmptyBorder(
2,
4,
4,
4
)
);

add(
progressLabel,
BorderLayout.NORTH
);

stepListPanel =
new IronmanGuideStepListPanel(
guideManager
);

JScrollPane routeScroll =
new JScrollPane(
stepListPanel
);

routeScroll.setBorder(null);
routeScroll.setOpaque(false);

routeScroll.getViewport()
.setOpaque(false);

routeScroll.setVerticalScrollBarPolicy(
ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
);

routeScroll.setHorizontalScrollBarPolicy(
ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
);

routeScroll.getVerticalScrollBar()
.setUnitIncrement(16);

add(
routeScroll,
BorderLayout.CENTER
);

JPanel buttons =
new JPanel(
new FlowLayout(
FlowLayout.CENTER,
6,
0
)
);

buttons.setOpaque(false);

JButton previous =
new JButton("Previous");

JButton next =
new JButton("Next");

previous.addActionListener(e ->
{
guideManager.previous();

onStepChanged.accept(
guideManager.getCurrentStepIndex()
);

refresh();
});

next.addActionListener(e ->
{
guideManager.next();

onStepChanged.accept(
guideManager.getCurrentStepIndex()
);

refresh();
});

buttons.add(previous);
buttons.add(next);

add(
buttons,
BorderLayout.SOUTH
);

refresh();
}

public void refresh()
{
progressLabel.setText(
"<html><b>Ironman Guide</b>"
+ " &nbsp; "
+ "Step "
+ (guideManager.getCurrentStepIndex() + 1)
+ " / "
+ guideManager.getTotalSteps()
+ "</html>"
);

stepListPanel.refresh();
}
}