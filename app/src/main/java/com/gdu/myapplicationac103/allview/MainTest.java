package com.gdu.myapplicationac103.allview;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Description:
 * Created by Czm on 2021/8/25 11:16.
 */
public class MainTest {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        BigDecimal a, b;
        while (cin.hasNext()){
            a = cin.nextBigDecimal(); b = cin.nextBigDecimal();
            System.out.println(""+(a.add(b)));
        }
    }
}
