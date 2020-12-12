package Visualization;

import MapElement.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Objects;

public class AnimalWatchFrame extends JFrame implements WindowListener{

    private final Animal animal;
    private final MainPanel parent;
    private JLabel childrenCounter;
    private JLabel descendantCounter;
    private JLabel ageCounter;
    private IAnimalWatchObserver observer;

    public AnimalWatchFrame(Animal animal, MainPanel parent){
        super("Watching animal " + animal.id);
        this.animal = animal;
        this.parent = parent;
        this.observer = parent;
        this.observer.addFrame(this);
        this.initializeWindow();
    }

    private void initializeWindow(){
        this.addWindowListener(this);
        setVisible(true);
        setLayout(new GridLayout(3, 1, 0, 0));
        setLocation(300,300);
        setPreferredSize(new Dimension(300,200));
        this.addComponents();
        pack();
    }

    private void addComponents(){
        this.childrenCounter = new JLabel("Children counter: " + this.animal.getChildrenCounter(), SwingConstants.CENTER);
        add(this.childrenCounter);

        this.descendantCounter = new JLabel("Descendants counter: " + this.animal.getDescendantCounter(), SwingConstants.CENTER);
        add(this.descendantCounter);

        this.ageCounter = new JLabel("Age: " + this.animal.getAge(), SwingConstants.CENTER);
        add(this.ageCounter);
    }

    public void updateLabels(){
        if (this.animal.isDead()){
            this.observer.deleteFrame(this);
            this.ageCounter.setText("Died at age: " + (this.animal.getAge() + 1));
            return;
        }
        this.childrenCounter.setText("Children counter: " + this.animal.getChildrenCounter());
        this.descendantCounter.setText("Descendants counter: " + this.animal.getDescendantCounter());
        this.ageCounter.setText("Age: " + this.animal.getAge());
    }

    @Override
    public int hashCode() {return Objects.hash(this.animal); }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (this.hashCode() != other.hashCode()) return false;
        if (!(other instanceof AnimalWatchFrame)) return false;
        AnimalWatchFrame otherFrame = (AnimalWatchFrame) other;
        return (this.animal.equals(otherFrame.animal));
    }


    @Override
    public void windowOpened(WindowEvent e) {
        //PASS
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.observer.deleteFrame(this);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //PASS
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //PASS
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //PASS
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //PASS
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //PASS
    }
}
