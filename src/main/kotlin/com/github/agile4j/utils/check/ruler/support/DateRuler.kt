package com.github.agile4j.utils.check.ruler.support

import com.github.agile4j.utils.check.CheckResultCodeEnum.DATE_AFTER_FAIL
import com.github.agile4j.utils.check.CheckResultCodeEnum.DATE_AFTER_OR_EQ_FAIL
import com.github.agile4j.utils.check.CheckResultCodeEnum.DATE_BEFORE_FAIL
import com.github.agile4j.utils.check.CheckResultCodeEnum.DATE_BEFORE_OR_EQ_FAIL
import com.github.agile4j.utils.check.CheckResultCodeEnum.DATE_EQ_FAIL
import com.github.agile4j.utils.check.CheckResultCodeEnum.DATE_NOT_NULL_FAIL
import com.github.agile4j.utils.check.CheckResultCodeEnum.DATE_NULL_FAIL
import com.github.agile4j.utils.check.and
import com.github.agile4j.utils.check.ruler.Ruler
import com.github.agile4j.utils.util.DateUtil.isAfter
import com.github.agile4j.utils.util.DateUtil.isAfterOrEq
import com.github.agile4j.utils.util.DateUtil.isBefore
import com.github.agile4j.utils.util.DateUtil.isBeforeOrEq
import com.github.agile4j.utils.util.DateUtil.isEq
import java.util.*

object DateRuler {
    val beNull = beNull()
    val notNull = notNull()

    fun beNull(code: Long = DATE_NULL_FAIL.code, desc: String = DATE_NULL_FAIL.desc)
            = Ruler.ofBeNull<Date?>(code, desc)

    fun notNull(code: Long = DATE_NOT_NULL_FAIL.code, desc: String = DATE_NOT_NULL_FAIL.desc)
            = Ruler.ofNotNull<Date?>(code, desc)

    fun eq(norm: Date, code: Long = DATE_EQ_FAIL.code, desc: String = DATE_EQ_FAIL.desc)
            = notNull and Ruler.of(norm, code, desc, ::isEq)

    fun after(norm: Date, code: Long = DATE_AFTER_FAIL.code, desc: String = DATE_AFTER_FAIL.desc)
            = notNull and Ruler.of(norm, code, desc, ::isAfter)

    fun afterOrEq(norm: Date, code: Long = DATE_AFTER_OR_EQ_FAIL.code, desc: String = DATE_AFTER_OR_EQ_FAIL.desc)
            = notNull and Ruler.of(norm, code, desc, ::isAfterOrEq)

    fun before(norm: Date, code: Long = DATE_BEFORE_FAIL.code, desc: String = DATE_BEFORE_FAIL.desc)
            = notNull and Ruler.of(norm, code, desc, ::isBefore)

    fun beforeOrEq(norm: Date, code: Long = DATE_BEFORE_OR_EQ_FAIL.code, desc: String = DATE_BEFORE_OR_EQ_FAIL.desc)
            = notNull and Ruler.of(norm, code, desc, ::isBeforeOrEq)

}