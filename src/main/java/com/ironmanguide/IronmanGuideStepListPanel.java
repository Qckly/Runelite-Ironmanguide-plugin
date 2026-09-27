package com.ironmanguide;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class IronmanGuideStepListPanel extends JPanel
{
private final GuideManager guideManager;

public IronmanGuideStepListPanel(
GuideManager guideManager)
{
this.guideManager = guideManager;

setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
setOpaque(false);

refresh();
}

public void refresh()
{
removeAll();

for (int i = 0; i < guideManager.getTotalSteps(); i++)
{
GuideStep step = guideManager.getStep(i);

JPanel row = new JPanel(new BorderLayout());
row.setOpaque(false);
row.setBorder(new EmptyBorder(6, 4, 6, 4));

JLabel label = new JLabel();

if (guideManager.isStepCompleted(i))
{
label.setText(
"✓ " + (i + 1) + ". " + step.getTitle()
);

label.setForeground(
new Color(210, 70, 70)
);
}
else if (i == guideManager.getCurrentStepIndex())
{
label.setText(
"▶ " + (i + 1) + ". " + step.getTitle()
);

label.setForeground(
new Color(255, 190, 60)
);
}
else
{
label.setText(
"○ " + (i + 1) + ". " + step.getTitle()
);
}

row.add(label, BorderLayout.CENTER);
add(row);
}

revalidate();
repaint();
}
}