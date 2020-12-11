package Visualization;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class ConfigurationPanel extends JPanel implements ActionListener {

    static public int MAX_GAP = 2000;
    static public int MIN_GAP = 0;
    static public int INITIAL_GAP = 1000;

    private MainPanel parent;
    private JSpinner mapWidth;
    private JSpinner mapHeight;
    private JSpinner startAnimals;
    private JSpinner startEnergy;
    private JSpinner moveEnergy;
    private JSpinner grassEnergy;
    private JSpinner jungleRatio;
    private JButton acceptConfig;
    private JSlider timeGapSlider;

    public ConfigurationPanel(MainPanel parent){
        this.parent = parent;

        this.initializeWindow();
        this.initializeSpinners();
        this.initializeButton();
        this.initializeSlider();
    }

    private void initializeSlider(){
        this.timeGapSlider = new JSlider(JSlider.HORIZONTAL,MIN_GAP,MAX_GAP, INITIAL_GAP);
        this.timeGapSlider.setMajorTickSpacing(1000);
        this.timeGapSlider.setMinorTickSpacing(200);
        this.timeGapSlider.setPaintTicks(true);
        this.timeGapSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1000, new JLabel("Time Gap"));
        labelTable.put(0, new JLabel("0"));
        labelTable.put(2000, new JLabel("2000"));
        this.timeGapSlider.setLabelTable(labelTable);
        add(this.timeGapSlider);
    }

    private void initializeButton(){
        this.acceptConfig = new JButton("Accept Config");
        this.acceptConfig.addActionListener(this);
        add(this.acceptConfig);
    }

    private void initializeWindow(){
        this.setSize(new Dimension(900, 400));
        this.setPreferredSize(new Dimension(300,250));
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
        this.startAnimals = new JSpinner(new SpinnerNumberModel(50,0,900*900,1));
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
        this.jungleRatio = new JSpinner(new SpinnerNumberModel(0.5,0.0,1.0,0.1));
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
            this.parent.changeTimerGap(this.timeGapSlider.getValue());
        }
    }
}
