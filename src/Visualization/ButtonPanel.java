package Visualization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventListener;

public class ButtonPanel extends JPanel implements ActionListener, ItemListener {

    private MainPanel parent;
    private JButton start;
    private JButton stop;
    private JButton nextStep;
    private JCheckBox secondWindowCheckBox;
    private JCheckBox dominatingGenotype;
    public boolean isStarted = false;
    private boolean isClicked = false;



    public ButtonPanel(MainPanel parent){
        this.parent = parent;

        this.initializeWindow();
    }

    private void initializeWindow(){
        setPreferredSize(new Dimension(900, 0));
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.start = new JButton("Start");
        this.stop = new JButton("Stop");
        this.nextStep = new JButton("Next Step");
        this.secondWindowCheckBox = new JCheckBox("Second Window", false);
        this.dominatingGenotype = new JCheckBox("Show dominating genotype", false);
        this.start.addActionListener(this);
        this.stop.addActionListener(this);
        this.nextStep.addActionListener(this);
        this.secondWindowCheckBox.addItemListener(this);
        this.dominatingGenotype.addItemListener(this);
        add(this.secondWindowCheckBox);
        add(this.start);
        add(this.stop);
        add(this.nextStep);
        add(this.dominatingGenotype);

    }

    public void endGame(){
        this.isStarted = false;
    }

    public void resetCheckBox() {
        this.isClicked = false;
        this.secondWindowCheckBox.setSelected(false);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if (source == this.start && !this.isStarted){
            this.parent.timer.start();
            this.isStarted = true;
        }
        else if (source == this.stop){
            this.parent.timer.stop();
            this.isStarted = false;
        }
        else if (source == this.nextStep && !this.isStarted){
            this.parent.mainLoop();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e){
        if(e.getSource() == this.secondWindowCheckBox){
            if(e.getStateChange() == 1){
                this.isClicked = true;
                this.parent.startSecondVisualization();
            }else if(this.isClicked){
                this.isClicked = true;
                this.parent.stopSecondVisualization();
            }
        }
        else if(e.getSource() == this.dominatingGenotype){
            if(e.getStateChange() == 1){
                this.parent.showDominatingGenotype();
            }else{
                this.parent.hideDominatingGenotype();
            }
        }
    }
}
