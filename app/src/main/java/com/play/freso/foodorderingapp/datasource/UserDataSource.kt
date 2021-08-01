package com.play.freso.foodorderingapp.datasource
import com.play.freso.foodorderingapp.models.UserData

class UserDataSource {
    companion object{
        fun createDataSet(): ArrayList<UserData>{
            val list = ArrayList<UserData>()


            list.add(
                UserData(
                    "admin",
                    "12345"
                )
            )

            list.add(
                UserData(
                    "user",
                    "pass"
                )
            )

            list.add(
                UserData(
                    "krishan",
                    "patel"
                )
            )

            return list
        }
    }
}