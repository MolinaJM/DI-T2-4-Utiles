package com.javafx.autocompletado; //Modificar al package correcto

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Filtered Combo con Modelo
 *
 * @author JMMolina
 */

public class EjemploFilteredCombo_Objetos extends Application {

    private ObservableList<Serie> SeriesBBDD = FXCollections.observableArrayList(
            new Serie("Breaking Bad", 2008),
            new Serie("Game of Thrones", 2011),
            new Serie("Friends", 1994),
            new Serie("Stranger Things", 2016),
            new Serie("The Crown", 2016),
            new Serie("The Office", 2005),
            new Serie("The Mandalorian", 2019),
            new Serie("The Witcher", 2019),
            new Serie("Sherlock", 2010),
            new Serie("The Big Bang Theory", 2007),
            new Serie("The Walking Dead", 2010),
            new Serie("Mindhunter", 2017),
            new Serie("Black Mirror", 2011),
            new Serie("Narcos", 2015),
            new Serie("Westworld", 2016),
            new Serie("Chernobyl", 2019),
            new Serie("Fargo", 2014),
            new Serie("The Handmaid's Tale", 2017),
            new Serie("The Simpsons", 1989),
            new Serie("Peaky Blinders", 2013),
            new Serie("Better Call Saul", 2015),
            new Serie("Lost", 2004),
            new Serie("Dexter", 2006),
            new Serie("Vikings", 2013),
            new Serie("True Detective", 2014),
            new Serie("BoJack Horseman", 2014),
            new Serie("Suits", 2011),
            new Serie("House of Cards", 2013),
            new Serie("Homeland", 2011),
            new Serie("The Haunting of Hill House", 2018),
            new Serie("The Marvelous Mrs. Maisel", 2017),
            new Serie("Brooklyn Nine-Nine", 2013),
            new Serie("The Umbrella Academy", 2019),
            new Serie("The Expanse", 2015),
            new Serie("The Good Place", 2016),
            new Serie("The West Wing", 1999),
            new Serie("The X-Files", 1993),
            new Serie("The Sopranos", 1999),
            new Serie("The Wire", 2002),
            new Serie("Firefly", 2002),
            new Serie("The IT Crowd", 2006),
            new Serie("The Leftovers", 2014),
            new Serie("Twin Peaks", 1990),
            new Serie("Sons of Anarchy", 2008),
            new Serie("Avatar: The Last Airbender", 2005),
            new Serie("The Bridge (Bron/Broen)", 2011),
            new Serie("The 100", 2014),
            new Serie("Mad Men", 2007),
            new Serie("Doctor Who", 1963),
            new Serie("Ozark", 2017),
            new Serie("The Boys", 2019),
            new Serie("The Mentalist", 2008),
            new Serie("Prison Break", 2005),
            new Serie("Six Feet Under", 2001),
            new Serie("Dark", 2017)
    );


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //FilteredList filtra dinámicamente un OL. 
        //El predicado (p) es una condición que indica si un valor debe mostrarse en la lista filtrada o no
        //Inicialmente se pone a p->true (muestra todo)
        ObservableList<String> series = FXCollections.observableArrayList();
        
        //Se rellena el OL que alimenta el combo
        for (int i=0; i<SeriesBBDD.size();i++){
            series.add(SeriesBBDD.get(i).getNombre());
        }
        
        //---------------------------------------------------------------------
        //A partir de aquí es exactamente igual que el proyecto anterior
        
        //Se crea el FilteredList a partir del OL
        FilteredList<String> filteredItems = new FilteredList<>(series, p -> true);

        ComboBox<String> comboBox = new ComboBox<>(filteredItems);
        comboBox.setEditable(true);
       
        //El editor me permite personalizar lo que se escribe en el combo y cómo funciona
        TextField txtCombo = comboBox.getEditor();

        //Metemos un listener a la propiedad del texto para que detecte cambios conforme vamos escribiendo
        txtCombo.textProperty().addListener((obs, oldValue, newValue) -> {
            final String selected = comboBox.getSelectionModel().getSelectedItem();

            //Si no seleccionamos nada en el combo, o si queremos ampliar la selección, establece el nuevo valor
            if (selected == null || !selected.equals(txtCombo.getText())) {
                txtCombo.setText(newValue);
                System.out.println("Viejo: " + oldValue + " Nuevo: " + newValue);
                //Solo filtra al añadir texto
                if (newValue.length() > oldValue.length()) {
                    //Condición puede ser: contiene, empieza por (startsWith), acaba por (endsWith)...
                    //filteredItems.setPredicate(p -> p.toLowerCase().contains(newValue.toLowerCase()));
                    filteredItems.setPredicate(p -> p.toLowerCase().startsWith(newValue.toLowerCase()));
                    comboBox.setVisibleRowCount(5);//Establecemos filas visibles
                    comboBox.show();
                } 
            } else {
                System.out.println("Seleccionada: " + selected);
                filteredItems.setPredicate(p -> true);//Restablece el filtro
            }

        }
        );
        //---------------------------------------------------------------------
        //A partir de aquí hay cambios

        /*Mostramos un valor pero obtenemos otro asociado
        Ejemplo:
        -creamos un OL de objetos Serie (Nombre,Año), quiero seleccionar Nombre pero sacar Año asociado 
        -Primero recorremos OL Serie y vamos rellenando otro OL que alimenta el combo usando 
        series.add(SeriesBBDD.get(i).getNombre());
        -Cuando cambio de selección, para obtener el ID hago lo siguiente:
            el índice del OL del combo me sirve para obtener el índice del objeto en el OL de series, y de ahí saco su ID
         */
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCombo.positionCaret(txtCombo.getText().length()); //Se posiciona al final para poder borrar fácilmente
                Integer id=SeriesBBDD.get(series.indexOf(comboBox.getValue())).getAnyo(); //Uso el getter del objeto seleccionado
                System.out.println("Id de serie es "+id.toString());
            }
        });

        VBox vbox = new VBox(10, comboBox);
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}
