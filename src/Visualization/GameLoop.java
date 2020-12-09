package Visualization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop implements ActionListener {
    private VisualizatingPanel panel;

    public GameLoop(VisualizatingPanel panel){
        this.panel=panel;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.loop();
    }
}