package org.Xivix.UI.Views.New;

import org.Xivix.UI.Elements.Label;
import org.Xivix.UI.Elements.*;
import org.Xivix.Utils.Constants;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class CreatePerson extends FormFrame {

    private JTextField firstNameInput, middleNameInput, lastNameInput, emailInput, phoneInput, jobInput;
    private JTextArea descriptionInput;
    private DatePicker bdayInput;
    private Selectable gender;
    private JTextField heightInput, weightInput, chestSizeInput, waistSizeInput, hipSizeInput, inseamLengthInput, shoeSizeInput;
    private Selectable shirtSize;


    public CreatePerson(){
        firstNameInput = new JTextField(20);
        middleNameInput = new JTextField(20);
        lastNameInput = new JTextField(20);
        emailInput = new JTextField(20);
        bdayInput = new DatePicker();
        phoneInput = new JTextField(20);
        descriptionInput = new JTextArea(5, 20);
        jobInput = new JTextField(20);
        HashMap<String, String> genders = new HashMap<>();
        genders.put("Female", "F");
        genders.put("Male", "M");
        gender = new Selectable(genders);
        addInput(new Label("First Name", Constants.colors[0]), firstNameInput);
        addInput(new Label("Middle Name", Constants.colors[1]), middleNameInput);
        addInput(new Label("Last Name", Constants.colors[2]), lastNameInput);
        addInput(new Label("Birthdate", Constants.colors[3]), bdayInput);
        addInput(new Label("Gender", Constants.colors[4]), gender);
        addInput(new Label("E-Mail", Constants.colors[5]), emailInput);
        addInput(new Label("Phone", Constants.colors[6]), phoneInput);
        addInput(new Label("Description", Constants.colors[7]), descriptionInput);
        addInput(new Label("Current Job", Constants.colors[8]), jobInput);
        heightInput = new JTextField(10);
        weightInput = new JTextField(10);
        addInput(new Label("Height (cm)", Constants.colors[9]), heightInput);
        addInput(new Label("Weight (kg)", Constants.colors[10]), weightInput);

        chestSizeInput = new JTextField(10);
        waistSizeInput = new JTextField(10);
        hipSizeInput = new JTextField(10);
        inseamLengthInput = new JTextField(10);
        shoeSizeInput = new JTextField(10);

        HashMap<String, String> shirtSizes = new HashMap<>();
        shirtSizes.put("Small", "S");
        shirtSizes.put("Medium", "M");
        shirtSizes.put("Large", "L");
        shirtSizes.put("Extra Large", "XL");
        shirtSize = new Selectable(shirtSizes);

        addInput(new Label("Shirt Size", Constants.colors[11]), shirtSize);
        addInput(new Label("Chest Size (cm)", Constants.colors[12]), chestSizeInput);
        addInput(new Label("Waist Size (cm)", Constants.colors[13]), waistSizeInput);
        addInput(new Label("Hip Size (cm)", Constants.colors[14]), hipSizeInput);
        addInput(new Label("Inseam Length (cm)", Constants.colors[15]), inseamLengthInput);
        addInput(new Label("Shoe Size", Constants.colors[16]), shoeSizeInput);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    protected void onCommit(MouseEvent e) {
        super.onCommit(e);
    }
}