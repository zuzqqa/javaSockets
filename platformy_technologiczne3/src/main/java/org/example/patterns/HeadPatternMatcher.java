package org.example.patterns;

import org.example.PatternMatcher;

public class HeadPatternMatcher implements PatternMatcher {
    @Override
    public boolean matches(String input) {
        String pattern = "HEAD /\\w+ HTTP/1\\.\\d";

        return input.matches(pattern);
    }
}
