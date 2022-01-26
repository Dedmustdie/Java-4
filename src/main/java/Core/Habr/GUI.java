package Core.Habr;

import Core.Habr.Model.ArticleParser;
import Core.Habr.Model.ImgParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame {
    JTextArea textArea = new JTextArea();
    JTextField textStart = new JTextField(10);
    JTextField textEnd = new JTextField(10);
    public GUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setTitle("Habr");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel Panel = new JPanel();
        Panel.setPreferredSize(new Dimension(0, 500));
        rightPanel.setPreferredSize(new Dimension(200, 200));
        leftPanel.setPreferredSize(new Dimension(1000, 200));
        GridLayout layoutRight = new GridLayout(0, 1, 5, 12);
        GridLayout layoutLeft = new GridLayout(0, 1, 1, 1);
        rightPanel.setLayout(layoutRight);
        leftPanel.setLayout(layoutLeft);
        rightPanel.add(new JLabel("Первая страница"));

        JButton startButton = new JButton("Start");

        JButton abortButton = new JButton("Abort");
        add(rightPanel, BorderLayout.EAST);
        add(leftPanel, BorderLayout.WEST);
        rightPanel.add(textStart);
        rightPanel.add(new JLabel("Последняя страница"));
        rightPanel.add(textEnd);
        JScrollPane scroll = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        leftPanel.add(scroll);
        rightPanel.add(startButton);
        rightPanel.add(abortButton);
        add(Panel);



        ParserWorker<ArrayList<String>>  parser = new ParserWorker<>(new ArticleParser(), new ImgParser());


        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int start, end;
                start = Integer.parseInt(textStart.getText());
                end = Integer.parseInt(textEnd.getText());
                parser.setParserSettings(new HabrSettings(start, end));
                parser.onCompletedList.add(new Completed());
                parser.onNewDataList.add(new NewDataa());

                try {
                    parser.Start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        abortButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                parser.Abort();
                //textArea.setToolTipText("Парсинг приостановлен!");
            }
        });

        pack();
        setVisible(true);
    }


    class Completed implements ParserWorker.OnCompleted {

        @Override
        public void OnCompleted(Object sender) {
            //System.out.println("Загрузка закончена");
            textArea.append("\nЗагрузка закончена");
        }
    }
    class NewDataa implements ParserWorker.OnNewDataHandler<ArrayList<String>> {

        @Override
        public void OnNewData(Object sender, ArrayList<String> args) {
            for (String s : args) {
                textArea.append("\n"+s);
            }
        }
    }
}
