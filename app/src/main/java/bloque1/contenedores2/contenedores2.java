/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bloque1.contenedores2;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * 
 * TitledPane: Representa el USS Enterprise, con una breve descripción.
ButtonBar: Contiene botones para "Engage" y "Warp Speed", evocando comandos del Capitán Picard.
DialogPane: Un cuaderno de bitácora del capitán con información expandible.
FlowPane: Incluye botones para personajes icónicos como Spock, McCoy y Scotty.
TextFlow: Muestra una cita famosa de la introducción de Star Trek.
TilePane: Incluye botones que representan herramientas y dispositivos típicos de la serie, como Phaser, Tricorder, Communicator y Transporter.
SplitPane: Organiza todos los contenedores en una disposición horizontal
 * 
 * @author JMMolina
 */


public class contenedores2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        //TitledPane: se puede expandir para mostrar info extra y contraer (inicialmente colapsado 
        //para que el usuario lo expanda)
        //Se suelen usar como hijos del Nodo Accordion
        TitledPane titledPane = new TitledPane();
        titledPane.setText("USS Enterprise (TITLED PANE)");
        titledPane.setContent(new Label("The starship of Captain Kirk and his crew."));//Acepta cualquier tipo de nodo como hijo
        titledPane.setExpanded(false);//Inicialmente colapsado

        //ButtonBar: organiza botones de forma uniforme, es una especie de Toolbar pero SOLO para botones
        //La única diferencia destacable es ButtonData, que reorganiza botones según "convenciones" del SSOO
        //Windows: [Cancelar] [Aceptar] // Linux/MACOS [Cancelar Aceptar]
        ButtonBar buttonBar = new ButtonBar();
        Button engageButton = new Button("Engage");
        engageButton.setOnAction(e -> System.out.println("Engage!!"));
        Button warpButton = new Button("Warp Speed");
        warpButton.setOnAction(e -> System.out.println("Warp Speed!!"));
        buttonBar.getButtons().addAll(new Label("BUTTON BAR"), engageButton, warpButton);
        buttonBar.setButtonData(engageButton, ButtonData.OK_DONE);
        buttonBar.setButtonData(warpButton, ButtonData.CANCEL_CLOSE);


        //DialogPane: expande diálogo con info adicional. Podemos meter cualquier nodo dentro.
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText("Captain's Log (DIALOG PANE)");
        dialogPane.setExpandableContent(new Label("Date: Stardate 41153.7. Reporting on the latest mission updates..."));

        //FlowPane: ajusta horizontal o verticalmente Nodos (se ajusta automáticamente). Es como un VBOX o HBOX
        //que se redimensiona automáticamente si le falta espacio.
        //Perfecto para interfaces que necesitan adaptarse dinámicamente, como galerías de imágenes 
        //o etiquetas que deben reorganizarse automáticamente si cambia el tamaño de la ventana.
        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.HORIZONTAL);//Rellena en horizontal preferiblemente
        flowPane.getChildren().addAll(
                new Label("FLOW PANE"),
                new Button("Spock"),
                new Button("McCoy"),
                new Button("Scotty")
        );
        // Agregar más botones dinámicamente para ver cómo se comportan ante un redimensionamiento
        Button addButton = new Button("Añadir Button");
        addButton.setOnAction(e -> flowPane.getChildren().add(new Button("NUEVO!")));
        flowPane.getChildren().add(addButton);

        // TextFlow: nodos de texto para que "fluyan" uno detrás del otro, es decir, el texto
        //se ajusta al ancho del contenedor. Es una especie de VBOX pero que se redimensiona
        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().addAll(
                new Text("TEXTFLOW\n"),
                new Text("Space: the final frontier. "),
                new Text("These are the voyages \nof the starship Enterprise. "),
                new Text("Its continuing mission:\n to explore strange new worlds...")
        );
        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                new Text("VBOX"),
                new Text("Space: the final frontier. "),
                new Text("These are the voyages \nof the starship Enterprise. "),
                new Text("Its continuing mission:\n to explore strange new worlds...")
        );

        // TilePane: dispone nodos en cuadrícula, los nodos se ajustan UNIFORMEMENTE (diferencia con FLOWPANE) 
        // Se usa para interfaces muy estructuradas, como tableros con iconos o paneles de control. Es una especie
        // de GridPane pero que se redimensiona automáticamente
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(2); //Preferencias: las intenta respetar.
        tilePane.setPrefRows(3); //Preferencias, si hay más filas no las evita!
        tilePane.getChildren().addAll(
                new Label("TILEPANE"),
                new Button("Phaser"),
                new Button("Tricorder"),
                new Button("Communicator"),
                new Button("Transporter")
        );
        // Añadir botones dinámicamente
        Button addTileButton = new Button("AÑADIR Tile");
        addTileButton.setOnAction(e -> tilePane.getChildren().add(new Button("New Tile")));
        tilePane.getChildren().add(addTileButton);

        // SplitPane1
        SplitPane splitPane1 = new SplitPane();
        splitPane1.setOrientation(Orientation.HORIZONTAL);
        splitPane1.setPrefHeight(300);//Altura preferida
        splitPane1.getItems().addAll(titledPane, buttonBar, dialogPane); //Reparte espacio

        // SplitPane2
        SplitPane splitPane2 = new SplitPane();
        splitPane2.setOrientation(Orientation.HORIZONTAL);
        splitPane2.setPrefHeight(300);//Altura preferida
        splitPane2.setDividerPositions(0.3, 0.5, 0.6); // Son % donde se colocan los separadores
        splitPane2.getItems().addAll(flowPane, vbox, textFlow, tilePane);

        Scene scene = new Scene(new VBox(new HBox(splitPane1), new HBox(splitPane2)), 800, 600);
        primaryStage.setTitle("Star Trek JavaFX - Contenedores 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


