package com.example.masonwest.lifestyle_app;

public class StepFilter {

    private StepFilter() {}

    public static float sum(float[] array) {
        float returnSum = 0;
        for (int i = 0; i < array.length; i++) {
            returnSum += array[i];
        }
        return returnSum;
    }

    public static float[] cross(float[] arrayA, float[] arrayB) {
        float[] returnArray = new float[3];
        returnArray[0] = arrayA[1] * arrayB[2] - arrayA[2] * arrayB[1];
        returnArray[1] = arrayA[2] * arrayB[0] - arrayA[0] * arrayB[2];
        returnArray[2] = arrayA[0] * arrayB[1] - arrayA[1] * arrayB[0];
        return returnArray;
    }

    public static float norm(float[] array) {
        float returnVal = 0;
        for (int i = 0; i < array.length; i++) {
            returnVal += array[i] * array[i];
        }
        return (float) Math.sqrt(returnVal);
    }


    public static float dot(float[] a, float[] b) {
        float returnVal = a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
        return returnVal;
    }

    public static float[] normalize(float[] a) {
        float[] returnVal = new float[a.length];
        float norm = norm(a);
        for (int i = 0; i < a.length; i++) {
            returnVal[i] = a[i] / norm;
        }
        return returnVal;
    }

}
