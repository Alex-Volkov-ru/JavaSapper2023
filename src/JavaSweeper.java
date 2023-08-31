import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class JavaSweeper extends JFrame
{
    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 9; // количество столбцов
    private final int ROWS = 9; // количество строк
    private final int BOMBS = 10; // количество бомб
    private final int IMAGE_SIZE = 50; // картинка


    public static void main(String[] args) {

        new JavaSweeper(); // Это вывод поля
    }
    private  JavaSweeper() {

        game = new Game(COLS,ROWS, BOMBS);
        game.start();
        setImages(); // тянем функцию картинок
        initLabel();
        initiPanel(); // тянем функцию размер поля
        initFrame(); // Это программа для начала поля


    }
    private void initLabel () {
      label = new JLabel("Sapper_2023");
      add(label, BorderLayout.SOUTH);
    }

    private void initiPanel(){
        panel = new JPanel() {
            @Override
            protected void paintComponent (Graphics g) { // Функия для вывода картинок

                super.paintComponent(g);
                    for(Coord coord : Ranges.getAllCoords()){
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if(e.getButton()== MouseEvent.BUTTON1)
                    game.pressLeftButton (coord);
                if(e.getButton()== MouseEvent.BUTTON3)
                    game.pressRightButton (coord);
                if(e.getButton()== MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                    panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE)); // Размер поля (Экран)
        add (panel);
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED : return  "Think Twice!";
            case BOMBED: return  " You Lose! Big BA-DA-BOOM!";
            case WINNER: return "VI KA!";
            default: return "Welcome";
        }
    }

    private void initFrame() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // закрытие игры
        setTitle("Jawa Sapper_2023"); // Название игры на панельки
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null); // установка текста по середине экрана
    }

    private void setImages (){
        for(sweeper.Box box: Box.values()) // загружаем картинки с контейнера
            box.image = getImage(box.name().toLowerCase());
    }
    private Image getImage(String name){
        String filename = "img/" + name + ".png"; // Готовим место для файла каждой картинки
        ImageIcon icon = new ImageIcon (getClass().getResource(filename)); // достаем картинку
        return icon.getImage(); // Здесь возвращаем именно картинку
    }
}
