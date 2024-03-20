package org.example.patterns;

import org.example.PatternMatcher;

public class OptionsPatternMatcher implements PatternMatcher {
    @Override
    public boolean matches(String input){
        String pattern = "OPTIONS /\\w+ HTTP/1\\.\\d";

        return input.matches(pattern);
    }
}
