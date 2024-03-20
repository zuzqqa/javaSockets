package org.example.patterns;

import org.example.PatternMatcher;

public class DeletePatternMatcher implements PatternMatcher {
    @Override
    public boolean matches(String input) {
        String pattern = "DELETE /\\w+/\\d+ HTTP/1\\.\\d";

        return input.matches(pattern);
    }
}
