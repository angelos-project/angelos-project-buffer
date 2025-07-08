/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.io.buf.json

import org.angproj.sec.util.ensure
import org.angproj.utf.Ascii
import org.angproj.utf.alphabetOf


public enum class JsonParser: Parser {
    JSON {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    VALUE {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    OBJECT {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    MEMBERS {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    MEMBER {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    ARRAY {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    ELEMENTS {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    ELEMENT {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    STRING {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    CHARACTERS {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    CHARACTER {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    ESCAPE {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    HEX {
        override fun parse(lexer: Lexer, start: Int): Int {
            TODO("Not yet implemented")
        }
    },
    NUMBER {
        override fun parse(lexer: Lexer, start: Int): Int {
            return INTEGER.parse(lexer, start).let { FRACTION.parse(lexer, it) }.let { EXPONENT.parse(lexer, it) }
        }
    },
    INTEGER {
        override fun parse(lexer: Lexer, start: Int): Int {
            val minusSize = lexer.txt.predicate(start) { it == Ascii.PRNT_HYPHEN.toInt() }
            val onenineSize = lexer.txt.predicate(start + minusSize) { it in onenine }
            val digitSize = lexer.txt.predicate(start + minusSize) { it in digit }
            return if(onenineSize > 0) {
                DIGITS.parse(lexer, start + minusSize + onenineSize)
            } else if(digitSize > 0) {
                start + minusSize + digitSize
            } else {
                ensure { JsonParserException("$this expected at ${lexer.position()}") }
            }
        }
    },
    DIGITS {
        override fun parse(lexer: Lexer, start: Int): Int {
            return DIGIT.parse(lexer, start).let { lexer.txt.parse(it) { it in digit } }
        }
    },
    DIGIT {
        override fun parse(lexer: Lexer, start: Int): Int {
            val size = lexer.txt.predicate(start) { it in digit }
            return when(size) {
                0 -> ensure { JsonParserException("$this expected at ${lexer.position()}") }
                else -> start + size
            }
        }
    },
    ONENINE {
        override fun parse(lexer: Lexer, start: Int): Int {
            val size = lexer.txt.predicate(start) { it in onenine }
            return when(size) {
                0 -> ensure { JsonParserException("$this expected at ${lexer.position()}") }
                else -> start + size
            }
        }
    },
    FRACTION {
        override fun parse(lexer: Lexer, start: Int): Int {
            val size = lexer.txt.predicate(start) { it == Ascii.PRNT_PERIOD.toInt() }
            return when(size) {
                0 -> start
                else -> (start + size).let { DIGITS.parse(lexer, it) }
            }
        }
    },
    EXPONENT {
        override fun parse(lexer: Lexer, start: Int): Int {
            val size = lexer.txt.predicate(start) { it in exponent }
            return when(size) {
                0 -> start
                else -> (start + size).let { SIGN.parse(lexer, it)}.let { DIGITS.parse(lexer, it) }
            }
        }
    },
    SIGN {
        override fun parse(lexer: Lexer, start: Int): Int {
            return start + lexer.txt.predicate(start) { it in sign }
        }
    },
    WS {
        override fun parse(lexer: Lexer, start: Int): Int {
            return start + lexer.txt.parse(start) { it in whiteSpace }
        }
    };

    public companion object {
        public val digit: Set<Int> by lazy {
            alphabetOf(Ascii.PRNT_ZERO) + onenine
        }

        public val onenine: Set<Int> by lazy {
            alphabetOf(
                Ascii.PRNT_ONE, Ascii.PRNT_TWO, Ascii.PRNT_THREE,
                Ascii.PRNT_FOUR, Ascii.PRNT_FIVE, Ascii.PRNT_SIX,
                Ascii.PRNT_SEVEN, Ascii.PRNT_EIGHT, Ascii.PRNT_NINE
            )
        }

        public val exponent: Set<Int> by lazy {
            alphabetOf(
                Ascii.PRNT_E_UP, Ascii.PRNT_E_LOW,
            )
        }

        public val sign: Set<Int> by lazy {
            alphabetOf(
                Ascii.PRNT_PLUS, Ascii.PRNT_HYPHEN,
            )
        }

        public val whiteSpace: Set<Int> by lazy {
            alphabetOf(
                Ascii.PRNT_SPACE, Ascii.CTRL_LF,
                Ascii.CTRL_CR, Ascii.CTRL_HT
            )
        }
    }
}