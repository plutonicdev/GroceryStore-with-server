package com.quintus.labs.grocerystore.interfaces;


/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public interface AddorRemoveCallbacks {

    void onAddProduct();

    void onRemoveProduct();

    void updateTotalPrice();
}