package bloque2.autocompletado; //Modificar al package correcto

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Filtered Combo
 *
 * @author JMMolina
 */
public class EjemploFilteredCombo_01_ extends Application {

    private ObservableList<String> series = FXCollections.observableArrayList(
            "Breaking Bad", "Game of Thrones", "Friends", "Stranger Things", "The Crown", "The Office",
            "The Mandalorian", "The Witcher", "Sherlock", "The Big Bang Theory",
            "The Walking Dead", "Mindhunter", "Black Mirror", "Narcos", "Westworld", "Chernobyl", "Fargo",
            "The Handmaid's Tale", "The Simpsons", "Peaky Blinders",
            "Better Call Saul", "Lost", "Dexter", "Vikings", "True Detective", "BoJack Horseman", "Suits",
            "Stranger Things", "House of Cards", "Homeland",
            "The Haunting of Hill House", "The Marvelous Mrs. Maisel", "Brooklyn Nine-Nine", "The Umbrella Academy",
            "The Expanse", "The Good Place", "The West Wing",
            "The X-Files", "The Sopranos", "The Wire", "Firefly", "The IT Crowd", "The Leftovers", "Twin Peaks",
            "Sons of Anarchy", "Avatar: The Last Airbender",
            "The Bridge (Bron/Broen)", "The 100", "Mad Men", "Doctor Who", "Ozark", "The Boys", "The Mentalist",
            "Prison Break", "Six Feet Under", "Dark");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // FilteredList filtra dinámicamente un OL.
        // El predicado (p) es una condición que indica si un valor debe mostrarse en la
        // lista filtrada o no
        // Inicialmente se pone a p->true (muestra todo)
        // https://javadoc.scijava.org/JavaFX25/javafx.base/javafx/collections/transformation/FilteredList.html
        FilteredList<String> filteredItems = new FilteredList<>(series, p -> true);

        ComboBox<String> comboBox = new ComboBox<>(filteredItems);
        comboBox.setEditable(true);

        // El editor me permite personalizar lo que se escribe en el combo y cómo
        // funciona
        TextField txtCombo = comboBox.getEditor();

        // Eventos, primero se procesa el EventFilter y luego el Listener

        // Añadimos un EventFilter para interceptar el evento antes de que el componente
        // lo procese: evita que el espacio lo tome como entrada vacía para el filtro y
        // salte una excepción)
        txtCombo.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (event.getCharacter().equals(" ")) {
                event.consume(); // bloquea que se escriba el espacio
            }
        });

        // Metemos un listener a la propiedad del texto para que detecte cambios
        // conforme vamos escribiendo
        txtCombo.textProperty().addListener((obs, oldValue, newValue) -> {
            final String selected = comboBox.getSelectionModel().getSelectedItem();

            // Si el campo está vacío, oculta y restaura el filtro
            if (newValue == null || newValue.trim().isEmpty()) {
                filteredItems.setPredicate(p -> true);
                comboBox.hide();// ocultamos combo
                return;
            }

            // Si no hay nada seleccionado en el combo y he escrito algo comienza el filtrado
            if (selected == null || !selected.equals(newValue)) {
                // Se aplica CONDICIÓN a STRING puede ser:
                // contiene, empieza por (startsWith), acaba en (endsWith)...
                // filteredItems.setPredicate(p ->
                // p.toLowerCase().contains(newValue.toLowerCase()));
                filteredItems.setPredicate(p -> p.toLowerCase().startsWith(newValue.toLowerCase().trim()));
                comboBox.setVisibleRowCount(5);
                comboBox.show();
            } else {
                filteredItems.setPredicate(p -> true);
            }
        });

        // Ahora indicamos que cuando se seleccione algo, coloque el CARET al final para
        // poder borrarlo fácilmente (Caret)
        /*
         * Además, podemos utilizar este mismo evento para hacer que me devuelva un dato
         * asociado al combo
         * pero no el que está mostrando, lo vemos en el siguiente proyecto.
         */
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCombo.positionCaret(txtCombo.getText().length());
            }
        });

        VBox vbox = new VBox(10, comboBox);
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}
