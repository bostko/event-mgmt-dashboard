package com.valentin.be;

import static com.valentin.utilities.JoinUtils.join;
import static com.valentin.utilities.SplitUtils.split;
import static com.valentin.be.MessageUtils.getMessage;

import org.apache.commons.text.WordUtils;

public class App {
    public static void main(String[] args) {
        var tokens = split(getMessage());
        String result = join(tokens);
        System.out.println(WordUtils.capitalize(result));
    }
}
