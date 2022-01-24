package com.iljin.apiServer.template.asset;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class AssetDetailDto implements Serializable {
    String year;
    String month;
    String day;
    String hour;
    String min;
    String lotNo;
    Double mainHeaterSv;
    Double mainHeaterPv;
    Double mainHeaterOut;
    Double bottomHeaterSv;
    Double bottomHeaterPv;
    Double bottomHeaterOut;
    Double temp;
    Double totalWeight;


    public AssetDetailDto(String year, String month, String day, String hour, String min, String lotNo, Double mainHeaterSv, Double mainHeaterPv, Double mainHeaterOut, Double bottomHeaterSv, Double bottomHeaterPv, Double bottomHeaterOut, Double temp, Double totalWeight) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.lotNo = lotNo;
        this.mainHeaterSv = mainHeaterSv;
        this.mainHeaterPv = mainHeaterPv;
        this.mainHeaterOut = mainHeaterOut;
        this.bottomHeaterSv = bottomHeaterSv;
        this.bottomHeaterPv = bottomHeaterPv;
        this.bottomHeaterOut = bottomHeaterOut;
        this.temp = temp;
        this.totalWeight = totalWeight;
    }

}