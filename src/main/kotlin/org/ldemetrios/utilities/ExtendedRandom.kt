@file:Suppress("unused")

package org.ldemetrios.utilities

import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

@Suppress("MemberVisibilityCanBePrivate")
class ExtendedRandom(private val random: Random) {
    constructor(owner: Class<*>) : this(Random(8045702385702345702L + owner.name.hashCode()))

    fun randomString(length: Int, charset: String): String {
        val sb = StringBuilder(length)
        for (i in 0..<length) sb.append(randomChar(charset))
        return sb.toString()
    }

    fun randomString(charset: String, denominator: Int = 2, numerator: Int = 1): String {
        val sb = StringBuilder().append(randomChar(charset))
        while (random.nextInt(denominator) < numerator && sb.length != Int.MAX_VALUE) {
            sb.append(randomChar(charset))
        }
        return sb.toString()
    }

    fun randomChar(charset: String) = charset[random.nextInt(charset.length)]


    fun nextBoolean(): Boolean {
        return random.nextBoolean()
    }

    fun nextInt(): Int {
        return random.nextInt()
    }

    fun nextInt(min: Int, max: Int): Int {
        return nextInt(max - min + 1) + min
    }

    fun nextInt(n: Int): Int {
        return random.nextInt(n)
    }

    @SafeVarargs
    fun <T> randomItem(vararg items: T): T {
        return items[nextInt(items.size)]
    }

    fun <T> randomItem(items: List<T>): T {
        return items[nextInt(items.size)]
    }

    fun getRandom(): Random {
        return random
    }

    fun <T> random(list: Int, generator: (ExtendedRandom) -> T): List<T> {
        return Stream.generate { generator(this) }.limit(list.toLong()).collect(Collectors.toUnmodifiableList())
    }

    fun nextDouble(): Double {
        return random.nextDouble()
    }

    fun <E> shuffle(all: List<E>) = all.shuffled(random)


    @Suppress("SpellCheckingInspection")
    companion object {
        const val ENGLISH = "abcdefghijklmnopqrstuvwxyz"
        const val RUSSIAN = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
        const val GREEK = "αβγŋδεζηθικλμνξοπρτυφχψω"
        const val GERMAN = "abcdefghijklmnopqrstuvwxyzäöüß"
        const val SPANISH = "abcdefghijklmnopqrstuvwxyzáéíóúÁÉÍÓÚñÑ"
        const val FRENCH = "abcdefghijklmnopqrstuvwxyzàâçéèêëîïôûùüÿñæœ"
        const val DUTCH = "abcdefghijklmnopqrstuvwxyzäëïöüß"
        const val PORTUGUESE = "abcdefghijklmnopqrstuvwxyzáéíóúñ"
        const val ITALIAN = "abcdefghijklmnopqrstuvwxyzàéìòóùç"
        const val CZECH = "abcdefghijklmnopqrstuvwxyzčďěíňóřšťýúůáéñ"
        const val POLISH = "abcdefghijklmnopqrstuvwxyząęłńóśźż"
        const val TURKISH = "abcdefghijklmnopqrstuvwxyzçğıöşü"
        const val ROMANIAN = "abcdefghijklmnopqrstuvwxyzăâîșț"
        const val HUNGARIAN = "abcdefghijklmnopqrstuvwxyzáéíóúñ"
        const val CATALAN = "abcdefghijklmnopqrstuvwxyzáéíóúñ"
        const val SWEDISH = "abcdefghijklmnopqrstuvwxyzäåö"
        const val DANISH = "abcdefghijklmnopqrstuvwxyzæøå"
        const val NORWAY = "abcdefghijklmnopqrstuvwxyzæøå"
        const val SPACES = " \t\n\u000B\u2029\u000c"
        const val JAPANESE = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆらりるれろわをん"
        const val CHINESE = "一二三四五六七八九十"
        const val KOREAN = "가나다라마바사아자차카타파하"
        const val HINDI = "अपनीचेवलकज"
        const val PUNCTUATION = "!@#$%^&*()_+-=[]{};':\",./<>?"
        val setsMap = mapOf(
            "English" to ENGLISH,
            "Russian" to RUSSIAN,
            "Greek" to GREEK,
            "German" to GERMAN,
            "Spanish" to SPANISH,
            "French" to FRENCH,
            "Dutch" to DUTCH,
            "Portuguese" to PORTUGUESE,
            "Italian" to ITALIAN,
            "Czech" to CZECH,
            "Polish" to POLISH,
            "Turkish" to TURKISH,
            "Romanian" to ROMANIAN,
            "Hungarian" to HUNGARIAN,
            "Catalan" to CATALAN,
            "Swedish" to SWEDISH,
            "Danish" to DANISH,
            "Norwegian" to NORWAY,
            "Spaces" to SPACES,
            "Japanese" to JAPANESE,
            "Chinese" to CHINESE,
            "Korean" to KOREAN,
            "Hindi" to HINDI,
            "Punctuation" to PUNCTUATION
        )

        val sets = setsMap.values
    }

    fun randomStuff(minDepth: Int = 0, maxDepth: Int = 4, minWidth: Int = 1, maxWidth: Int = 4): Any? {
        fun inside() = randomStuff(minDepth - 1, maxDepth -1, minWidth, maxWidth)
        val plain = minDepth <= 0
        val complex = maxDepth > 0
        val plainOptions = 7
        val complexOptions = 5
        val option = if (plain && complex) {
            nextInt(plainOptions + complexOptions)
        } else if (plain) {
            nextInt(plainOptions)
        } else if (complex) {
            plainOptions + nextInt(complexOptions)
        } else return null
        return when (option) {
            0  -> null
            1  -> nextInt()
            2  -> nextDouble()
            3  -> randomChar(ENGLISH)
            4  -> nextBoolean()
            5  -> randomString(ENGLISH, 3, 2)
            6  -> random.nextLong()
            7  -> Pair(inside(), inside())
            8  -> Triple(inside(), inside(), inside())
            9  -> List(nextInt(minWidth, maxWidth + 1)) { inside() }
            10 -> List(nextInt(minWidth, maxWidth + 1)) { inside() }.toSet()
            11 -> List(nextInt(minWidth, maxWidth + 1)) { inside() to inside() }.toMap()
            else -> throw IllegalStateException()
        }
    }
}