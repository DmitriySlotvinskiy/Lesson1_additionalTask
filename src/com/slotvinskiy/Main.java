//2. Заданы строка с выражением состоящая из чисел и знаков плюс/минус разделенным пробелом, например "123 + 7 - 52". Вычислить значение выражения.
//2.1. Также в выражении могут присутстовать знаки умножить и разделить. Вычислить значение строки с учетом приоритета операторов.


package com.slotvinskiy;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        String instance = "70 * 7 * 4 * 2 / 2 * 2 + 2 - 2";
        String[] operandsAndValues = instance.split("\\s");
        String[] operands = new String[operandsAndValues.length / 2];
        double[] values = new double[operandsAndValues.length / 2 + 1];
        splitOnNumbersAndOperands(operandsAndValues, operands, values);
        double result = solution(values, operands);
        System.out.println("The result:\n" + instance + " = " + result);

    }

    private static double solution(double[] values, String[] operands) {
        if (values.length == 1) {
            return 0;
        }
        for (int i = 0; i < operands.length && operands != null; i++) {
            if (operands[i].equals("/")) {
                double solution = values[i] = values[i] / values[i + 1];
                values = recResult(values, i, solution);
                operands = recResult(operands, i);
                i--;
                if (operands != null) {
                    continue;
                } else {
                    return values[0];
                }
            }
        }
        for (int i = 0; i < operands.length && operands != null; i++) {
            if (operands[i].equals("*")) {
                double solution = values[i] = values[i] * values[i + 1];
                values = recResult(values, i, solution);
                operands = recResult(operands, i);
                i--;
                if (operands != null) {
                    continue;
                } else {
                    return values[0];
                }
            }
        }
        double solution = 0;
        while (operands != null) {
            int i = 0;
            if (operands[i].equals("+")) {
                solution = values[i] + values[i + 1];
            } else {
                solution = values[i] - values[i + 1];
            }
            values = recResult(values, i, solution);
            operands = recResult(operands, i);

        }
        return values[0];
    }

    private static double[] recResult(double[] values, int index, double solution) {
        values[index] = solution;
        for (int i = index + 1; i < values.length - 1; i++) {
            swap(values, i, i + 1);
        }
        values = Arrays.copyOfRange(values, 0, values.length - 1);
        return values;
    }

    private static String[] recResult(String[] operands, int index) {
        if (operands.length == 1) {
            return null;
        }
        for (int i = index; i < operands.length - 1; i++) {
            swap(operands, i, i + 1);
        }
        operands = Arrays.copyOfRange(operands, 0, operands.length - 1);
        return operands;
    }

    private static void swap(double[] arr, int index1, int index2) {
        double temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    private static void swap(String[] arr, int index1, int index2) {
        String temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    private static void splitOnNumbersAndOperands(String[] operandsAndValues, String[] operands, double[] values) {
        int valuesCounter = 0;
        int operandsCounter = 0;
        for (int i = 0; i < operandsAndValues.length; i++) {
            boolean isOperand = isStringOperand(operandsAndValues[i]);
            if (isOperand) {
                operands[operandsCounter] = operandsAndValues[i];
                operandsCounter++;
            } else {
                values[valuesCounter] = Integer.parseInt(operandsAndValues[i]);
                valuesCounter++;
            }
        }
    }

    private static boolean isStringOperand(String string) {
        if (string.equals("+") || string.equals("-") || string.equals("/") || string.equals("*")) {
            return true;
        }
        return false;
    }
}
