package com.app.taybatApplication.data.model

 object  BagSharedPrefrences {

     private val listItems: MutableList<ItemsWithMainPrice> = mutableListOf()
     private val listPrices: MutableList<PriceModel> = mutableListOf()
     private val listBagModel: MutableList<BagModel> = mutableListOf()
     private val listItemWithInnerPrice: MutableList<ItemsWithInnerPrice> = mutableListOf()
     private val listproductDetails: MutableList<ProductDetail> = mutableListOf()



     fun setBagData(item:ItemsWithMainPrice){
         listItems.add(item)
     }

     fun retreiveListItems(): MutableList<ItemsWithMainPrice>
     {
         return listItems
     }
     ///////////////////////////////////////

     fun setBagData(item:BagModel){
         listBagModel.add(item)
     }

     fun retreiveBagData():MutableList<BagModel>
     {
         return listBagModel
     }

     //////////////////////////////////////////

     fun setListPrice(price:PriceModel)
     {
              listPrices.add(price)

     }
     fun retreiveListPrice():MutableList<PriceModel>
     {
         return listPrices
     }
     /////////////////////////////////////////////////////////////////////////////

     fun setlistproductDetails(item:ProductDetail)
     {
         listproductDetails.add(item)

     }
     fun retreivelistproductDetails():MutableList<ProductDetail>
     {
         return listproductDetails
     }


     ///////////////////////////////////////////////////////////////////////

     fun Clear(){

         listItems.clear()
         listPrices.clear()
         listBagModel.clear()

     }




}