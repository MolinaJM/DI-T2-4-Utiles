package com.javafx.cargayconversionb64;

import java.awt.image.BufferedImage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javax.imageio.ImageIO;

/**
 *
 * @author JMMolina
 */
public class ResizerImagenyConversionB64 extends Application {

    private TextArea textArea;
    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Carga Imagen");
        textArea = new TextArea();
        imageView = new ImageView();

        button.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imágenes",
                            "*.jpg", "*.png")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                try {
                    Image originalImage = new Image(selectedFile.toURI().toString());
                    long originalSize = selectedFile.length();
                    //Redimensionamos a 150x150 por ejemplo
                    Image resizedImage = new Image(originalImage.getUrl(), 150, 150, true, true);
                    imageView.setImage(resizedImage);

                    //Se realizan conversiones para usar b64:resizedImage->BufferedImage->baos->png
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(resizedImage, null);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", baos);//Convierte a PNG de forma temporal
                    //Convertimos a byte
                    byte[] imageBytes = baos.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    //Mostramos resultados
                    textArea.setWrapText(true);
                    textArea.setText("Tamaño original: " + originalSize + " bytes\n"
                            + "Tamaño tras resize+b64: " + base64Image.length() + " bytes\n"
                            + "Imagen en base64: " + base64Image);

                    //Por último, copiamos al Portapapeles
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(base64Image);
                    clipboard.setContent(content);

                } catch (IOException e) {
                }
            }
        });

        VBox root = new VBox(button, imageView, textArea);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Convertidor de Imágenes a Base64");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
