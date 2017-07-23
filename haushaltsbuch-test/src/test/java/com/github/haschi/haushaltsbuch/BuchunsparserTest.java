package com.github.haschi.haushaltsbuch;

import org.junit.Test;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class BuchunsparserTest
{
    @Test
    public void buchungstext_parsen() {
        final Matcher matcher = Pattern.compile("\\d*").matcher("1234");
        final boolean b = matcher.find();
        final MatchResult result = matcher.toMatchResult();

        assertThat(result.group()).isEqualTo("1234");
    }

    @Test
    public void text_parsen() {
        final Matcher matcher = Pattern.compile("^(\\w*) (\\d*,\\d\\d EUR)$").matcher("Sparbuch 3500,00 EUR");




        assertThat(matcher.find()).isTrue();
        final MatchResult result = matcher.toMatchResult();
        assertThat(result.group(1)).isEqualTo("Sparbuch");
        assertThat(result.group(2)).isEqualTo("3500,00 EUR");
    }
}
