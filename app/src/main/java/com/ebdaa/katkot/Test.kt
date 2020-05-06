package com.ebdaa.katkot

import java.util.ArrayList

object Test {

    //


    //
     val periods: List<TestPeriod>
        get() {
            val test1 = TestPeriod("مزرعة عليوة", 16, "13-2-2109_2.40 AM ")
            val test2 = TestPeriod("مزرعة دواجن", 5, "13-2-2109_2.40 AM ")
            val test3 = TestPeriod("مزرعة عاشور", 3, "13-2-2109_2.40 AM ")
            val test4 = TestPeriod("مزرعة كتكوت", 33, "13-2-2109_2.40 AM ")
            val test8 = TestPeriod("مزرعة عليوة", 16, "13-2-2109_2.40 AM ")
            val test5 = TestPeriod("مزرعة دواجن", 5, "13-2-2109_2.40 AM ")
            val test6 = TestPeriod("مزرعة عاشور", 3, "13-2-2109_2.40 AM ")
            val test7 = TestPeriod("مزرعة كتكوت", 33, "13-2-2109_2.40 AM ")

            val list = ArrayList<TestPeriod>()
            list.add(test1)
            list.add(test2)
            list.add(test3)
            list.add(test4)
            list.add(test5)
            list.add(test6)
            list.add(test7)
            list.add(test8)


            return list
        }


     class TestPeriod(var farmName: String, var numPeriod: Int, var date: String)


}
