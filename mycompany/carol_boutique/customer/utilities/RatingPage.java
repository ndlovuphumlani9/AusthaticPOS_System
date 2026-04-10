/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.customer.utilities;

/**
 *
 * @author Mrqts
 */
public class RatingPage {
    public static String generateRatingLink(int storeId) {
        return "http://localhost:8080/Carol_Boutique/rating.jsp?storeId=" + storeId;
    }

}
