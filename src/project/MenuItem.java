package project;

public enum MenuItem implements Dish {
          SALAD("Salad",900,1),
          BURGER("Burger",1500,1),
          PASTA("Pasta",1800,2);

          private final String name;
          private final int priceCents;
          private final int prepUnits;

          MenuItem(String name, int priceCents, int prepUnits){
                    this.name=name;
                    this.priceCents=priceCents;
                    this.prepUnits=prepUnits;
                    
          }

          public String getName(){return name;}
          public int priceCents(){return priceCents;}
          public int prepUnits(){return prepUnits;}
}
