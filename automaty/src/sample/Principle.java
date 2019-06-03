package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.pow;


public class Principle extends Task{
    double kt=1;
    public Canvas canvas;
    public ChoiceBox choiceBox;
    public ComboBox comboBox2;
    GraphicsContext gc ;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    int width,height,amount,row,column,ray,nei_size,drawCounter=0;
    Cell[][] board,next_step;
    int[] neighbers;
    int[] neighberi;
    int[] neighberj;
    public int counter=1,new_i,new_j,size=5;
    public boolean stop=false;
    public boolean change=true;
    public boolean col=false;
    List<Color> c_list = new ArrayList<Color>();
    Random rand=new Random();
    Principle(Canvas a, ChoiceBox b, ComboBox c){
        canvas=a;
        choiceBox=b;
        comboBox2=c;
    }
    public Object call() throws InterruptedException {

            gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.WHITE);
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.fillRect(0, 0, width * size, height * size);
            add_color();



        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                next_step[i][j].id = board[i][j].id;
            }








            if(comboBox2.getValue().toString().equals("Von Neumann")) {
                nei_size=4;
                neighberi=new int[nei_size];
                neighberj=new int[nei_size];
                neighbers = new int[nei_size];
                neighberi[0] = 0;
                neighberi[1] = -1;
                neighberi[2] = 1;
                neighberi[3] = 0;
                neighberj[0] = -1;
                neighberj[1] = 0;
                neighberj[2] = 0;
                neighberj[3] = 1;
                while (!stop && change) {
                    change = false;
                    for (int i = 0; i < width; i++)
                        for (int j = 0; j < height; j++) {
                            if (board[i][j].id == 0) {
                                change = true;
                                for (int k = 0; k < nei_size; k++) {
                                    new_i = i + neighberi[k];
                                    new_j = j + neighberj[k];

                                    if (choiceBox.getValue().toString().equals("Absorbing")) {
                                        if (new_i > width - 1)
                                            neighbers[2] = 0;
                                        if (new_i < 0)
                                            neighbers[1] = 0;
                                        if (new_j > height - 1)
                                            neighbers[3] = 0;
                                        if (new_j < 0)
                                            neighbers[0] = 0;
                                        if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                            neighbers[k] = board[new_i][new_j].id;
                                    } else if (choiceBox.getValue().toString().equals("Periodic")) {
                                        if (new_i > width - 1)
                                            new_i = 0;
                                        if (new_i < 0)
                                            new_i = width - 1;
                                        if (new_j > height - 1)
                                            new_j = 0;
                                        if (new_j < 0)
                                            new_j = height - 1;
                                        if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                            neighbers[k] = board[new_i][new_j].id;
                                    }
                                }

                                //most frequently number in array

                                int count = 0, tempCount = 0;
                                int popular = 0;
                                int temp = 0;
                                for (int z = 0; z < (neighbers.length); z++) {
                                    temp = neighbers[z];
                                    tempCount = 0;
                                    if (temp != 0) {
                                        for (int zz = 0; zz < neighbers.length; zz++) {
                                            if (temp == neighbers[zz])
                                                tempCount++;
                                        }
                                    }
                                    if (tempCount >= count) {
                                        popular = temp;
                                        count = tempCount;

                                    }
                                }
                                next_step[i][j].id = popular;

                            }
                        }


                    for (int i = 0; i < width; i++)
                        for (int j = 0; j < height; j++) {
                            board[i][j].id = next_step[i][j].id;
                        }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < width; i++) {
                                for (int j = 0; j < height; j++) {
                                    if (board[i][j].id != 0) {
                                        gc.setFill(c_list.get(board[i][j].id));
                                        gc.fillRect(i * size, j * size, size, size);
                                    }
                                }

                            }

                        }
                    });


                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    while (stop) {
                        Thread.sleep(1000);

                    }

                    canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            board[(int) (event.getX() / size)][(int) (event.getY() / size)].id = counter;
                            counter++;
                            add_color();

                            gc.setFill(c_list.get(board[(int) (event.getX() / size)][(int) (event.getY() / size)].id));
                            gc.fillRect((int) (event.getX() / size) * size, (int) (event.getY() / size) * size, size, size);
                        }
                    });

                }
            }








        if(comboBox2.getValue().toString().equals("Left hexagonal")) {
            nei_size=6;
            neighbers = new int[nei_size];
            neighberi=new int[nei_size];
            neighberj=new int[nei_size];
            neighberi[0] = 0;
            neighberi[1] = 1;
            neighberi[2] = -1;
            neighberi[3] = 1;
            neighberi[4] = -1;
            neighberi[5] = 0;
            neighberj[0] = -1;
            neighberj[1] = -1;
            neighberj[2] = 0;
            neighberj[3] = 0;
            neighberj[4] = 1;
            neighberj[5] = 1;
            while (!stop && change) {
                change = false;
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        if (board[i][j].id == 0) {
                            change = true;
                            for (int k = 0; k < nei_size; k++) {
                                new_i = i + neighberi[k];
                                new_j = j + neighberj[k];

                                if (choiceBox.getValue().toString().equals("Absorbing")) {
                                    if (new_i > width - 1)
                                        neighbers[k] = 0;
                                    if (new_i < 0)
                                        neighbers[k] = 0;
                                    if (new_j > height - 1)
                                        neighbers[k] = 0;
                                    if (new_j < 0)
                                        neighbers[k] = 0;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                } else if (choiceBox.getValue().toString().equals("Periodic")) {
                                    if (new_i > width - 1)
                                        new_i = 0;
                                    if (new_i < 0)
                                        new_i = width - 1;
                                    if (new_j > height - 1)
                                        new_j = 0;
                                    if (new_j < 0)
                                        new_j = height - 1;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                }
                            }

                            //most frequently number in array

                            int count = 0, tempCount = 0;
                            int popular = 0;
                            int temp = 0;
                            for (int z = 0; z < (neighbers.length); z++) {
                                temp = neighbers[z];
                                tempCount = 0;
                                if (temp != 0) {
                                    for (int zz = 0; zz < neighbers.length; zz++) {
                                        if (temp == neighbers[zz])
                                            tempCount++;
                                    }
                                }
                                if (tempCount >= count) {
                                    popular = temp;
                                    count = tempCount;

                                }
                            }
                            next_step[i][j].id = popular;

                        }
                    }


                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        board[i][j].id = next_step[i][j].id;
                    }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < width; i++) {
                            for (int j = 0; j < height; j++) {
                                if (board[i][j].id != 0) {
                                    gc.setFill(c_list.get(board[i][j].id));
                                    gc.fillRect(i * size, j * size, size, size);
                                }
                            }

                        }

                    }
                });


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (stop) {
                    Thread.sleep(1000);

                }

                canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        board[(int) (event.getX() / size)][(int) (event.getY() / size)].id = counter;
                        counter++;
                        add_color();

                        gc.setFill(c_list.get(board[(int) (event.getX() / size)][(int) (event.getY() / size)].id));
                        gc.fillRect((int) (event.getX() / size) * size, (int) (event.getY() / size) * size, size, size);
                    }
                });

            }
        }











        if(comboBox2.getValue().toString().equals("Right hexagonal")) {
            nei_size=6;
            neighbers = new int[nei_size];
            neighberi=new int[nei_size];
            neighberj=new int[nei_size];
            neighberi[0] = -1;
            neighberi[1] = 0;
            neighberi[2] = -1;
            neighberi[3] = 1;
            neighberi[4] = 0;
            neighberi[5] = 1;
            neighberj[0] = -1;
            neighberj[1] = -1;
            neighberj[2] = 0;
            neighberj[3] = 0;
            neighberj[4] = 1;
            neighberj[5] = 1;
            while (!stop && change) {
                change = false;
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        if (board[i][j].id == 0) {
                            change = true;
                            for (int k = 0; k < nei_size; k++) {
                                new_i = i + neighberi[k];
                                new_j = j + neighberj[k];

                                if (choiceBox.getValue().toString().equals("Absorbing")) {
                                    if (new_i > width - 1)
                                        neighbers[k] = 0;
                                    if (new_i < 0)
                                        neighbers[k] = 0;
                                    if (new_j > height - 1)
                                        neighbers[k] = 0;
                                    if (new_j < 0)
                                        neighbers[k] = 0;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                } else if (choiceBox.getValue().toString().equals("Periodic")) {
                                    if (new_i > width - 1)
                                        new_i = 0;
                                    if (new_i < 0)
                                        new_i = width - 1;
                                    if (new_j > height - 1)
                                        new_j = 0;
                                    if (new_j < 0)
                                        new_j = height - 1;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                }
                            }

                            //most frequently number in array

                            int count = 0, tempCount = 0;
                            int popular = 0;
                            int temp = 0;
                            for (int z = 0; z < (neighbers.length); z++) {
                                temp = neighbers[z];
                                tempCount = 0;
                                if (temp != 0) {
                                    for (int zz = 0; zz < neighbers.length; zz++) {
                                        if (temp == neighbers[zz])
                                            tempCount++;
                                    }
                                }
                                if (tempCount >= count) {
                                    popular = temp;
                                    count = tempCount;

                                }
                            }
                            next_step[i][j].id = popular;

                        }
                    }


                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        board[i][j].id = next_step[i][j].id;
                    }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < width; i++) {
                            for (int j = 0; j < height; j++) {
                                if (board[i][j].id != 0) {
                                    gc.setFill(c_list.get(board[i][j].id));
                                    gc.fillRect(i * size, j * size, size, size);
                                }
                            }

                        }

                    }
                });


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (stop) {
                    Thread.sleep(1000);

                }

                canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        board[(int) (event.getX() / size)][(int) (event.getY() / size)].id = counter;
                        counter++;
                        add_color();

                        gc.setFill(c_list.get(board[(int) (event.getX() / size)][(int) (event.getY() / size)].id));
                        gc.fillRect((int) (event.getX() / size) * size, (int) (event.getY() / size) * size, size, size);
                    }
                });

            }
        }







        if(comboBox2.getValue().toString().equals("Random hexagonal")) {
            nei_size=6;
            neighbers = new int[nei_size];
            neighberi=new int[nei_size];
            neighberj=new int[nei_size];
            Random rand = new Random();
            while (!stop && change) {
                change = false;
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        int r=rand.nextInt(2);
                        if(r==0)
                        {
                            neighberi[0]=0;
                            neighberi[1]= 1;
                            neighberi[2]=-1;
                            neighberi[3]=1;
                            neighberi[4]=-1;
                            neighberi[5]=0;

                            neighberj[0]=-1;
                            neighberj[1]= -1;
                            neighberj[2]=0;
                            neighberj[3]=0;
                            neighberj[4]=1;
                            neighberj[5]=1;

                        }
                        else if(r==1)
                        {
                            neighberi[0]=-1;
                            neighberi[1]= 0;
                            neighberi[2]=-1;
                            neighberi[3]=1;
                            neighberi[4]=0;
                            neighberi[5]=1;

                            neighberj[0]=-1;
                            neighberj[1]= -1;
                            neighberj[2]=0;
                            neighberj[3]=0;
                            neighberj[4]=1;
                            neighberj[5]=1;
                        }
                        if (board[i][j].id == 0) {
                            change = true;
                            for (int k = 0; k < nei_size; k++) {
                                new_i = i + neighberi[k];
                                new_j = j + neighberj[k];

                                if (choiceBox.getValue().toString().equals("Absorbing")) {
                                    if (new_i > width - 1)
                                        neighbers[k] = 0;
                                    if (new_i < 0)
                                        neighbers[k] = 0;
                                    if (new_j > height - 1)
                                        neighbers[k] = 0;
                                    if (new_j < 0)
                                        neighbers[k] = 0;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                } else if (choiceBox.getValue().toString().equals("Periodic")) {
                                    if (new_i > width - 1)
                                        new_i = 0;
                                    if (new_i < 0)
                                        new_i = width - 1;
                                    if (new_j > height - 1)
                                        new_j = 0;
                                    if (new_j < 0)
                                        new_j = height - 1;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                }
                            }

                            //most frequently number in array

                            int count = 0, tempCount = 0;
                            int popular = 0;
                            int temp = 0;
                            for (int z = 0; z < (neighbers.length); z++) {
                                temp = neighbers[z];
                                tempCount = 0;
                                if (temp != 0) {
                                    for (int zz = 0; zz < neighbers.length; zz++) {
                                        if (temp == neighbers[zz])
                                            tempCount++;
                                    }
                                }
                                if (tempCount >= count) {
                                    popular = temp;
                                    count = tempCount;

                                }
                            }
                            next_step[i][j].id = popular;

                        }
                    }


                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        board[i][j].id = next_step[i][j].id;
                    }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < width; i++) {
                            for (int j = 0; j < height; j++) {
                                if (board[i][j].id != 0) {
                                    gc.setFill(c_list.get(board[i][j].id));
                                    gc.fillRect(i * size, j * size, size, size);
                                }
                            }

                        }

                    }
                });


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (stop) {
                    Thread.sleep(1000);

                }

                canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        board[(int) (event.getX() / size)][(int) (event.getY() / size)].id = counter;
                        counter++;
                        add_color();

                        gc.setFill(c_list.get(board[(int) (event.getX() / size)][(int) (event.getY() / size)].id));
                        gc.fillRect((int) (event.getX() / size) * size, (int) (event.getY() / size) * size, size, size);
                    }
                });

            }
        }














        if(comboBox2.getValue().toString().equals("Random pentagonal")) {
            nei_size=5;
            neighbers = new int[nei_size];
            neighberi=new int[nei_size];
            neighberj=new int[nei_size];
            Random rand = new Random();
            while (!stop && change) {

                change = false;
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        int r=rand.nextInt(4);
                        if(r==0)
                        {
                            neighberi[0]=-1;
                            neighberi[1]= 0;
                            neighberi[2]=-1;
                            neighberi[3]=-1;
                            neighberi[4]=0;

                            neighberj[0]=-1;
                            neighberj[1]= -1;
                            neighberj[2]=0;
                            neighberj[3]=1;
                            neighberj[4]=1;

                        }
                        else if(r==1)
                        {
                            neighberi[0]=0;
                            neighberi[1]= 1;
                            neighberi[2]=1;
                            neighberi[3]=0;
                            neighberi[4]=1;

                            neighberj[0]=-1;
                            neighberj[1]= -1;
                            neighberj[2]=0;
                            neighberj[3]=1;
                            neighberj[4]=1;
                        }
                        else if(r==2)
                        {
                            neighberi[0]=-1;
                            neighberi[1]= 1;
                            neighberi[2]=-1;
                            neighberi[3]=0;
                            neighberi[4]=1;

                            neighberj[0]=0;
                            neighberj[1]= 0;
                            neighberj[2]=1;
                            neighberj[3]=1;
                            neighberj[4]=1;
                        }
                        else if(r==3)
                        {
                            neighberi[0]=-1;
                            neighberi[1]= 0;
                            neighberi[2]=1;
                            neighberi[3]=-1;
                            neighberi[4]=1;

                            neighberj[0]=1;
                            neighberj[1]= 1;
                            neighberj[2]=1;
                            neighberj[3]=0;
                            neighberj[4]=0;
                        }
                        if (board[i][j].id == 0) {
                            change = true;
                            for (int k = 0; k < nei_size; k++) {
                                new_i = i + neighberi[k];
                                new_j = j + neighberj[k];

                                if (choiceBox.getValue().toString().equals("Absorbing")) {
                                    if (new_i > width - 1)
                                        neighbers[k] = 0;
                                    if (new_i < 0)
                                        neighbers[k] = 0;
                                    if (new_j > height - 1)
                                        neighbers[k] = 0;
                                    if (new_j < 0)
                                        neighbers[k] = 0;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                } else if (choiceBox.getValue().toString().equals("Periodic")) {
                                    if (new_i > width - 1)
                                        new_i = 0;
                                    if (new_i < 0)
                                        new_i = width - 1;
                                    if (new_j > height - 1)
                                        new_j = 0;
                                    if (new_j < 0)
                                        new_j = height - 1;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                }
                            }

                            //most frequently number in array

                            int count = 0, tempCount = 0;
                            int popular = 0;
                            int temp = 0;
                            for (int z = 0; z < (neighbers.length); z++) {
                                temp = neighbers[z];
                                tempCount = 0;
                                if (temp != 0) {
                                    for (int zz = 0; zz < neighbers.length; zz++) {
                                        if (temp == neighbers[zz])
                                            tempCount++;
                                    }
                                }
                                if (tempCount >= count) {
                                    popular = temp;
                                    count = tempCount;

                                }
                            }
                            next_step[i][j].id = popular;

                        }
                    }


                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        board[i][j].id = next_step[i][j].id;
                    }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < width; i++) {
                            for (int j = 0; j < height; j++) {
                                if (board[i][j].id != 0) {
                                    gc.setFill(c_list.get(board[i][j].id));
                                    gc.fillRect(i * size, j * size, size, size);
                                }
                            }

                        }

                    }
                });


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (stop) {
                    Thread.sleep(1000);

                }

                canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        board[(int) (event.getX() / size)][(int) (event.getY() / size)].id = counter;
                        counter++;
                        add_color();

                        gc.setFill(c_list.get(board[(int) (event.getX() / size)][(int) (event.getY() / size)].id));
                        gc.fillRect((int) (event.getX() / size) * size, (int) (event.getY() / size) * size, size, size);
                    }
                });

            }
        }




        if(comboBox2.getValue().toString().equals("Moore")) {
            nei_size=8;
            neighberi=new int[nei_size];
            neighberj=new int[nei_size];
            neighberi[0] = -1;
            neighberi[1] = 0;
            neighberi[2] = 1;
            neighberi[3] = -1;
            neighberi[4] = 1;
            neighberi[5] = -1;
            neighberi[6] = 0;
            neighberi[7] = 1;
            neighberj[0] = -1;
            neighberj[1] = -1;
            neighberj[2] = -1;
            neighberj[3] = 0;
            neighberj[4] = 0;
            neighberj[5] = 1;
            neighberj[6] = 1;
            neighberj[7] = 1;
            neighbers = new int[nei_size];
            while (!stop && change) {
                change = false;
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        if (board[i][j].id == 0) {
                            change = true;
                            for (int k = 0; k < nei_size; k++) {
                                new_i = i + neighberi[k];
                                new_j = j + neighberj[k];

                                if (choiceBox.getValue().toString().equals("Absorbing")) {
                                    if (new_i > width - 1)
                                        neighbers[k] = 0;
                                    if (new_i < 0)
                                        neighbers[k] = 0;
                                    if (new_j > height - 1)
                                        neighbers[k] = 0;
                                    if (new_j < 0)
                                        neighbers[k] = 0;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                } else if (choiceBox.getValue().toString().equals("Periodic")) {
                                    if (new_i > width - 1)
                                        new_i = 0;
                                    if (new_i < 0)
                                        new_i = width - 1;
                                    if (new_j > height - 1)
                                        new_j = 0;
                                    if (new_j < 0)
                                        new_j = height - 1;
                                    if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                        neighbers[k] = board[new_i][new_j].id;
                                }
                            }

                            //most frequently number in array

                            int count = 0, tempCount = 0;
                            int popular = 0;
                            int temp = 0;
                            for (int z = 0; z < (neighbers.length); z++) {
                                temp = neighbers[z];
                                tempCount = 0;
                                if (temp != 0) {
                                    for (int zz = 0; zz < neighbers.length; zz++) {
                                        if (temp == neighbers[zz])
                                            tempCount++;
                                    }
                                }
                                if (tempCount >= count) {
                                    popular = temp;
                                    count = tempCount;

                                }
                            }
                            next_step[i][j].id = popular;

                        }
                    }


                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        board[i][j].id = next_step[i][j].id;
                    }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < width; i++) {
                            for (int j = 0; j < height; j++) {
                                if (board[i][j].id != 0) {
                                    gc.setFill(c_list.get(board[i][j].id));
                                    gc.fillRect(i * size, j * size, size, size);
                                }
                            }

                        }

                    }
                });


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (stop) {
                    Thread.sleep(1000);

                }

                canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        board[(int) (event.getX() / size)][(int) (event.getY() / size)].id = counter;
                        counter++;
                        add_color();

                        gc.setFill(c_list.get(board[(int) (event.getX() / size)][(int) (event.getY() / size)].id));
                        gc.fillRect((int) (event.getX() / size) * size, (int) (event.getY() / size) * size, size, size);
                    }
                });

            }
        }


//////////////////////////////MONTE CARLO///////////////////////////////

        while(!stop)
        {
            drawCounter++;
            int mcRow=rand.nextInt(width);
            int mcColumn=rand.nextInt(height);
            int r = rand.nextInt(nei_size);


            if(comboBox2.getValue().toString().equals("Random hexagonal")) {
                int rr=rand.nextInt(2);
                if(rr==0)
                {
                    neighberi[0]=0;
                    neighberi[1]= 1;
                    neighberi[2]=-1;
                    neighberi[3]=1;
                    neighberi[4]=-1;
                    neighberi[5]=0;

                    neighberj[0]=-1;
                    neighberj[1]= -1;
                    neighberj[2]=0;
                    neighberj[3]=0;
                    neighberj[4]=1;
                    neighberj[5]=1;

                }
                else if(rr==1)
                {
                    neighberi[0]=-1;
                    neighberi[1]= 0;
                    neighberi[2]=-1;
                    neighberi[3]=1;
                    neighberi[4]=0;
                    neighberi[5]=1;

                    neighberj[0]=-1;
                    neighberj[1]= -1;
                    neighberj[2]=0;
                    neighberj[3]=0;
                    neighberj[4]=1;
                    neighberj[5]=1;
                }
            }

            if(comboBox2.getValue().toString().equals("Random pentagonal"))
            {
                int rr=rand.nextInt(4);
                if(rr==0)
                {
                    neighberi[0]=-1;
                    neighberi[1]= 0;
                    neighberi[2]=-1;
                    neighberi[3]=-1;
                    neighberi[4]=0;

                    neighberj[0]=-1;
                    neighberj[1]= -1;
                    neighberj[2]=0;
                    neighberj[3]=1;
                    neighberj[4]=1;

                }
                else if(rr==1)
                {
                    neighberi[0]=0;
                    neighberi[1]= 1;
                    neighberi[2]=1;
                    neighberi[3]=0;
                    neighberi[4]=1;

                    neighberj[0]=-1;
                    neighberj[1]= -1;
                    neighberj[2]=0;
                    neighberj[3]=1;
                    neighberj[4]=1;
                }
                else if(rr==2)
                {
                    neighberi[0]=-1;
                    neighberi[1]= 1;
                    neighberi[2]=-1;
                    neighberi[3]=0;
                    neighberi[4]=1;

                    neighberj[0]=0;
                    neighberj[1]= 0;
                    neighberj[2]=1;
                    neighberj[3]=1;
                    neighberj[4]=1;
                }
                else if(rr==3)
                {
                    neighberi[0]=-1;
                    neighberi[1]= 0;
                    neighberi[2]=1;
                    neighberi[3]=-1;
                    neighberi[4]=1;

                    neighberj[0]=1;
                    neighberj[1]= 1;
                    neighberj[2]=1;
                    neighberj[3]=0;
                    neighberj[4]=0;
                }
            }

                for (int k = 0; k < nei_size; k++) {
                    new_i = mcRow + neighberi[k];
                    new_j = mcColumn + neighberj[k];

                    if (choiceBox.getValue().toString().equals("Absorbing")) {
                        if (new_i > width - 1)
                            neighbers[k] = -1;
                        if (new_i < 0)
                            neighbers[k] = -1;
                        if (new_j > height - 1)
                            neighbers[k] = -1;
                        if (new_j < 0)
                            neighbers[k] = -1;
                        if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                            neighbers[k] = board[new_i][new_j].id;
                    } else if (choiceBox.getValue().toString().equals("Periodic")) {
                        if (new_i > width - 1)
                            new_i = 0;
                        if (new_i < 0)
                            new_i = width - 1;
                        if (new_j > height - 1)
                            new_j = 0;
                        if (new_j < 0)
                            new_j = height - 1;
                        if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                            neighbers[k] = board[new_i][new_j].id;
                    }
                }


                for(int k=0;k<nei_size;k++)
                {
                    if(board[mcRow][mcColumn].id != neighbers[k] && neighbers[k]>0)
                    board[mcRow][mcColumn].energy++;
                }
                for(int k=0;k<nei_size;k++)
                {
                    if(neighbers[r] != neighbers[k])
                        board[mcRow][mcColumn].energy2++;
                }

                if((board[mcRow][mcColumn].energy2<=board[mcRow][mcColumn].energy) && (board[mcRow][mcColumn].energy!=0) && neighbers[r]>0)
                {
                    board[mcRow][mcColumn].id=neighbers[r];
                    if(drawCounter%1000==0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < width; i++) {
                                    for (int j = 0; j < height; j++) {
                                        if (board[i][j].id != 0) {
                                            gc.setFill(c_list.get(board[i][j].id));
                                            gc.fillRect(i * size, j * size, size, size);
                                        }
                                    }

                                }

                            }
                        });
                        Thread.sleep(200);
                    }

                }

            if((board[mcRow][mcColumn].energy2>board[mcRow][mcColumn].energy) && (board[mcRow][mcColumn].energy!=0) && neighbers[r]>0)
            {
                double odds=pow(Math.E,-(board[mcRow][mcColumn].energy2-board[mcRow][mcColumn].energy)/kt)*100;
                if(rand.nextInt(101)<odds) {

                    board[mcRow][mcColumn].id = neighbers[r];
                    if (drawCounter % 1000 == 0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < width; i++) {
                                    for (int j = 0; j < height; j++) {
                                        if (board[i][j].id != 0) {
                                            gc.setFill(c_list.get(board[i][j].id));
                                            gc.fillRect(i * size, j * size, size, size);
                                        }
                                    }

                                }

                            }
                        });

                        Thread.sleep(200);
                    }
                }

            }

            board[mcRow][mcColumn].energy=0;
            board[mcRow][mcColumn].energy2=0;

            while (stop) {
                Thread.sleep(1000);
            }




        }




        return 0;
    }
    void homogeneous()
    {
        int x=0,y=0;
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<column;j++)
           {

               x=i*width/row+width/row/2;
               y=j*height/column+height/column/2;
               if(x>width-1)
                   i=width-1;
               if(y>height-1)
                   j=height-1;
               board[x][y].id=counter;
                       counter++;
               add_color();
           }

        }
    }

    void random()
    {
        Random rand = new Random();
        for(int i=0;i<amount;i++)
        {
            int r_i=rand.nextInt(width);
            int r_j=rand.nextInt(height);
            if(board[r_i][r_j].id==0) {
                board[r_i][r_j].id = counter;
                counter++;
                add_color();
            }
            else
                i--;
        }
    }
    void ray() {
        try {
            int exception = 0;
            Random rand = new Random();
            Boolean agremeent = true;
            for (int i = 0; i < amount; i++) {
                int r_i = rand.nextInt(width);
                int r_j = rand.nextInt(height);

                for (int ii = 0; ii < width; ii++)
                    for (int jj = 0; jj < height; jj++) {
                        if (board[ii][jj].id != 0 && (Math.sqrt(Math.pow(ii - r_i, 2) + Math.pow(jj - r_j, 2)) < ray)) {
                            agremeent = false;
                        }
                    }

                if (board[r_i][r_j] .id== 0 && agremeent) {
                    board[r_i][r_j].id = counter;
                    counter++;
                    add_color();
                } else {
                    i--;
                    agremeent = true;
                    exception++;
                }
                if (exception > 30000) {
                    throw new RuntimeException();
                }

            }

        }
        catch(RuntimeException e)
        {
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Cannot find place");
                alert.showAndWait();
        }
    }

    void add_color(){
        while(true) {
            Random rand = new Random();
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            Color c = Color.rgb(r, g, b);
            for (int j = 0; j < c_list.size(); j++) {
                if (c == c_list.get(j)) {
                    col = false;
                }

            }
            if (col) {
                c_list.add(c);
                break;
            }
            col=true;
        }
    }

    void energy(){
            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++) {
                    for(int k=0;k<nei_size;k++ )
                    {
                        new_i = i + neighberi[k];
                        new_j = j + neighberj[k];

                        if (choiceBox.getValue().toString().equals("Absorbing")) {
                            if (new_i > width - 1)
                                neighbers[k] = -1;
                            if (new_i < 0)
                                neighbers[k] = -1;
                            if (new_j > height - 1)
                                neighbers[k] = -1;
                            if (new_j < 0)
                                neighbers[k] = -1;
                            if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                neighbers[k] = board[new_i][new_j].id;
                        } else if (choiceBox.getValue().toString().equals("Periodic")) {
                            if (new_i > width - 1)
                                new_i = 0;
                            if (new_i < 0)
                                new_i = width - 1;
                            if (new_j > height - 1)
                                new_j = 0;
                            if (new_j < 0)
                                new_j = height - 1;
                            if (new_i <= width - 1 && new_i >= 0 && new_j <= height - 1 && new_j >= 0)
                                neighbers[k] = board[new_i][new_j].id;
                        }
                        if((board[i][j].id != neighbers[k]) && neighbers[k]>0) {
                            board[i][j].energy++;
                        }
                    }
                }
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            if (board[i][j].energy ==0) {
                                gc.setFill(Color.GREEN);
                                gc.fillRect(i * size, j * size, size, size);
                            }
                            else
                            {
                                Color c = Color.rgb(0,0,board[i][j].energy*25);
                                gc.setFill(c);
                                gc.fillRect(i * size, j * size, size, size);
                            }
                            board[i][j].energy=0;
                            board[i][j].energy2=0;
                        }


                    }

                }
            });


    }

}
