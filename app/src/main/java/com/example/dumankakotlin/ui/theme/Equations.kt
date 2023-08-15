package com.example.dumankakotlin

import java.util.LinkedList
import java.util.Random


class Equations {
    var list = LinkedList<String>()
    var flag = true

    init {
        var a: Int
        var b: Int
        var c: Int
        var d: Int = 0
        for (i in 0..9) {
            a = i
            for (j in 0..9) {
                b = j
                for (k in 0..9) {
                    c = k
                    d = a + b + c
                    if (a + b + c == d && d <= 9 && d >= 0) {
                        sendequation(a, '+', b, '+', c, '=', d)
                    }
                    d = a + b - c
                    if (a + b - c == d && d <= 9 && d >= 0) {
                        sendequation(a, '+', b, '-', c, '=', d)
                    }
                    d = a + b * c
                    if (a + b * c == d && d <= 9 && d >= 0) {
                        sendequation(a, '+', b, '*', c, '=', d)
                    }
                    if (c != 0 && b % c == 0) {
                        d = a + b / c
                        if (a + b / c == d && d <= 9 && d >= 0) {
                            sendequation(a, '+', b, '/', c, '=', d)
                        }
                    }
                    d = a - b + c
                    if (a - b + c == d && d <= 9 && d >= 0) {
                        sendequation(a, '-', b, '+', c, '=', d)
                    }
                    d = a - b - c
                    if (a - b - c == d && d <= 9 && d >= 0) {
                        sendequation(a, '-', b, '-', c, '=', d)
                    }
                    d = a - b * c
                    if (a - b * c == d && d <= 9 && d >= 0) {
                        sendequation(a, '-', b, '*', c, '=', d)
                    }
                    if (c != 0 && b % c == 0) {
                        d = a - b / c
                        if (a - b / c == d && d <= 9 && d >= 0) {
                            sendequation(a, '-', b, '/', c, '=', d)
                        }
                    }
                    d = a * b + c
                    if (a * b + c == d && d <= 9 && d >= 0) {
                        sendequation(a, '*', b, '+', c, '=', d)
                    }
                    d = a * b - c
                    if (a * b - c == d && d <= 9 && d >= 0) {
                        sendequation(a, '*', b, '-', c, '=', d)
                    }
                    d = a * b * c
                    if (a * b * c == d && d <= 9 && d >= 0) {
                        sendequation(a, '*', b, '*', c, '=', d)
                    }
                    if (c != 0 && b % c == 0) {
                        d = a * b / c
                        if (a * b / c == d && d <= 9 && d >= 0) {
                            sendequation(a, '*', b, '/', c, '=', d)
                        }
                    }
                    if (a != 0 && b != 0 && a % b == 0) {
                        d = a / b + c
                        if (a / b + c == d && d <= 9 && d >= 0) {
                            sendequation(a, '/', b, '+', c, '=', d)
                        }
                        d = a / b + c
                        if (a / b - c == d && d <= 9 && d >= 0) {
                            sendequation(a, '/', b, '-', c, '=', d)
                        }
                        d = a / b * c
                        if (a / b * c == d && d <= 9 && d >= 0) {
                            sendequation(a, '/', b, '*', c, '=', d)
                        }
                        if (c != 0) {
                            d = a / b / c
                            if (a / b / c == d && d <= 9 && d >= 0 && (b % c == 0 || a % c == 0)) {
                                sendequation(a, '/', b, '/', c, '=', d)
                            }
                        }
                    }
                }
            }
        }
        for (i in 0..9) {
            a = i
            for (j in 10..99) {
                b = j
                d = a + b
                if (a + b == d && d >= 10 && d <= 99) {
                    sendequation(a, '+', b, '=', d)
                }
                d = a * b
                if (a * b == d && d >= 10 && d <= 99) {
                    sendequation(a, '*', b, '=', d)
                }
            }
        }
        for (i in 10..99) {
            a = i
            for (j in 0..9) {
                b = j
                d = a + b
                if (a + b == d && d >= 10 && d <= 99) {
                    sendequation(a, '+', b, '=', d)
                }
                d = a * b
                if (a * b == d && d >= 10 && d <= 99) {
                    sendequation(a, '*', b, '=', d)
                }
                d = a - b
                if (a - b == d && d >= 10 && d <= 99) {
                    sendequation(a, '-', b, '=', d)
                }
                if (a != 0 && b != 0 && a % b == 0) {
                    d = a / b
                    if (a / b == d && d >= 10 && d <= 99) {
                        sendequation(a, '/', b, '=', d)
                    }
                }
            }
        }
        for (i in 10..99) {
            a = i
            for (j in 10..99) {
                b = j
                d = a - b
                if (a - b == d && d < 10 && d >= 0) {
                    sendequation(a, '-', b, '=', d)
                }
                if (a != 0 && b != 0 && a % b == 0) {
                    d = a / b
                    if (a / b == d && d < 10 && d >= 0) {
                        sendequation(a, '/', b, '=', d)
                    }
                }
            }
        }
    }

    fun sendequation(a: Int, q: Char, b: Int, w: Char, d: Int) {
        val sb = StringBuilder()
        sb.append(a)
        sb.append(q)
        sb.append(b)
        sb.append(w)
        sb.append(d)
        addtoList(sb.toString())
    }

    fun sendequation(a: Int, q: Char, b: Int, w: Char, c: Int, e: Char, d: Int) {
        val sb = StringBuilder()
        sb.append(a)
        sb.append(q)
        sb.append(b)
        sb.append(w)
        sb.append(c)
        sb.append(e)
        sb.append(d)
        addtoList(sb.toString())
    }

    fun addtoList(s: String) {
        list.add(s)
    }

    fun returnEquation(): String {
        val rand = Random()
        val random: Int
        random = rand.nextInt(list.size)
        return list[random]
    }
}