package ua.example;

import org.junit.Test;
import org.junit.Assert;
import java.util.Arrays;
import java.util.List;

public class MethodsTest{
    Methods methods = new Methods();

    @Test
    public void twoSum() {
        Assert.assertEquals(Arrays.toString(new int[]{0,1}),Arrays
                .toString(methods.twoSum(new int[] {2,7,11,15}, 9)));

        Assert.assertEquals(Arrays.toString(new int[]{1,2}),Arrays
                .toString(methods.twoSum(new int[] {3,2,4}, 6)));

        Assert.assertEquals(Arrays.toString(new int[]{0,1}),Arrays
                .toString(methods.twoSum(new int[] {3,3}, 6)));
    }

    @Test
    public void fibonacciNumber() {
        Assert.assertEquals(1,methods.fibonacciNumber(2));
        Assert.assertEquals(2,methods.fibonacciNumber(3));
        Assert.assertEquals(3,methods.fibonacciNumber(4));
        Assert.assertEquals(233,methods.fibonacciNumber(13));
    }


    @Test
    public void pascalsTriangle() {
        List<List<Integer>> allRaws = Arrays.asList(Arrays.asList(1),
                Arrays.asList(1,1),Arrays.asList(1,2,1),
                Arrays.asList(1,3,3,1),Arrays.asList(1,4,6,4,1));
        Assert.assertEquals(allRaws,methods.pascalsTriangle(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void pascalsTriangleWithZeroArgument() {
        methods.pascalsTriangle(0);
    }
}