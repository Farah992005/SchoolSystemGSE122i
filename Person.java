/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolsystemgse122i;

/**
 *
 * @author farah
 */
public abstract class Person {
    protected String name;

    public abstract void addRecord();
    public abstract void modifyRecord();
    public abstract void searchRecord();
    public abstract void deleteRecord();
    public abstract String getName();
}