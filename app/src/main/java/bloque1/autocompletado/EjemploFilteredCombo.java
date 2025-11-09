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

/**
 * Filtered Combo
 *
 * @author JMMolina
 */
public class EjemploFilteredCombo extends Application {

    private ObservableList<String> series = FXCollections.observableArrayList(
            "Breaking Bad", "Game of Thrones", "Friends", "Stranger Things", "The Crown", "The Office", "The Mandalorian", "The Witcher", "Sherlock", "The Big Bang Theory",
            "The Walking Dead", "Mindhunter", "Black Mirror", "Narcos", "Westworld", "Chernobyl", "Fargo", "The Handmaid's Tale", "The Simpsons", "Peaky Blinders",
            "Better Call Saul", "Lost", "Dexter", "Vikings", "True Detective", "BoJack Horseman", "Suits", "Stranger Things", "House of Cards", "Homeland",
            "The Haunting of Hill House", "The Marvelous Mrs. Maisel", "Brooklyn Nine-Nine", "The Umbrella Academy", "The Expanse", "The Good Place", "The West Wing",
            "The X-Files", "The Sopranos", "The Wire", "Firefly", "The IT Crowd", "The Leftovers", "Twin Peaks", "Sons of Anarchy", "Avatar: The Last Airbender",
            "The Bridge (Bron/Broen)", "The 100", "Mad Men", "Doctor Who", "Ozark", "The Boys", "The Mentalist", "Prison Break", "Six Feet Under", "Dark"
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //FilteredList filtra dinámicamente un OL. 
        //El predicado (p) es una condición que indica si un valor debe mostrarse en la lista filtrada o no
        //Inicialmente se pone a p->true (muestra todo)
        FilteredList<String> filteredItems = new FilteredList<>(series, p -> true);

        ComboBox<String> comboBox = new ComboBox<>(filteredItems);
        comboBox.setEditable(true);
        //Añadimos un EventFilter para evitar que el espacio lo tome como Acción (y seleccione)

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

        //Ahora indicamos que cuando se seleccione algo, se coloque al final para poder borrarlo fácilmente (Caret)
        /*Además, podemos utilizar este mismo evento para hacer que me devuelva un dato asociado al combo 
        pero no el que está mostrando, lo vemos en el siguiente proyecto.
         */
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCombo.positionCaret(txtCombo.getText().length()); //Se posiciona al final para poder borrar fácilmente
                //Integer id=Series(comboBox.getSelectedIndex()).getDNI(); //Uso el getter del objeto seleccionado
            }
        });

        VBox vbox = new VBox(10, comboBox);
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}
