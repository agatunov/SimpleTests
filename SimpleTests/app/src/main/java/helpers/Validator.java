package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    // Правила валидации различных полей
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String LOGIN_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";

    // Валидация логина
    public static boolean loginValidate(String login)
    {
        Pattern pattern = Pattern.compile(LOGIN_PATTERN);
        Matcher matcher = pattern.matcher(login);
        return  matcher.matches();
    }
    // Валидация пароля
    public static boolean passwordValidate(String password)
    {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return  matcher.matches();
    }
    // Валидация почты
    public static boolean emailValidate(String login)
    {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }
    public static boolean registrationValidate(String login, String password, String email)
    {
        return loginValidate(login) && passwordValidate(password) && emailValidate(email);
    }
}
