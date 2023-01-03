package com.jangjh123.allpouse_android

import com.jangjh123.allpouse_android.data.model.Perfume
import org.junit.Assert.assertEquals
import org.junit.Test

class AllPouseTest {
    @Test
    fun getPerfumeInfo() {
        val testPerfume = Perfume(
            brandId = 1,
            brandName = "testBrand",
            id = 10,
            imagePath = listOf("image"),
            perfumeName = "TestPerfume"
        )

        val testReview = Triple(
            10,
            "TestPerfume",
            "testBrand"
        )

        assertEquals(
            "TestPerfume", if (testPerfume.id == testReview.first) {
                testReview.second
            } else {
                " "
            }
        )
    }
}