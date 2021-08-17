package com.gdu.myapplicationac103;

/**
 * Description:
 * Created by Czm on 2021/7/14 11:12.
 */
public class Ns {

    private static volatile Ns ns;

    public static Ns getin() {
        if (ns == null) {
            synchronized (Ns.class) {
                if (ns == null) {
                    ns = new Ns();
                }
            }
        }
        return ns;
    }
}