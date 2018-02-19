package org.openchat.domain.posts;

import java.util.List;

import static java.util.Arrays.asList;

public class LanguageService {

    private List<String> inappropriateWords = asList("ELEPHANT", "ORANGE", "ICE CREAM");

    public boolean isInappropriate(String text) {
        return inappropriateWords.stream()
                                 .anyMatch(word -> text.toUpperCase().contains(word));
    }
}
