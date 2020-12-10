package Visualization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class ButtonPanel extends JPanel implements ActionListener {

    private MainPanel parent;
    private JButton start;
    private JButton stop;
    private JButton nextStep;
    private boolean isStarted = true;



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
        this.start.addActionListener(this);
        this.stop.addActionListener(this);
        this.nextStep.addActionListener(this);
        add(this.start);
        add(this.stop);
        add(this.nextStep);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if (source == this.start && !this.isStarted){
            this.parent.timer.start();
            this.isStarted = true;
        }

        if (source == this.stop){
            this.parent.timer.stop();
            this.isStarted = false;
        }

        if (source == this.nextStep && !this.isStarted){
            this.parent.mainLoop();
        }
    }

}
