package ba.unsa.etf.rs.zadaca3;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Administrator extends Korisnik {

    public Administrator(String ime, String prezime, String email, String username, String password) {
        super(ime, prezime, email, username, password);
    }

    @Override
    public boolean checkPassword(){

        String pass = super.getPassword();
        String pattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*?[0-9])(?=.*[@#$%^&+=*;.<>()ß¤×÷¸¸¨˘°°!:˛`˙˘ˇ~/])";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(pass);

        return m.find();
    }
}
