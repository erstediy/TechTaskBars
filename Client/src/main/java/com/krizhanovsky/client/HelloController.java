package com.krizhanovsky.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krizhanovsky.client.entity.Contract;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class HelloController implements Initializable {

    private static HttpURLConnection conn;

    @FXML
    private TableColumn<Contract,CheckBox> ActualCol;

    @FXML
    private Button AddButton;

    @FXML
    private TableColumn<Contract,String> DateOfCreationCol;

    @FXML
    private TableColumn<Contract,Long> IDCol;

    @FXML
    private TextField InsertNum;

    @FXML
    private TableColumn<Contract,String> NumCol;

    @FXML
    private TableView<Contract> Table;

    @FXML
    private Button UpdateButton;

    @FXML
    private TableColumn<Contract,String> UpdateDateCol;

    @FXML
    void Add(ActionEvent event) {
        Contract contract = new Contract();
        contract.setNumber(InsertNum.getText());
        contract.setDateOfCreation(new Date());
        contract.setUpDate(new Date());


        try {
            URL url = new URL("http://localhost:8080/contracts");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(contract);


            // For POST only - START
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(json);
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("POST request not worked");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tableAutoReload();
    }

    @FXML
    void Reload(ActionEvent event) {
        tableAutoReload();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableAutoReload();
    }

    void tableAutoReload() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));

        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            URL url = new URL("http://localhost:8080/contracts");
            conn = (HttpURLConnection) url.openConnection();

            // Request setup
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
            conn.setReadTimeout(5000);

            // Test if the response from the server is successful
            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }


        ObservableList<Contract> contractList = FXCollections.observableArrayList(parse(responseContent.toString()));
        Table.setItems(contractList);
        IDCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        NumCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getNumber()));
        DateOfCreationCol.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(dateFormat.format(c.getValue().getDateOfCreation())));

        UpdateDateCol.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(dateFormat.format(c.getValue().getUpDate())));

        ActualCol.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getActual()));
    }
    public static List<Contract> parse(String json){
        List<Contract> contracts;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
             contracts = objectMapper.readValue(json, new TypeReference<>() {
             });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return contracts;
    }
}
