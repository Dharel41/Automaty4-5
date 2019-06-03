package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Controller{

    public Canvas canvas;
    public TextField text_field2;
    public TextField text_field3;
    public TextField text_field4;
    public TextField field_row;
    public TextField field_kt;
    public TextField field_column;
    public TextField field_ray;
    public ComboBox comboBox;
    public ComboBox comboBox2;
    public ChoiceBox choiceBox;
    ObservableList<String> rule;
    ObservableList<String> boundary;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private Alert alert2 = new Alert(Alert.AlertType.ERROR);
    private Alert alert3 = new Alert(Alert.AlertType.ERROR);
    private Principle principle;
    private Thread t=new Thread();


    public void start() {
        principle=new Principle(canvas,choiceBox,comboBox2);
        principle.stop=false;

try {
    principle.height = Integer.parseInt(text_field3.getText());
    principle.width= Integer.parseInt(text_field2.getText());
    principle.amount= Integer.parseInt(text_field4.getText());
    principle.row= Integer.parseInt(field_column.getText());
    principle.column= Integer.parseInt(field_row.getText());
    principle.ray= Integer.parseInt(field_ray.getText());
    principle.board =  new Cell[principle.width][principle.height];
    principle.next_step = new Cell[principle.width][principle.height];
    principle.kt=Double.parseDouble(field_kt.getText());
    if(principle.kt<0.1 || principle.kt>6)
    {
        throw new NumberFormatException();
    }
    for(int i=0;i<principle.width;i++)
    {
        for(int j=0;j<principle.height;j++)
        {
            principle.board[i][j]=new Cell();
            principle.next_step[i][j]=new Cell();
        }
    }

    if (comboBox.getValue().toString().equals("Homogeneous"))
    {
        principle.homogeneous();
    }

    if (comboBox.getValue().toString().equals("Random"))
    {
        principle.random();
    }
    if (comboBox.getValue().toString().equals("Ray"))
    {
        principle.ray();
        text_field4.setText(principle.counter-1+"");
    }
       if(t.isAlive())
        {
        t.stop();
        }

        t=new Thread(principle);
        t.start();


}
catch(NumberFormatException e)
{
    alert.setTitle("Error Dialog");
    alert.setHeaderText("Incorrect data");
    alert.showAndWait();
}
catch(NullPointerException e)
{
    alert2.setTitle("Error Dialog");
    alert2.setHeaderText("You didn't choose pattern");
    alert2.showAndWait();
}

catch(ArrayIndexOutOfBoundsException e)
{
    alert3.setTitle("Error Dialog");
    alert3.setHeaderText("ArrayIndexOutOfBoundsException");
    alert3.showAndWait();
}


    }
    public void configurate(){
        ObservableList<String> rule =
                FXCollections.observableArrayList("Homogeneous","Random","Ray","Set");
        comboBox.setItems(rule);
        ObservableList<String> neigh_list=
                FXCollections.observableArrayList("Von Neumann","Random pentagonal","Left hexagonal","Right hexagonal","Random hexagonal","Moore" );
        comboBox2.setItems(neigh_list);


    }
    public void configurate2(){
        ObservableList<String> boundary = FXCollections.observableArrayList("Absorbing","Periodic");
        choiceBox.setItems(boundary);
    }
    public void stop(){
        principle.stop=!(principle.stop);
    }
    public void energy(){principle.energy();}

}
