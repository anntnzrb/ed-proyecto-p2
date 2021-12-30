package ec.edu.espol.proyecto.controller;

import ec.edu.espol.proyecto.game.Board;
import ec.edu.espol.proyecto.game.Player;
import ec.edu.espol.proyecto.utils.Util;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

final public class SecondController implements Initializable{

    private Stage stage;
    private Player player1;
    private Player player2;
    private Board board;

    @FXML
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnRegresar;
    private Button btnMostrar;
    private Button btnIniciar;
    private Label lblJugador1;
    private Label lblJugador2;
    private Label lblMarca1;
    private Label lblMarca2;
    
    private boolean alternateTurn = false;
    private int counter = 0;
    @FXML
    private Text winnerText;

    private int playerTurn = 0;

    ArrayList<Button> buttons;

    public void setNombre1(final String nombre1) {
        lblJugador1.setText(nombre1);
    }

    public void setNombre2(final String nombre2) {
        lblJugador2.setText(nombre2);
    }

    public void setMarca1(final String marca) {
        lblMarca1.setText(marca);
    }

    public void setMarca2(final String marca) {
        lblMarca2.setText(marca);
    }

    private void crearJugador() {
        player1 = new Player(lblJugador1.getText());
        lblJugador1.setText(String.format(player1.getNickname()));
        
        player2 = new Player(lblJugador2.getText());
        lblJugador2.setText(String.format(player2.getNickname()));
        
    }

    @FXML
    private void onRegresarBtnClick(final ActionEvent ae) throws IOException {
        stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        stage.setScene(Util.getNewScene("main"));
        stage.show();
    }

    @FXML
    private void onSalirBtnClick() {
        Platform.exit();
    }
    
//    
//    private void onIniciarBtnClick() {
//    
//        crearJugador();
//       
//    }
    
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9));

        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });
    }

    @FXML
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        winnerText.setText("Tres en Raya");
    }

    public void resetButton(Button button){
        button.setDisable(false);
        button.setText("");
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            setPlayerSymbol(button);
            button.setDisable(true);
            checkIfGameIsOver();
        });
    }

    public void setPlayerSymbol(Button button){
        if(playerTurn % 2 == 0){
            button.setText("X");
            playerTurn = 1;
        } else{
            button.setText("O");
            playerTurn = 0;
        }
    }

    public void checkIfGameIsOver(){
        for (int a = 0; a < 8; a++) {
            switch (a) {
                case 0 : 
                    board.checkWin();
                case 1 :
                    board.checkWin();
                case 2 : 
                    board.checkWin();
                case 3 : 
                    board.checkWin();
                case 4 : 
                    board.checkWin();
                case 5 : 
                    board.checkWin();
                case 6 : 
                    board.checkWin();
                case 7 : 
                    board.checkWin();
                default : 
                    break;
            };

//            //X winner
//            if (line.equals("XXX")) {
//                winnerText.setText("X won!");
//            }
//
//            //O winner
//            else if (line.equals("OOO")) {
//                winnerText.setText("O won!");
//            }
        }
    }
    
    private void changeTurn(Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                
                String winnerString = "";
                if (button.getText().isEmpty()) {
                    if (!alternateTurn) {
                        button.setText("X");
                        alternateTurn = true;
                    } else {
                        button.setText("O");
                        alternateTurn = false;
                    }
                    
                    winnerString = button.getText();
                    counter++;
                }
                
                // first column
                if(btn1.getText().equals(winnerString) && btn4.getText().equals(winnerString) && btn7.getText().equals(winnerString) ||
                        // second column
                        btn2.getText().equals(winnerString) && btn5.getText().equals(winnerString) && btn8.getText().equals(winnerString) ||
                        // third column
                        btn3.getText().equals(winnerString) && btn6.getText().equals(winnerString) && btn9.getText().equals(winnerString) ||
                     // first row
                        btn1.getText().equals(winnerString) && btn2.getText().equals(winnerString) && btn3.getText().equals(winnerString) ||
                     // second row
                        btn4.getText().equals(winnerString) && btn5.getText().equals(winnerString) && btn6.getText().equals(winnerString) ||
                     // third row
                        btn7.getText().equals(winnerString) && btn8.getText().equals(winnerString) && btn9.getText().equals(winnerString) ||
                     // first diagonal
                        btn1.getText().equals(winnerString) && btn5.getText().equals(winnerString) && btn9.getText().equals(winnerString) ||
                     // second diagonal
                        btn3.getText().equals(winnerString) && btn5.getText().equals(winnerString) && btn7.getText().equals(winnerString)) {
                    winnerText.setText(winnerString + "Wins");
                } else if (counter >= 9){
                    winnerText.setText("Draw... ");
                }
            }
        });
    }
   
}
