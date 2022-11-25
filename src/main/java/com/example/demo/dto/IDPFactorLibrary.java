package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IDPFactorLibrary {
    private Long snapId;
    private String className;
    private String factorName;

    public List<IDPFactorLibrary> getList() {
        return new ArrayList<>(Arrays.asList(
                new IDPFactorLibrary(1L, "MacroEconomic", "Factor MacroEconomic"),
                new IDPFactorLibrary(2L, "BEICF", "Factor BEICF"),
                new IDPFactorLibrary(3L, "MacroEconomic", "Factor MacroEconomic"),
                new IDPFactorLibrary(4L, "Reference Data", "Factor Reference Data"),
                new IDPFactorLibrary(5L, "BEICF", "Factor BEICF"),
                new IDPFactorLibrary(5L, "Business Volumne", "Factor BEICF")));
    }

//    public int CompareByClassName(IDPFactorLibrary factorLibrary1, IDPFactorLibrary factorLibrary2) {
//        return String.com
//    }
}
