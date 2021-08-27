package com.gdu.myapplicationac103;

/**
 * Description:
 * Created by Czm on 2021/8/20 14:20.
 */
public class IWantFreeImpl implements IWantFree {


    @Override
    public void add() {
        try {
            DCSService.add();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sub() {
        try {
            DCSService.sub();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void set(int num) {
        try {
            DCSService.setNum(num);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
