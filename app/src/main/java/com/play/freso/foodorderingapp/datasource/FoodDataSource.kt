package com.play.freso.foodorderingapp.datasource

import com.play.freso.foodorderingapp.models.FoodItemPost

class FoodDataSource {
    companion object{
        fun createDataSet(catName: String): ArrayList<FoodItemPost>{
            val list = ArrayList<FoodItemPost>()

            when (catName){
                "Breakfast" -> {

                }
                "Lunch" -> {
                    list.add(
                        FoodItemPost(
                            "",
                            "Lunch",
                            "Mac and Cheese",
                            "5.99",
                            "kljeda srhfjkas dkljfsd jk ldsh asdj fhaksjdhf sdfjhjskh jhdjkfh lasjhaksd jfh jsdhfk jsadhfs kl",
                            "https://upload.wikimedia.org/wikipedia/commons/4/44/Original_Mac_n_Cheese_.jpg"
                        )
                    )

                    list.add(
                        FoodItemPost(
                            "",
                            "Lunch",
                            "Burger",
                            "6.99",
                            "kljeda srhfjkas dkljfsd jk ldsh asdj fhaksjdhf sdfjhjskh jhdjkfh lasjhaksd jfh jsdhfk jsadhfs kl",
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/RedDot_Burger.jpg/300px-RedDot_Burger.jpg"
                        )
                    )

                }
                "Dinner" -> {
                    list.add(
                        FoodItemPost(
                            "",
                            "Dinner",
                            "Spaghetti and Meatballs",
                            "9.99",
                            "kljeda srhfjkas dkljfsd jk ldsh asdj fhaksjdhf sdfjhjskh jhdjkfh lasjhaksd jfh jsdhfk jsadhfs kl",
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Spaghetti_and_meatballs_1.jpg/248px-Spaghetti_and_meatballs_1.jpg"
                        )
                    )

                    list.add(
                        FoodItemPost(
                            "",
                            "Dinner",
                            "Pizza",
                            "7.99",
                            "kljeda srhfjkas dkljfsd jk ldsh asdj fhaksjdhf sdfjhjskh jhdjkfh lasjhaksd jfh jsdhfk jsadhfs kl",
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/NYPizzaPie.jpg/220px-NYPizzaPie.jpg"
                        )
                    )


                    list.add(
                        FoodItemPost(
                            "",
                            "Dinner",
                            "Fettuccine Alfredo",
                            "10.99",
                            "kljeda srhfjkas dkljfsd jk ldsh asdj fhaksjdhf sdfjhjskh jhdjkfh lasjhaksd jfh jsdhfk jsadhfs kl",
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dc/The_Only_Original_Alfredo_Sauce_with_Butter_and_Parmesano-Reggiano_Cheese.png/220px-The_Only_Original_Alfredo_Sauce_with_Butter_and_Parmesano-Reggiano_Cheese.png"
                        )
                    )
                }
                "Sides" -> {

                }
                "Drinks" -> {
                    list.add(
                        FoodItemPost(
                            "",
                            "Drinks",
                            "Coca-Cola",
                            "1.45",
                            "kljeda srhfjkas dkljfsd jk ldsh asdj fhaksjdhf sdfjhjskh jhdjkfh lasjhaksd jfh jsdhfk jsadhfs kl",
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/15-09-26-RalfR-WLC-0098.jpg/800px-15-09-26-RalfR-WLC-0098.jpg"
                        )
                    )


                    list.add(
                        FoodItemPost(
                            "",
                            "Drinks",
                            "Fanta",
                            "1.45",
                            "kljeda srhfjkas dkljfsd jk ldsh asdj fhaksjdhf sdfjhjskh jhdjkfh lasjhaksd jfh jsdhfk jsadhfs kl",
                            "https://products1.imgix.drizly.com/ci-fanta-fcd342eaf8e54ea3.jpeg?auto=format%2Ccompress&ch=Width%2CDPR&fm=jpg&q=20"
                        )
                    )


                    list.add(
                        FoodItemPost(
                            "",
                            "Drinks",
                            "Sprite",
                            "1.45",
                            "kljeda srhfjkas dkljfsd jk ldsh asdj fhaksjdhf sdfjhjskh jhdjkfh lasjhaksd jfh jsdhfk jsadhfs kl",
                            "https://sc04.alicdn.com/kf/Uc71a39a589f14619aaf57e6953c8620fG.jpg"
                        )
                    )


                    list.add(
                        FoodItemPost(
                            "",
                            "Drinks",
                            "Water",
                            "0.99",
                            "kljeda srhfjkas dkljfsd jk ldsh asdj fhaksjdhf sdfjhjskh jhdjkfh lasjhaksd jfh jsdhfk jsadhfs kl",
                            "https://i.stack.imgur.com/Bvvvi.jpg"
                        )
                    )

                }
            }

            return list
        }
    }
}