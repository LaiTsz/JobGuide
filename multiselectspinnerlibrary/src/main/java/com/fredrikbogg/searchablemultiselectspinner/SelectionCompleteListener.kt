package com.fredrikbogg.searchablemultiselectspinner

interface SelectionCompleteListener {
    fun onCompleteSelection(selectedItems: ArrayList<SearchableItem>)
}