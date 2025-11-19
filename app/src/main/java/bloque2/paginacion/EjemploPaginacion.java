package bloque2.paginacion;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author JMMolina
 */
public class EjemploPaginacion extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App - Paginador");

        List<String> cuentos = new ArrayList<>(); //No es necesario un OL
        cuentos.add("Cuento 1: La leyenda de la Alhambra.\n"
                + "Cuenta la historia que en lo alto de la colina de la Sabika, el sultán Boabdil contemplaba "
                + "por última vez la Alhambra. Su madre, con lágrimas en los ojos, le dijo: 'Llora como mujer "
                + "lo que no supiste defender como hombre'. Desde entonces, en las noches de luna llena, "
                + "dicen que el eco de su llanto aún resuena por los muros de este majestuoso palacio.");

        cuentos.add("Cuento 2: El Albaicín y sus calles encantadas.\n"
                + "En el barrio del Albaicín, un laberinto de calles estrechas y empedradas, se dice que al "
                + "caer la noche, las sombras de antiguos moriscos pasean por las callejuelas, susurrando "
                + "secretos olvidados. Los vecinos afirman haber visto luces misteriosas danzando sobre las "
                + "azoteas, guiando a los viajeros hacia rincones escondidos llenos de magia.");

        cuentos.add("Cuento 3: El misterio del Sacromonte.\n"
                + "En las cuevas del Sacromonte, donde el flamenco resuena en cada rincón, una vieja leyenda "
                + "habla de una gitana que podía predecir el futuro. Quienes se atrevieron a buscarla, "
                + "decían que sus predicciones se cumplían siempre, pero solo si bailabas al compás del "
                + "canto gitano bajo la luz de las estrellas.");

        cuentos.add("Cuento 4: El último suspiro del rey.\n"
                + "En la colina conocida como 'El Suspiro del Moro', Boabdil, el último rey nazarí, lloró "
                + "desconsolado al perder su amada ciudad de Granada. Su madre, firme y severa, le reprochó "
                + "su derrota. Dicen que desde aquel día, el viento que sopla en ese lugar lleva consigo "
                + "el eco de su último suspiro, un recordatorio de la caída del reino nazarí.");

        cuentos.add("Cuento 5: El Generalife y los jardines de ensueño.\n"
                + "Los jardines del Generalife, con sus fuentes susurrantes y fragantes flores, fueron "
                + "testigos de los amores prohibidos entre un príncipe y una joven de origen humilde. "
                + "Cada tarde, se encontraban en secreto, entre los rosales y naranjos. Se dice que si te "
                + "sientas en una de las bancas del jardín, puedes escuchar sus voces, aún vivas en el "
                + "murmullo del agua y el aroma de las flores.");

        Pagination pagination = new Pagination();
        pagination.setPageCount(7);//
        pagination.setCurrentPageIndex(0);//Inicial
        pagination.setMaxPageIndicatorCount(3);//Botones en separador

        //Contenido dinámico compartido
        Label tituloCuento = new Label();
        tituloCuento.setFont(new Font("Arial", 24));
        TextArea txtCuento = new TextArea();
        txtCuento.setFont(new Font("Arial", 24));
        txtCuento.setWrapText(true);

        //Cada página se genera dinámicamente
        pagination.setPageFactory(pagina -> {
            tituloCuento.setText("Página Nº "+ pagina);
            //Cambiamos el contenido según la página mediante un rule switch
            // Mostrar el cuento correspondiente a la página
            if (pagina < cuentos.size()) {
                txtCuento.setText(cuentos.get(pagina));
            } else {
                txtCuento.setText("No hay más cuentos!");
            }
            return new VBox(tituloCuento, txtCuento);
        });

        //Usamos stackPane porque con el vBox se ve la "animación" de cambio
        StackPane sP = new StackPane(pagination);
        Scene scene = new Scene(sP, 500, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
