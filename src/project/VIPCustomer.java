package project;

import java.util.Random;

public class VIPCustomer extends Customer{
    public VIPCustomer(String n, Random random) {
        super(n, random);
    }

    @Override
    protected int getIrritation(){
        return 5;
    }

    @Override
    protected double getDiscount(){
        return 0.9;
    }

    @Override
    public String getType(){
        return "VIP";
    }
}
