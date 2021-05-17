package ba.unsa.etf.rs.zadaca3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.events.MouseEvent;

import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public Button btnObris;
    public Slider sliderGodinaRodjenja;
    public CheckBox cbAdmin;

    private KorisniciModel model;

    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }

    public KorisnikController() {

    }



    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if( newKorisnik instanceof Administrator){
                cbAdmin.setSelected(true);
            }
            else{
                cbAdmin.setSelected(false);
            }


            model.setTrenutniKorisnik(newKorisnik);
            listKorisnici.refresh();


        });

        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty());
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty());
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty());
                sliderGodinaRodjenja.valueProperty().unbindBidirectional(oldKorisnik.godinaRodjenjaProperty());
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty());
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty());




            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                fldPasswordRepeat.setText("");

            } else {
                fldIme.textProperty().bindBidirectional(newKorisnik.imeProperty());
                fldPrezime.textProperty().bindBidirectional(newKorisnik.prezimeProperty());
                fldEmail.textProperty().bindBidirectional(newKorisnik.emailProperty());
                sliderGodinaRodjenja.valueProperty().bindBidirectional(newKorisnik.godinaRodjenjaProperty());
                fldUsername.textProperty().bindBidirectional(newKorisnik.usernameProperty());
                fldPassword.textProperty().bindBidirectional(newKorisnik.passwordProperty());

                fldPasswordRepeat.textProperty().bindBidirectional(newKorisnik.passwordProperty());
                fldPasswordRepeat.textProperty().unbindBidirectional((newKorisnik.passwordProperty()));


            }
        });
//
        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            String pattern = "^[a-zA-Z\\s\\-]*$";
            Pattern r = Pattern.compile(pattern);

            Matcher m = r.matcher(newIme);


            if (!newIme.isEmpty() && newIme.length() >= 3 && m.find()) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {

            String pattern = "^[a-zA-Z\\s\\-]*$";
            Pattern r = Pattern.compile(pattern);

            Matcher m = r.matcher(newIme);
            if (!newIme.isEmpty() && newIme.length() >= 3 && m.find()) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {

            String pattern = ".+@.+?$";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(newIme);
            if (!newIme.isEmpty() && m.find()) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {

            String pattern = "^[a-zA-Z_$][a-zA-Z_$0-9]*$";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(newIme);
            if (!newIme.isEmpty() && newIme.length() <= 16 && m.find()) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
           Korisnik korisnik = new Korisnik("","","","",newIme);
            if(cbAdmin.isSelected()){
                korisnik = new Administrator("","","","",newIme);
            }

            if (korisnik.checkPassword()) {

                    fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPassword.getStyleClass().add("poljeIspravno");
                    fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeIspravno");
                } else {
                    fldPassword.getStyleClass().removeAll("poljeIspravno");
                    fldPassword.getStyleClass().add("poljeNijeIspravno");
                    fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
                }

        });




        fldPasswordRepeat.textProperty().addListener((obs, oldIme, newIme) -> {
            Korisnik korisnik = new Korisnik("","","","",newIme);
            if(cbAdmin.isSelected()){
                korisnik = new Administrator("","","","",newIme);
            }
            if (!newIme.isEmpty() && newIme.equals(fldPassword.getText()) && korisnik.checkPassword()) {

                   fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                   fldPassword.getStyleClass().add("poljeIspravno");
                    fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeIspravno");

                } else {

                   fldPassword.getStyleClass().removeAll("poljeIspravno");
                   fldPassword.getStyleClass().add("poljeNijeIspravno");
                    fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");

                }



        });

    }

    public void dodajAction(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik("", "", "", "", ""));
        listKorisnici.getSelectionModel().selectLast();


    }

    public void obrisiAction(ActionEvent actionEvent) {
        listKorisnici.getItems().removeAll(listKorisnici.getSelectionModel().getSelectedItems());

    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public static String generateRandomPassword(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chars2="abcdefghijklmnopqrstuvwxyz";
        String chars3 = "0123456789";
        String chars4 ="@#$%^&+=*;.<>()ß¤×÷¸¸¨˘°°!:˛`˙˘ˇ~/";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        int duzina = 8;

        for( int i =0 ; i< duzina ; i++){


               int randomIndex = random.nextInt(chars.length());
               sb.append(chars.charAt(randomIndex));

               int randomIndex2 = random.nextInt(chars2.length());
               sb.append(chars2.charAt(randomIndex2));

               int randomIndex3 = random.nextInt(chars3.length());
               sb.append(chars3.charAt(randomIndex3));

               int randomIndex4 = random.nextInt(chars4.length());
               sb.append(chars4.charAt(randomIndex4));

        }


        return sb.toString();

    }


    public void dugmegenerisi(ActionEvent actionEvent) {

        String ime = fldIme.getText();
        fldIme.setText(ime);
        String prezime = fldPrezime.getText();
        fldPrezime.setText(prezime);

        String newime = ime.split("")[0].toLowerCase();
        String newprezime = prezime.toLowerCase();


        String rezultat = newime + newprezime;

        for (int i = 0; i < rezultat.length(); i++) {
            String podijeljenistring = rezultat.split("")[i];
            if (podijeljenistring.equals("ž")) {
                char[] qwer = rezultat.toCharArray();
                qwer[i] = 'z';
                rezultat = String.valueOf(qwer);
            }
            if (podijeljenistring.equals("đ")) {
                char[] qwer = rezultat.toCharArray();
                qwer[i] = 'd';
                rezultat = String.valueOf(qwer);
            }
            if (podijeljenistring.equals("č")) {
                char[] qwer = rezultat.toCharArray();
                qwer[i] = 'c';
                rezultat = String.valueOf(qwer);
            }
            if (podijeljenistring.equals("ć")) {
                char[] qwer = rezultat.toCharArray();
                qwer[i] = 'c';
                rezultat = String.valueOf(qwer);
            }
            if (podijeljenistring.equals("š")) {
                char[] qwer = rezultat.toCharArray();
                qwer[i] = 's';
                rezultat = String.valueOf(qwer);
            }

        }

        fldUsername.setText(rezultat);


        String pass = generateRandomPassword();

        String result = pass.substring(0, pass.length() - 24);
        fldPassword.setText(result);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Vaša lozinka glasi: " + fldPassword.getText());
        alert.showAndWait();




    }



    public void chekiraj (ActionEvent actionEvent) {
        Korisnik k = listKorisnici.getSelectionModel().getSelectedItem();

        if (!cbAdmin.isSelected()) {



            if (k != null) {
                Korisnik kor = new Korisnik(k.getIme(), k.getPrezime(), k.getEmail(), k.getUsername(), k.getPassword());
                model.getKorisnici().remove(k);
                model.getKorisnici().add(kor);
                model.setTrenutniKorisnik(kor);
            }


        } else {


            if (k != null) {
                Korisnik kor = new Administrator(k.getIme(), k.getPrezime(), k.getEmail(), k.getUsername(), k.getPassword());
                model.getKorisnici().add(kor);
                model.setTrenutniKorisnik(kor);

            }


        }

    }
}












