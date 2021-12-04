package nl.underkoen.adventofcode.utils;

import java.util.function.BiFunction;

public class MatrixUtils {
    /**
     * @see <a href="https://stackoverflow.com/a/1092721/6098780">StackOverflow</a>
     */
    public static int[][] rotateMatrixRight(int[][] matrix) {
        if (matrix.length == 0) return matrix;

        /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        int[][] ret = new int[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[w - j - 1][i];
            }
        }
        return ret;
    }

    public static int[][] rotateMatrixLeft(int[][] matrix) {
        if (matrix.length == 0) return matrix;

        /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        int[][] ret = new int[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[j][h - i - 1];
            }
        }
        return ret;
    }

    public static long[][] rotateMatrixRight(long[][] matrix) {
        if (matrix.length == 0) return matrix;

        /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        long[][] ret = new long[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[w - j - 1][i];
            }
        }
        return ret;
    }

    public static long[][] rotateMatrixLeft(long[][] matrix) {
        if (matrix.length == 0) return matrix;

        /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        long[][] ret = new long[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[j][h - i - 1];
            }
        }
        return ret;
    }

    public static boolean[][] rotateMatrixRight(boolean[][] matrix) {
        if (matrix.length == 0) return matrix;

        /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        boolean[][] ret = new boolean[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[w - j - 1][i];
            }
        }
        return ret;
    }

    public static boolean[][] rotateMatrixLeft(boolean[][] matrix) {
        if (matrix.length == 0) return matrix;

        /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        boolean[][] ret = new boolean[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[j][h - i - 1];
            }
        }
        return ret;
    }

    /**
     * @param createMatrix should create a matrix as (h, w) -> new Boolean[h][w];
     */
    public static <T> T[][] rotateMatrixRight(T[][] matrix, BiFunction<Integer, Integer, T[][]> createMatrix) {
        if (matrix.length == 0) return matrix;

        /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        T[][] ret = createMatrix.apply(h, w);
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[w - j - 1][i];
            }
        }
        return ret;
    }

    /**
     * @param createMatrix should create a matrix as (h, w) -> new Boolean[h][w];
     */
    public static <T> T[][] rotateMatrixLeft(T[][] matrix, BiFunction<Integer, Integer, T[][]> createMatrix) {
        if (matrix.length == 0) return matrix;

        /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        T[][] ret = createMatrix.apply(h, w);
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[j][h - i - 1];
            }
        }
        return ret;
    }
}
