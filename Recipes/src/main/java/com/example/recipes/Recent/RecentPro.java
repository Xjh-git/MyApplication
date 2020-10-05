package com.example.recipes.Recent;

public class RecentPro {
    private int productionImage;
    private int workerImage;
    private String productionName;
    private String workerName;


    public RecentPro(int productionImage, int workerImage, String productionName, String worker){
        this.productionImage=productionImage;
        this.workerImage=workerImage;
        this.productionName=productionName;
        this.workerName=worker;
    }

    public int getProductionImage() {
        return productionImage;
    }

    public int getWorkerImage(){
        return workerImage;
    }

    public String getProductionName() {
        return productionName;
    }

    public String getWorkerName() {
        return workerName;
    }
}
