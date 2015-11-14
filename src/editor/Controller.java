package editor;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Controller {
    public TextArea text;
    public MenuItem miOT12;
    public MenuItem miOT10;
    public MenuItem miFSortir;
    public MenuItem miECopiar;
    public MenuItem miETallar;
    public MenuItem miEPaste;
    public MenuItem miEUndo;
    public Button btCopy;
    public Button btCut;
    public Button btPaste;
    public MenuItem miAAbout;
    public AnchorPane mainPane;
    public CheckMenuItem cmiOFSans;
    public CheckMenuItem cmiOFFree;
    public MenuItem miCCopiar;
    public MenuItem miCTallar;

    /**
     * S'executa al iniciar l'aplicació.
     */
    public void initialize(){
        btCopy.setGraphic(new ImageView("copy.png"));
        btCut.setGraphic(new ImageView("cut.png"));
        btPaste.setGraphic(new ImageView("paste.png"));
        cmiOFSans.setSelected(true);
    }

    /**
     * Menú Opcions -> Font. Canvi de la font del text.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void setFont(ActionEvent actionEvent) {
        CheckMenuItem item = (CheckMenuItem) actionEvent.getSource();
        cmiOFFree.setSelected(false);
        cmiOFSans.setSelected(false);
        item.setSelected(true);
        text.setFont(new Font(item.getText(), text.getFont().getSize()));
    }

    /**
     * Menú Opcions -> Tamany. Canvi del tamany de la font del text.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void setFontSize(ActionEvent actionEvent) {
        double fontSize = Double.parseDouble(((MenuItem) actionEvent.getSource()).getText());
        text.setFont(new Font(fontSize));
    }

    /**
     * Menú Fitxer -> Sortir. Surt de l'aplicació
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void sortir(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Menú Editar -> Copiar. Còpia el text seleccionat.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void eCopiar(ActionEvent actionEvent) {
        text.copy();
    }

    /**
     * Menú Editar -> Tallar. Talla el text seleccionat.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void eTallar(ActionEvent actionEvent) {
        text.cut();
    }

    /**
     * Menú Editar -> Enganxar. Enganxa el text del clipboard.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void ePaste(ActionEvent actionEvent) {
        text.paste();
    }

    /**
     * Menú Editar -> Desfer. Desfà la última acció.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void eUndo(ActionEvent actionEvent) {
        text.undo();
    }

    /**
     * Menú Ajuda -> About. Mostra un diàleg modal amb info de la app.
     *
     * @param actionEvent Event onAction del MenuItem
     */
    public void eAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cutreditor");
        alert.setHeaderText("Un editor de text cutre!");
        alert.setContentText("Fet durant el curs 2015-2016.");

        alert.showAndWait();
    }

    /**
     * Deshabilita els items Copiar i Tallar del menú Editar si no hi ha text seleccionat.
     *
     * @param event Event onShowing del menú Editar.
     */
    public void deshabilitarCTEd(Event event) {
        if (text.getSelectedText().equals("")) {
            miECopiar.setDisable(true);
            miETallar.setDisable(true);
        } else {
            miECopiar.setDisable(false);
            miETallar.setDisable(false);
        }
    }

    /**
     * Deshabilita els items Copiar i Tallar del menú contextual si no hi ha text seleccionat.
     *
     * @param event Event onShowing del menú Editar.
     */
    public void deshabilitarCTCont(Event event) {
        if (text.getSelectedText().equals("")) {
            miCCopiar.setDisable(true);
            miCTallar.setDisable(true);
        } else {
            miCCopiar.setDisable(false);
            miCTallar.setDisable(false);
        }
    }

    public void setStageTitle(String newTitle){ // Método que nos cambiara el stage en el Main controller
        Main.getStage().setTitle(newTitle /*+ "  Editor cutre"*/); // Es necesario porque no se puede referenciar en un contexto éstatico directamente
    }

    /**
     * Obre un arxiu determinat amb extensio .txt i .java
     * @param actionEvent
     */
    public void eObrir(ActionEvent actionEvent) {
        Stage mainStage = new Stage();  //Creamos el main stage

        FileChooser chooser = new FileChooser();    //Creamos el dialog
        chooser.setTitle("Obrir arxiu");            //Con el nombre setArxiu

        chooser.getExtensionFilters().addAll(   //Definimos los archivos que ha de leer
                new FileChooser.ExtensionFilter("Axius de text", "*.txt"),
                new FileChooser.ExtensionFilter("Axius java", "*.java"),
                new FileChooser.ExtensionFilter("Tots els arxius", "*.*"));



        try{
            File selectedFile = chooser.showOpenDialog(mainStage);  //selectedFile será el archivo que escojamos en el dialog

            //setStageTitle(selectedFile.getName());
            chooser.setTitle(selectedFile.getName());


            BufferedReader br = new BufferedReader(new FileReader(selectedFile));
            while(br.ready()){
                text.setText(text.getText().toString() + br.readLine() + "\n");
            }


        }
        catch(FileNotFoundException a){
            a.printStackTrace();
        }
        catch (IOException b){
            b.printStackTrace();
        }
    }

    /**
     * Guarda el contingut de l'editor de text i el
     * guarda en un arxiu que denomini l'usuari
     * @param actionEvent
     * @throws IOException
     */
    public void eGuardar(ActionEvent actionEvent) throws IOException, SecurityException {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Guardar arxiu");
        Stage mainStage = new Stage();
        File selectedFile = chooser.showSaveDialog(mainStage);

        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Axius de text", "*.txt"),
                new FileChooser.ExtensionFilter("Axius java", "*.java"),
                new FileChooser.ExtensionFilter("Tots els arxius", "*.*"));

        chooser.setTitle(selectedFile.getName()); // El nombre la pantalla cambiará al del archivo
        selectedFile.createNewFile();
        /*
        if(!selectedFile.exists()){     //Si el archivo no existe que lo cree. Codi per a futura ampliació
            CrearFitxer.crea(selectedFile);
        }*/

        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile));
            bw.write(text.getText());  //Leemos el texto del archivo seleccionado y lo aplicamos a nuestro programa y cerramos
            bw.close();
        }catch(FileNotFoundException a){
            a.printStackTrace();
        }
        catch (IOException b){
            b.printStackTrace();
        }

    }
}
//http://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm