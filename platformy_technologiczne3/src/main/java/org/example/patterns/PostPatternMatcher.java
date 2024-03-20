package org.example.patterns;

import org.example.PatternMatcher;

public class PostPatternMatcher implements PatternMatcher {
    @Override
    public boolean matches(String input) {
        String pattern = "POST /\\w+ HTTP/1\\.\\d";

        return input.matches(pattern);
    }
}
