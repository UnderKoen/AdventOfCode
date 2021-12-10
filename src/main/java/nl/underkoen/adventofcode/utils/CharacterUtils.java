package nl.underkoen.adventofcode.utils;

public class CharacterUtils {
    public static Character oppositeBracket(char c) {
        return switch (c) {
            case '(' -> ')';
            case ')' -> '(';
            case '[' -> ']';
            case ']' -> '[';
            case '{' -> '}';
            case '}' -> '{';
            case '<' -> '>';
            case '>' -> '<';
            default -> null;
        };
    }

    public static boolean isOpeningBracket(char c) {
        return switch (c) {
            case '(', '[', '{', '<' -> true;
            default -> false;
        };
    }

    public static boolean isClosingBracket(char c) {
        return switch (c) {
            case ')', ']', '}', '>' -> true;
            default -> false;
        };
    }

    public static boolean isPair(char c1, char c2) {
        if (c1 == c2) return false;
        Character opposite = oppositeBracket(c1);
        if (opposite == null) return false;

        return opposite == c2;
    }
}
