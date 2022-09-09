/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Contador {
    static int counter = 0;

    public static void main(String[] args) {
        Frame frame = new Frame("Counter Example");
        frame.setSize(400, 300);

        Button button = new Button("Click");
        button.setBounds(100, 50, 100, 40);
        Label label = new Label();

        label.setBounds(100, 100, 200, 100);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                String counterAsString = String.valueOf(counter);
                label.setText("Click Counter: " + counterAsString);
            }
        });


        frame.add(button);
        frame.add(label);

        frame.setLayout(null);
        frame.setVisible(true);

    }
}
    

