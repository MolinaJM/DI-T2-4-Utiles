package bloque1.componentesbasicos2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * PRUEBA DE COMPONENTES BASICOS PARTE 2
 *
 * @author JMMolina
 */
public class PeliController implements Initializable {

    @FXML
    private Accordion accordion;

    @FXML
    private TextField movieTitle;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Slider ratingSlider;

    @FXML
    private Label ratingLabel;

    @FXML
    private Spinner<Integer> yearSpinner;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Hyperlink movieLink;

    @FXML
    private WebView webView;

    KeyCombination ctrlP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        movieTitle.setText("Inception");
        yearSpinner.getValueFactory().setValue(2010);// Año de la peli, no existe un .setValue directo
        System.out.println(yearSpinner.getValue());

        // Configuramos el contenido del accordion
        TextArea sinopsis = new TextArea(
                "Inception es una película de ciencia ficción y acción que explora el mundo de los sueños y la manipulación mental. La trama se centra en Dom Cobb, un experto ladrón que se adentra en los sueños de las personas para robarles sus secretos más profundos. \n"
                        + "\nArgumento:\nDom Cobb y su equipo de especialistas se dedican a una peligrosa misión: en lugar de robar una idea, deben implantarla en la mente de otra persona. Esta operación, conocida como 'inception', es considerada imposible y extremadamente arriesgada."
                        + "\n\nLa misión de Cobb consiste en infiltrarse en los sueños del heredero de un poderoso magnate para sembrar en su subconsciente la idea de disolver la empresa de su padre. Si tiene éxito, no solo obtendrá su libertad, sino que también podrá volver a ver a sus hijos."
                        + "\n\nA medida que se adentran en los niveles más profundos del sueño, Cobb y su equipo se enfrentan a desafíos cada vez más complejos y peligrosos. Los sueños se vuelven inestables, los límites entre la realidad y la ficción se difuminan, y los enemigos se multiplican.");
        // Creamos hijos de accordion (TitledPane) y los añadimos
        TextArea actores = new TextArea("Leonardo DiCaprio\nJoseph Gordon-Levitt\nEllen Page\n// ... ");
        actores.setEditable(false);
        sinopsis.setEditable(false);
        sinopsis.setWrapText(true); // Encaja las líneas en el contenedor

        // Accordion: es una agrupación de TitledPane. Genera un scrollpane automático.
        TitledPane actoresPane = new TitledPane("Actores", actores);
        TitledPane sinopsisPane = new TitledPane("Sinopsis", sinopsis);
        accordion.getPanes().addAll(actoresPane, sinopsisPane);
        accordion.setMaxHeight(170);

        progressBar.setProgress(0.0);// Por defecto usa valores entre 0 y 1
        // progressIndicator: nos sirve para mostrar progreso o carga indeterminada
        // progressIndicator.isIndeterminate();//Se puede poner por aquí o por SB
        // como cualquier Node, es personalizable (tema 5)
        ratingSlider.setMax(10);
        ratingSlider.setValue(8.8);
        ratingLabel.setText("8.8");

        colorPicker.setValue(Color.BLUE);

        // obs es el el propio valueProperty (para acceso a sus características)
        ratingSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            ratingLabel.setText(String.format("%.1f", newVal));
        });

        colorPicker.setOnAction(e -> {
            //Hacemos algo nuevo, cogemos el parent y lo recorremos para hacer algo con todos sus hijos
            //Accedemos al nodo raíz, hacemos casting y lo recorremos para cambiar "algo"
            Color color = colorPicker.getValue();
            AnchorPane root = (AnchorPane) movieTitle.getScene().getRoot();
            for (Node node : root.getChildren()) {
                node.setStyle("-fx-background-color: " + toRgbString(color) + ";");
            }
        });

        movieLink.setOnAction(e -> {
            WebEngine webEngine = webView.getEngine();
            // Se le indica un UserAgent válido, por si tiene restricciones CORS la web que
            // consultamos
            // El UserAgent es una cadena de texto que identifica al navegador o aplicación
            // que está solicitando una página web al servidor.
            // Ej: UserAgent de Firefox, con compatibilidad para otros:
            webEngine.setUserAgent(
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            webEngine.load("https://www.imdb.com/title/tt1375666/");
            progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);// Ponemos a valor : estado indeterminado

            // Se puede monitorizar el estado de carga del WebEngine
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == State.SUCCEEDED) {
                    System.out.println("Web cargada");
                    progressIndicator.setVisible(false);
                } else if (newState == State.FAILED || newState == State.CANCELLED) {
                    progressIndicator.setVisible(false);
                    System.out.println("Error al cargar la web");
                }
            });

        });

        // Por último, utilizamos una combinación de teclas mediante el objeto
        // KeyCodeCombination
        // Comprobamos el evento en una función de teclas asociada desde SB
        // Ejemplo: en el root (AnchorPane) se asocia al evento OnKeyPressed la llamada a la función checkCombTeclas
        ctrlP = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);

    }

    // Función para de forma dinámica cambiar el estilo a partir de un ColorPicker
    private String toRgbString(Color c) {
        return "rgb(" + (int) (c.getRed() * 255) + "," + (int) (c.getGreen() * 255) + "," + (int) (c.getBlue() * 255)
                + ")";
    }

    @FXML
    public void subeProgress(ActionEvent event) {
        double progress = progressBar.getProgress();
        System.out.println(progress);
        if (progress < 1.0) {
            progress += 0.1;
            progressBar.setProgress(progress);
            progressIndicator.setProgress(progress);
        }
    }

    @FXML
    // Comprobación de todas las combinaciones de teclas
    void checkCombTeclas(KeyEvent event) {
        if (ctrlP.match(event)) {
            subeProgress(null); // Hay que pasarle un evento nulo
        }
    }

}
