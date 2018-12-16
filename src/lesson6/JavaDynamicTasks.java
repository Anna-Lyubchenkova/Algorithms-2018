package lesson6;

import kotlin.NotImplementedError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    // Трудоёмкость Т = О(N^2);
    // Ресурсоёмкость R = O(N);
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() == 0 || list.size() == 1) {
            return list;
        }
        int d[] = new int[list.size()];
        int p[] = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            d[i] = 1;
            p[i] = -1;
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i))
                    if (1 + d[j] > d[i]) {
                        d[i] = 1 + d[j];
                        p[i] = j;
                    }
            }
        }

        int result = d[0], pos = 0;
        for (int i = 0; i < list.size(); i++) {
            if (d[i] > result) {
                result = d[i];
                pos = i;
            }
        }

        int order[] = new int[list.size()];
        int size = 0;
        while (pos != -1) {
            order[size] = pos;
            pos = p[pos];
            size++;
        }

        List<Integer> res = new ArrayList<>();
        for (int j = size - 1; j >= 0; j--) {
            res.add(list.get(order[j]));
        }

        return res;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    // Трудоёмкость Т = О(n*m);
    // Ресурсоёмкость R = O(n*m);
    public static int shortestPathOnField(String inputName) throws IOException {
        
        List<String> lines = Files.readAllLines(Paths.get(inputName));
        int height = lines.size();
        int width = lines.get(0).split("\\s+").length;
        int[][] field = IntStream.range(0, height).mapToObj(i ->
                Stream.of(lines.get(i).split("\\s+")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);

        for (int j = width - 1; j >= 0; j--) {
            for (int i = height - 1; i >= 0; i--) {
                if (j == width - 1 && i == height - 1)
                    continue;
                if (i == height - 1)
                    field[i][j] += field[i][j + 1];
                else if (j == width - 1)
                    field[i][j] += field[i + 1][j];
                else
                    field[i][j] += Math.min(
                            Math.min(field[i + 1][j], field[i][j + 1]),
                            field[i + 1][j + 1]
                    );
            }
        }

        return field[0][0];
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
