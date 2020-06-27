package com.agile4j.utils.util

import com.agile4j.utils.enumeration.ChineseZodiacEnum
import com.agile4j.utils.enumeration.ConstellationEnum
import com.agile4j.utils.enumeration.GenderEnum
import com.agile4j.utils.enumeration.ProvinceEnum
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class IdCardUtilTest {

    private val cnIdCard = "222323196608271521"
    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    @Test
    fun testValidate() {
        assertEquals(true, IdCardUtil.validate(cnIdCard))
        assertEquals(currentYear - 1966, IdCardUtil.getAge(cnIdCard))
        assertEquals("1966-08-27", IdCardUtil.getStandardBirthDay(cnIdCard))
        assertEquals("19660827", IdCardUtil.getNoHyphenBirthDay(cnIdCard))
        assertEquals(1966, IdCardUtil.getBirthYear(cnIdCard))
        assertEquals(8, IdCardUtil.getBirthMonth(cnIdCard))
        assertEquals(27, IdCardUtil.getBirthDate(cnIdCard))
        assertEquals(GenderEnum.WOMAN, IdCardUtil.getGender(cnIdCard))
        assertEquals(ProvinceEnum.JI_LIN, IdCardUtil.getProvince(cnIdCard))
        assertEquals(ConstellationEnum.VIRGO, IdCardUtil.getConstellation(cnIdCard))
        assertEquals(ChineseZodiacEnum.HORSE, IdCardUtil.getChineseZodiac(cnIdCard))
        assertEquals("丙午", IdCardUtil.getChineseEra(cnIdCard))
    }

}