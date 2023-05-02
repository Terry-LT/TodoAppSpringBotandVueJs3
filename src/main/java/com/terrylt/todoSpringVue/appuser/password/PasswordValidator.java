package com.terrylt.todoSpringVue.appuser.password;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class PasswordValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //password should contain more than 8 symbols, at least 2 numbers
        if (s.length() > 8){
            int digits = 0;
            for (char c : s.toCharArray()){
                if (Character.isDigit(c)){
                    digits += 1;
                }
            }
            return digits >= 2;
        }
        return false;
    }
}
