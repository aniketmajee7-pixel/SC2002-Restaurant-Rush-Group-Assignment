package project;

import java.util.Random;

public class CriticCustomer extends Customer{
    public CriticCustomer(String n, Random random) {
        super(n, random);
    }

    @Override
    protected int getIrritation(){
        return 12;
    }

    @Override
    protected double getDiscount(){
        return 1;
    }

    @Override
    public String getType(){
        return "Critic";
    }

    @Override
    protected Order makeDesire(Random random){
        return new Order(MenuItem.PASTA);
    }

    @Override
    public void serveOrder(){
        super.serveOrder();
        //Distinct reaction: he becomes happy if you serve him fast
        if (getSatisfaction() >= 64){
            setSatisfaction(getSatisfaction()+12);
        }
        //take too long and he gets angry!
        else if (getSatisfaction() >= 13){
            setSatisfaction(getSatisfaction()-12);
        }
        //take even longer and he gets so hungry the food tastes better
        //this must be on the exact turn before he leaves!!! Plam carefully!
        else{
            setSatisfaction(100);
        }
    }
}
