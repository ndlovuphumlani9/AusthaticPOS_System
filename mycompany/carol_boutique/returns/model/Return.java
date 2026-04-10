/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.returns.model;

import com.mycompany.carol_boutique.ibt.model.Item;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Mrqts
 */
@AllArgsConstructor@NoArgsConstructor@Setter@Getter
public class Return {
    List<Item> items;
    Reason reason;
    Outcome outcome;
}
