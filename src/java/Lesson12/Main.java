package Lesson12;

public class Main {


        public static void main(String[] args) {
            String[][] array = {
                    {"1", "2", "3", "4"},
                    {"5", "6", "a", "8"},
                    {"9", "10", "11", "12"},
                    {"13", "14", "15", "16"}
            };

            try {
                int sum = sumArray(array);
                System.out.println("Сумма элементов массива: " + sum);
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }

        public static int sumArray(String[][] array) throws IllegalArgumentException {
            // Проверяем размер массива
            if (array.length != 4) {
                throw new IllegalArgumentException("Неправильный размер массива! Количество строк должно быть 4.");
            }

            for (int i = 0; i < array.length; i++) {
                if (array[i].length != 4) {
                    throw new IllegalArgumentException("Неправильный размер массива! Количество столбцов в строке " + i + " должно быть 4.");
                }
            }

            int sum = 0;

            // Обход массива и суммирование элементов
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    try {
                        sum += Integer.parseInt(array[i][j]);
                    } catch (NumberFormatException e) {
                        // В случае ошибки преобразования к числу выбрасываем исключение с детализацией
                        throw new NumberFormatException("Неверные данные в массиве [" + i + "][" + j + "]: " + array[i][j]);
                    }
                }
            }

            return sum;
        }
    }

