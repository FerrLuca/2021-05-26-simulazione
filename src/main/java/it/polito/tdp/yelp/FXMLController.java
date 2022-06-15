/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private final Integer[] years = { 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 };

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="btnLocaleMigliore"
	private Button btnLocaleMigliore; // Value injected by FXMLLoader

	@FXML // fx:id="btnPercorso"
	private Button btnPercorso; // Value injected by FXMLLoader

	@FXML // fx:id="cmbCitta"
	private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

	@FXML // fx:id="txtX"
	private TextField txtX; // Value injected by FXMLLoader

	@FXML // fx:id="cmbAnno"
	private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

	@FXML // fx:id="cmbLocale"
	private ComboBox<Business> cmbLocale; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaPercorso(ActionEvent event) {

	}

	@FXML
	void doCreaGrafo(ActionEvent event) {
		txtResult.clear();
		Integer n = cmbAnno.getValue();
		if (n == null) {
			txtResult.appendText("Seleziona un anno!");
			return;
		}

		String city = cmbCitta.getValue();
		if (city == null) {
			txtResult.appendText("Seleziona una città");
			return;
		}

		model.creaGrafo(city, n);
		
		txtResult.appendText("Grafo creato!");
		txtResult.appendText("\n#V: " + model.getVertici().size());
		txtResult.appendText("\n#A: " + model.getArchi().size());
		
	}

	@FXML
	void doLocaleMigliore(ActionEvent event) {
		txtResult.clear();
		Business b = model.bestBusiness();
		txtResult.appendText("LOCALE MIGLIORE: " + b.getBusinessName());
		
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnLocaleMigliore != null
				: "fx:id=\"btnLocaleMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbLocale != null : "fx:id=\"cmbLocale\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
	}

	public void setModel(Model model) {
		this.model = model;
		cmbAnno.getItems().addAll(years);
		cmbCitta.getItems().addAll(model.getCities());
	}
}
