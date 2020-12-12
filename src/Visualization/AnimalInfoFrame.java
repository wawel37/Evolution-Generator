package Visualization;

import MapElement.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimalInfoFrame extends JFrame implements ActionListener{
    private final MainPanel parent;
    private final Animal animal;
    private JButton button;

    public AnimalInfoFrame(Animal animal, MainPanel parent){
        super("Animal id " + animal.id);
        this.parent = parent;
        this.animal = animal;
        this.initializeWindow();
    }

    private void initializeWindow(){
        setVisible(true);
        setLayout(new GridLayout(2, 1, 0, 0));
        setLocation(100,100);
        setPreferredSize(new Dimension(500, 100));
        this.addComponents();
        pack();
    }

    private void addComponents(){
        add(new JLabel("Animal's genotype: " + this.animal.genotype, SwingConstants.CENTER));
        this.button = new JButton("Watch the Animal");
        this.button.addActionListener(this);
        add(this.button);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if (source == this.button){
            new AnimalWatchFrame(this.animal, this.parent);
        }
    }
}
