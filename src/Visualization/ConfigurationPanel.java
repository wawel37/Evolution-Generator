package Visualization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationPanel extends JPanel implements ActionListener {

    private MainPanel parent;
    private JSpinner mapWidth;
    private JSpinner mapHeight;
    private JSpinner startAnimals;
    private JSpinner startEnergy;
    private JSpinner moveEnergy;
    private JSpinner grassEnergy;
    private JSpinner jungleRatio;
    private JButton acceptConfig;
    public ConfigurationPanel(MainPanel parent){
        this.parent = parent;

        this.initializeWindow();
        this.initializeSpinners();
        this.initializeButton();
    }

    private void initializeButton(){
        this.acceptConfig = new JButton("Accept Config");
        this.acceptConfig.setSize(new Dimension(30,30));
        this.acceptConfig.addActionListener(this);
        add(this.acceptConfig);
    }

    private void initializeWindow(){
        this.setPreferredSize(new Dimension(300,300));
        setLayout(new GridLayout(8,2));
    }

    private void initializeSpinners(){
        add(new JLabel("Map Width", SwingConstants.CENTER));
        this.mapWidth = new JSpinner(new SpinnerNumberModel(30,0,900,1));
        add(this.mapWidth);

        add(new JLabel("Map Height", SwingConstants.CENTER));
        this.mapHeight = new JSpinner(new SpinnerNumberModel(30,0,900,1));
        add(this.mapHeight);

        add(new JLabel("Starting animals amount", SwingConstants.CENTER));
        this.startAnimals = new JSpinner(new SpinnerNumberModel(5,0,900*900,1));
        add(this.startAnimals);

        add(new JLabel("Starting energy amount", SwingConstants.CENTER));
        this.startEnergy = new JSpinner(new SpinnerNumberModel(50.0,0.0,9999.0,1.0));
        add(this.startEnergy);

        add(new JLabel("Move energy", SwingConstants.CENTER));
        this.moveEnergy = new JSpinner(new SpinnerNumberModel(1.0,0.0,9999.0,1.0));
        add(this.moveEnergy);

        add(new JLabel("Energy gained from grass", SwingConstants.CENTER));
        this.grassEnergy = new JSpinner(new SpinnerNumberModel(25.0,0.0,9999.0,1.0));
        add(this.grassEnergy);

        add(new JLabel("Jungle ratio", SwingConstants.CENTER));
        this.jungleRatio = new JSpinner(new SpinnerNumberModel(0.0,0.0,1.0,0.1));
        add(this.jungleRatio);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if (source == this.acceptConfig){
            System.out.println("siema");
            this.parent.setEngineValues((int)this.mapWidth.getValue(),
                    (int)this.mapHeight.getValue(),
                    (double) this.jungleRatio.getValue(),
                    (double) this.grassEnergy.getValue(),
                    (double) this.startEnergy.getValue(),
                    (double) this.moveEnergy.getValue(),
                    (int)this.startAnimals.getValue());
        }
    }
}
