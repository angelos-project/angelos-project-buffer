package org.angproj.io.buf

/**
 * Retrievable interface to retrieve data from a buffer
 *
 * @constructor Create empty Retrievable
 */
interface Retrievable {
    /**
     * Retrieve a byte from said position.
     *
     * @return a byte of data
     */
    fun getRetrieveByte(position: Int): Byte

    /**
     * Retrieve an unsigned byte from said position.
     *
     * @return an unsigned byte of data
     */
    fun getRetrieveUByte(position: Int): UByte

    /**
     * Retrieve a character from said position.
     *
     * @return a character of data
     */
    fun getRetrieveChar(position: Int): Char

    /**
     * Retrieve a short integer from said position.
     *
     * @return a short integer of data
     */
    fun getRetrieveShort(position: Int): Short

    /**
     * Retrieve an unsigned short integer from said position.
     *
     * @return an unsigned short integer of data
     */
    fun getRetrieveUShort(position: Int): UShort

    /**
     * Retrieve an integer from said position.
     *
     * @return an integer of data
     */
    fun getRetrieveInt(position: Int): Int

    /**
     * Retrieve an unsigned integer from said position.
     *
     * @return an unsigned integer of data
     */
    fun getRetrieveUInt(position: Int): UInt

    /**
     * Retrieve a long integer from said position.
     *
     * @return a long integer of data.
     */
    fun getRetrieveLong(position: Int): Long

    /**
     * Retrieve an unsigned long integer from said position.
     *
     * @return an unsigned long integer of data
     */
    fun getRetrieveULong(position: Int): ULong

    /**
     * Retrieve a float from said position.
     *
     * @return a float of data
     */
    fun getRetrieveFloat(position: Int): Float

    /**
     * Retrieve a double from said position.
     *
     * @return a double of data
     */
    fun getRetrieveDouble(position: Int): Double
}