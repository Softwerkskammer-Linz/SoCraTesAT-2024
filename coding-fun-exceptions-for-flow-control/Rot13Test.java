import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Rot13Test {

    public String encode(String plainText) {
        try {
            // could: plaintText.replaceAll('Ä',...)

            char firstCharacter = plainText.charAt(0);
            return encode(firstCharacter) + encode(plainText.substring(1));

        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    private String encode(char plaintTextCharacter) {
        int encoded = Character.toUpperCase(plaintTextCharacter);

        // TODO move low level code into series of methods
        try {

            // could: map['Ä'] lookup
            replaceUmlaut(encoded, 'Ä', "AE");
            replaceUmlaut(encoded, 'Ö', "OE");
            replaceUmlaut(encoded, 'Ü', "UE");

            testIfSmallerThan(encoded, 'A');
            testIfLargerThan(encoded, 'Z');
            encoded += 13;

            // could: encoded = (encoded - 'A' ) % 26 + 'A';
            testIfSmallerThan(encoded, 'Z' + 1);
            encoded -= 26;

        } catch (SmallerThan | LargerThan outOfRange) {
        } catch (ReplacedUmlaut containedUmlaut) {
            return encode(containedUmlaut.replacement);
        }
        return Character.toString((char) encoded);
    }

    static class ReplacedUmlaut extends Exception {
        public final String replacement;

        public ReplacedUmlaut(String replacement) {
            this.replacement = replacement;
        }
    }

    private void replaceUmlaut(int encoded, int umlaut, String replacement) throws ReplacedUmlaut {
        try {
            testIfSmallerThan(encoded, umlaut);
            testIfLargerThan(encoded, umlaut);
            throw new ReplacedUmlaut(replacement);
        } catch (SmallerThan | LargerThan notUmlaut) {
        }

    }

    // --- control flow library

    static class SmallerThan extends Exception {
        public SmallerThan(Throwable cause) {
            super(cause);
        }
    }

    private void testIfSmallerThan(int encoded, int limit) throws SmallerThan {
        try {
            @SuppressWarnings("unused")
            int unused = 1 / (encoded / limit);
        } catch (ArithmeticException e) {
            throw new SmallerThan(e);
        }
    }

    static class LargerThan extends Exception {
        public LargerThan(Throwable cause) {
            super(cause);
        }
    }

    private void testIfLargerThan(int encoded, int limit) throws LargerThan {
        try {
            testIfSmallerThan(limit, encoded);
        } catch (SmallerThan e) {
            throw new LargerThan(e);
        }
    }

    // --- tests

    @Test
    void shouldEncodeEmpty() {
        assertEquals("", encode(""));
    }

    @ParameterizedTest
    @CsvSource({ "A,N", "B,O", "M,Z", //
            "N,A", "O,B", "Z,M", "U,H", //
            "a,N", //
            "ABZ,NOM", //
            "!1!!?@,!1!!?@", "[{~,[{~", //
            "Ä,NR", "ÖÜäöü,BRHRNRBRHR" })
    void shouldEncode(String plaintText, String expected) {
        assertEquals(expected, encode(plaintText));
    }

}
