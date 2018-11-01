package com.example.masonwest.lifestyle_app;

/**
 * Class filters out values that are close approximations to steps
 */
public class StepFilter {

    private StepFilter() {}

    /**
     * Sums all values of passed in array
     * @param array
     * @return
     */
    public static float sum(float[] array) {
        float returnSum = 0;
        for (int i = 0; i < array.length; i++) {
            returnSum += array[i];
        }
        return returnSum;
    }

    /**
     * Solves the cross product of two arrays for 3 dimensions
     * @param arrayA
     * @param arrayB
     * @return
     */
    public static float[] cross(float[] arrayA, float[] arrayB) {
        float[] returnArray = new float[3];
        returnArray[0] = arrayA[1] * arrayB[2] - arrayA[2] * arrayB[1];
        returnArray[1] = arrayA[2] * arrayB[0] - arrayA[0] * arrayB[2];
        returnArray[2] = arrayA[0] * arrayB[1] - arrayA[1] * arrayB[0];
        return returnArray;
    }

    /**
     * Normalizes Vector
     * @param array
     * @return
     */
    public static float norm(float[] array) {
        float returnVal = 0;
        for (int i = 0; i < array.length; i++) {
            returnVal += array[i] * array[i];
        }
        return (float) Math.sqrt(returnVal);
    }

    /**
     * Solves the dot product of two arrays
     * @param a
     * @param b
     * @return
     */
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
