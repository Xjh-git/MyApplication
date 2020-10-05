package com.example.recipes.Rank;

public class production {
    private int productionImage;
    private int workerImage;
    private String productionName;
    private String productionGrade;
    private String workerName;
    public production(int productionImage,int workerImage,String productionName,String grade,String worker){
        this.productionImage=productionImage;
        this.workerImage=workerImage;
        this.productionName=productionName;
        this.productionGrade=grade;
        this.workerName=worker;
    }

    public int getProductionImage() {
        return productionImage;
    }
    public int getWorkerImage(){
        return workerImage;
    }

    public String getProductionGrade() {
        return productionGrade;
    }

    public String getProductionName() {
        return productionName;
    }

    public String getWorkerName() {
        return workerName;
    }
}
