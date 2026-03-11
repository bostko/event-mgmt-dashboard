package com.valentin.app;

import com.valentin.list.LinkedList;

import static com.valentin.utilities.StringUtils.join;
import static com.valentin.utilities.StringUtils.split;
import static com.valentin.app.MessageUtils.getMessage;

import org.apache.commons.text.WordUtils;

public class App {
    public static void main(String[] args) {
        LinkedList tokens;
        tokens = split(getMessage());
        String result = join(tokens);
        System.out.println(WordUtils.capitalize(result));
    }
}
