package ua.artcode.taxi.view.driver_view;

import ua.artcode.taxi.exception.RegisterException;
import ua.artcode.taxi.model.Car;
import ua.artcode.taxi.model.User;
import ua.artcode.taxi.model.UserIdentifier;
import ua.artcode.taxi.model.ClientAccessToken;
import ua.artcode.taxi.service.UserService;
import ua.artcode.taxi.view.UserLogin;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DriverRegistration extends JFrame {

    private final UserService userService;

    private JLabel mainLabel;
    private JLabel nullLabel;
    private JLabel phoneLabel;
    private JTextField phoneText;
    private JLabel nameLabel;
    private JTextField nameText;
    private JLabel passLabel;
    private JTextField passText;
    private JLabel carTypeLabel;
    private JTextField carTypeText;
    private JLabel carModelLabel;
    private JTextField carModelText;
    private JLabel carNumberLabel;
    private JTextField carNumberText;
    private JPanel buttonPanel1;
    private JPanel buttonPanel2;

    private JButton okButton;
    private JButton returnButton;

    public DriverRegistration(UserService userService){

        this.userService = userService;

        setTitle("Main");
        setSize(400, 600);
        init();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void init() {
        GridLayout gridLayout = new GridLayout(8, 1);
        setLayout(gridLayout);

        mainLabel = new JLabel("DRIVER REGISTRATION");
        nullLabel = new JLabel("");

        phoneLabel = new JLabel("PHONE:");
        phoneText = new JTextField();

        nameLabel = new JLabel("NAME:");
        nameText = new JTextField();

        passLabel = new JLabel("PASSWORD:");
        passText = new JTextField();

        carTypeLabel = new JLabel("CAR TYPE:");
        carTypeText = new JTextField();

        carModelLabel = new JLabel("CAR MODEL:");
        carModelText = new JTextField();

        carNumberLabel = new JLabel("CAR NUMBER:");
        carNumberText = new JTextField();

        //for change registration
        if (ClientAccessToken.accessToken != null) {
            User current = userService.getUser(ClientAccessToken.accessToken);
            if (current != null) {
                phoneText.setText(current.getPhone());
                passText.setText(current.getPass());
                nameText.setText(current.getName());
                carTypeText.setText(current.getCar().getType());
                carModelText.setText(current.getCar().getModel());
                carNumberText.setText(current.getCar().getNumber());
            }
        }

        buttonPanel1 = new JPanel(new GridLayout(1,1));
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (phoneText.getText().equals("")) {
                    JOptionPane.showMessageDialog(getParent(), "Please enter your phone");
                } else if (passText.getText().equals("")) {
                    JOptionPane.showMessageDialog(getParent(), "Please enter your password");
                } else if (nameText.getText().equals("")) {
                    JOptionPane.showMessageDialog(getParent(), "Please enter your name");
                } else if (carTypeText.getText().equals("")) {
                    JOptionPane.showMessageDialog(getParent(), "Please enter type of your car");
                } else if (carModelText.getText().equals("")) {
                    JOptionPane.showMessageDialog(getParent(), "Please enter model of your car");
                } else if (carNumberText.getText().equals("")) {
                    JOptionPane.showMessageDialog(getParent(), "Please enter number of your car");

                } else {
                    try {
                        //for change registration
                        if (ClientAccessToken.accessToken != null) {
                            User newUser = new User(UserIdentifier.D, phoneText.getText(), passText.getText(), nameText.getText(),
                                    new Car(carTypeText.getText(), carModelText.getText(), carNumberText.getText()));
                            newUser.setId(userService.getUser(ClientAccessToken.accessToken).getId());

                            userService.updateUser(newUser, ClientAccessToken.accessToken);

                        //for new registration
                        } else if (ClientAccessToken.accessToken == null) {
                            User newDriver = new User(UserIdentifier.D, phoneText.getText(), passText.getText(), nameText.getText(),
                                    new Car(carTypeText.getText(), carModelText.getText(), carNumberText.getText()));

                            userService.registerDriver(newDriver);
                            ClientAccessToken.accessToken = userService.login(newDriver.getPhone(), newDriver.getPass());
                        }

                        dispose();
                        new DriverMenu(userService);
                        JOptionPane.showMessageDialog(getParent(), "You have been successfully registered");

                    } catch (RegisterException e1) {
                        JOptionPane.showMessageDialog(getParent(), "You enter wrong data! Try a different phone number");
                    } catch (LoginException e1) {
                        JOptionPane.showMessageDialog(getParent(), "You enter wrong data! Try a different phone number");
                    }
                }
            }
        });

        buttonPanel2 = new JPanel(new GridLayout(1,1));
        returnButton = new JButton("RETURN");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserLogin(userService);
                dispose();
            }
        });

        buttonPanel1.add(okButton);
        buttonPanel2.add(returnButton);

        getContentPane().add(mainLabel);
        getContentPane().add(nullLabel);

        getContentPane().add(phoneLabel);
        getContentPane().add(phoneText);

        getContentPane().add(passLabel);
        getContentPane().add(passText);

        getContentPane().add(nameLabel);
        getContentPane().add(nameText);

        getContentPane().add(carTypeLabel);
        getContentPane().add(carTypeText);

        getContentPane().add(carModelLabel);
        getContentPane().add(carModelText);

        getContentPane().add(carNumberLabel);
        getContentPane().add(carNumberText);

        getContentPane().add(buttonPanel1);
        getContentPane().add(buttonPanel2);
    }
}
