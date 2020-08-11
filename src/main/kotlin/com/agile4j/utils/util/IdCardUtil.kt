package com.agile4j.utils.util

import com.agile4j.utils.enumeration.ChineseZodiacEnum
import com.agile4j.utils.enumeration.ConstellationEnum
import com.agile4j.utils.enumeration.GenderEnum
import com.agile4j.utils.enumeration.ProvinceEnum
import org.apache.commons.lang3.StringUtils
import java.util.*
import java.util.stream.Collectors

/**
 * 身份证号工具类(只适用于18位大陆身份证号)
 * @author: liurenpeng
 * @date: Created in 18-7-13
 */
object IdCardUtil {

    /**
     * 中国公民身份证号码长度。
     */
    private const val ID_CARD_LENGTH = 18

    /**
     * 每位加权因子
     */
    private val POWER = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)

    /**
     * 前17位受加权因子影响的和与11取模后的值与第18位值的映射
     */
    private val POWER_TO_LAST = mapOf(10 to '2', 9 to '3', 8 to '4', 7 to '5', 6 to '6', 5 to '7', 4 to '8', 3 to '9', 2 to 'x', 1 to '0', 0 to '1')

    /**
     * 验证身份证是否合法
     */
    fun validate(idCard: String): Boolean {
        if (idCard.length != ID_CARD_LENGTH) return false
        val front17Part = idCard.substring(0, ID_CARD_LENGTH - 1)
        if (!StringUtil.isDigit(front17Part)) return false
        val lastChar = getLastCharByFront17Part(front17Part)
        return lastChar == idCard.last()
    }

    /**
     * 获取年龄
     */
    fun getAge(idCard: String): Int {
        assertBeIdCard(idCard)
        return Calendar.getInstance().get(Calendar.YEAR) - idCard.substring(6, 10).toInt()
    }

    /**
     * 获取生日 yyyy-MM-dd
     */
    fun getStandardBirthDay(idCard: String): String? {
        val birth = getNoHyphenBirthDay(idCard)
        return StringUtils.join(birth.substring(0, 4), "-", birth.substring(4, 6),
                "-", birth.substring(6, 8))
    }

    /**
     * 获取生日 yyyyMMdd
     */
    fun getNoHyphenBirthDay(idCard: String): String {
        assertBeIdCard(idCard)
        return idCard.substring(6, 14)
    }

    /**
     * 获取生日年 yyyy
     */
    fun getBirthYear(idCard: String): Int {
        assertBeIdCard(idCard)
        return idCard.substring(6, 10).toInt()
    }

    /**
     * 获取生日月 MM
     */
    fun getBirthMonth(idCard: String): Int {
        assertBeIdCard(idCard)
        return idCard.substring(10, 12).toInt()
    }

    /**
     * 获取生日天 dd
     */
    fun getBirthDate(idCard: String): Int {
        assertBeIdCard(idCard)
        return idCard.substring(12, 14).toInt()
    }

    /**
     * 获取性别
     */
    fun getGender(idCard: String): GenderEnum {
        assertBeIdCard(idCard)
        if ((idCard.substring(16, 17).toInt()) % 2 == 0) {
            return GenderEnum.WOMAN
        }
        return GenderEnum.MAN
    }

    /**
     * 获取户籍省份
     */
    fun getProvince(idCard: String): ProvinceEnum {
        assertBeIdCard(idCard)
        return ProvinceEnum.getByCode(idCard.substring(0, 2).toInt())
    }

    /**
     * 获取星座
     */
    fun getConstellation(idCard: String): ConstellationEnum {
        assertBeIdCard(idCard)
        return ConstellationEnum.getByMonthAndDay(getBirthMonth(idCard), getBirthDate(idCard))
    }

    /**
     * 获取生肖
     */
    fun getChineseZodiac(idCard: String): ChineseZodiacEnum {
        assertBeIdCard(idCard)
        val index = (getBirthYear(idCard) - 3) % 12
        return enumValues<ChineseZodiacEnum>().first { zodiac -> zodiac.ordinal == index }
    }

    /**
     * 获取天干地支
     */
    fun getChineseEra(idCard: String): String {
        assertBeIdCard(idCard)
        val sTG = arrayOf("癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "任")
        val sDZ = arrayOf("亥", "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌")
        return StringUtils.join(sTG[(getBirthYear(idCard) - 3) % 10],
                sDZ[(getBirthYear(idCard) - 3) % 12])
    }

    /**
     * 将Char数组转为Int数组,例如['1','2','3']转成[1,2,3]
     */
    private fun convertCharArrToIntArr(cArr: CharArray): IntArray =
            cArr.toList().stream().map { (it + "").toInt() }
                    .collect(Collectors.toList()).toIntArray()

    private fun getLastCharByFront17Part(front17Part: String) =
            POWER_TO_LAST[getPowerSum(convertCharArrToIntArr(front17Part.toCharArray())) % 11]

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     */
    private fun getPowerSum(iArr: IntArray): Int {
        if (iArr.size != POWER.size) throw IllegalArgumentException("illegal iArr:$iArr")
        return iArr.indices.toList().stream()
                .map { iArr[it] * POWER[it] }.reduce { it, i -> it + i }.get()
    }
    
    private fun assertBeIdCard(idCard: String) {
        if (!StringUtil.isIdCard(idCard)) throw IllegalArgumentException("illegal idCard:$idCard")
    }
}
