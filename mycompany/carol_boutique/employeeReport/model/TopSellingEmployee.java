/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.employeeReport.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Acer
 */
@Setter 
@Getter
@NoArgsConstructor@AllArgsConstructor
public class TopSellingEmployee {
    private int employeeId;
    private String employeeName;
    private BigDecimal totalSales;
}
