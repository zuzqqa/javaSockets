package org.example.patterns;

import org.example.PatternMatcher;

public class GetPatternMatcher implements PatternMatcher {
    @Override
    public boolean matches(String input){
        String pattern = "^GET\\s+/(\\S+)\\s+HTTP/1\\.1$";

        return input.matches(pattern);
    }
}
