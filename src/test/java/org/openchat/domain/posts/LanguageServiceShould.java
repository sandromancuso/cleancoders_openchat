package org.openchat.domain.posts;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class LanguageServiceShould {
    
    private LanguageService languageService = new LanguageService();

    @Test
    @Parameters({
            "elephant",
            "ELEPHANT",
            "ELEphaNT",
            "grey ELEphaNT",
            "orange",
            "ORANGE",
            "ORAnge",
            "big Orange juice",
            "ice CREAM",
            "ice cream",
    })
    public void
    inform_when_a_text_has_inappropriate_language(String text) {
        assertThat(languageService.isInappropriate(text)).isTrue();
    }

    @Test public void
    determine_when_text_has_appropriate_language() {
        assertThat(languageService.isInappropriate("OK text")).isFalse();
    }
}