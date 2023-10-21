package com.example.cadastrojavafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Utilizador;
import java.sql.Connection;

import java.net.ConnectException;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

   @FXML
   private TextField nome;

   @FXML
   private TextField idade;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn tableColumnnome;
    @FXML
    private TableColumn tableColumnidade;

    private List<Utilizador> utilizadors=new ArrayList<>();


    @FXML
    protected void onClickCadastrarUtilizador() {

        try {
            String nome = this.nome.getText();
            Integer idade= Integer.valueOf(this.idade.getText());
            Utilizador utilizador= new Utilizador(nome,idade);

            utilizadors.add(utilizador);

            tableColumnnome.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("nome"));
            tableColumnidade.setCellValueFactory(new PropertyValueFactory<Utilizador, Integer>("idade"));

            ObservableList<Utilizador> data = FXCollections.observableArrayList(utilizadors);
            tableView.setItems(data);

            System.out.println("Utilizador: " + utilizador);

        }catch (NumberFormatException ex)
        {
            ex.printStackTrace(); // poderia criar uma label e setar como erro , exemplo essa idade nao e valida
        }
        // Limpar os campos de texto após o cadastro
        this.nome.clear();
        this.idade.clear();
    }
    @FXML
    protected void onTableViewClicked() {


        Utilizador selectedUtilizador = (Utilizador) tableView.getSelectionModel().getSelectedItem();
        if (selectedUtilizador != null) {
            this.nome.setText(selectedUtilizador.getNome());
           this.idade.setText(String.valueOf(selectedUtilizador.getIdade()));
        }
    }

    @FXML
    protected void onEditar() {
        Utilizador selectedUtilizador = (Utilizador) tableView.getSelectionModel().getSelectedItem();
        if (selectedUtilizador != null) {
            String novoNome = nome.getText();
            int novaIdade = Integer.parseInt(idade.getText());

            // Atualizar os valores do item selecionado
            selectedUtilizador.setNome(novoNome);
            selectedUtilizador.setIdade(novaIdade);

            // Atualizar a TableView
            tableView.refresh();
        }

    }
    @FXML
    protected void onApagar() {
        Utilizador selectedUtilizador = (Utilizador) tableView.getSelectionModel().getSelectedItem();
        if (selectedUtilizador != null) {
            utilizadors.remove(selectedUtilizador);
            tableView.getItems().remove(selectedUtilizador);
        }
    }
    @FXML
    protected void onPesquisar() {
        String termoPesquisa = nome.getText().toLowerCase();

        ObservableList<Utilizador> resultadosPesquisa = FXCollections.observableArrayList();

        for (Utilizador utilizador : utilizadors) {
            String nome = utilizador.getNome().toLowerCase();

            if (nome.contains(termoPesquisa)) {
                resultadosPesquisa.add(utilizador);
            }
        }
        this.idade.clear();
        tableView.setItems(resultadosPesquisa);
    }
    @FXML
    protected void limparPesquisa() {
        nome.clear();
        idade.clear();
        tableView.setItems(FXCollections.observableArrayList(utilizadors));
    }
}