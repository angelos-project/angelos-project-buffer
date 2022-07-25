package org.angproj.io.buf

/**
 * Storable interface to save data to a buffer.
 *
 * @constructor Create empty Storable
 */
interface Storable {
    /**
     * Store a byte unto said position.
     *
     * @param value a byte of data
     */
    fun setStoreByte(position: Int, value: Byte)

    /**
     * Store a unsigned byte unto said position.
     *
     * @param value an unsigned byte of data
     */
    fun setStoreUByte(position: Int, value: UByte)

    /**
     * Store character unto said position.
     *
     * @param value a character of data
     */
    fun setStoreChar(position: Int, value: Char)

    /**
     * Store a short integer unto said position.
     *
     * @param value a short integer of data.
     */
    fun setStoreShort(position: Int, value: Short)

    /**
     * Store an unsigned short integer unto said position.
     *
     * @param value an unsigned short integer of data.
     */
    fun setStoreUShort(position: Int, value: UShort)

    /**
     * Store an integer unto said position.
     *
     * @param value an integer of data
     */
    fun setStoreInt(position: Int, value: Int)

    /**
     * Store an unsigned integer unto said position.
     *
     * @param value an unsigned integer of data
     */
    fun setStoreUInt(position: Int, value: UInt)

    /**
     * Store a long integer unto said position.
     *
     * @param value a long integer of data
     */
    fun setStoreLong(position: Int, value: Long)

    /**
     * Store an unsigned long integer unto said position.
     *
     * @param value an unsigned long integer of data
     */
    fun setStoreULong(position: Int, value: ULong)

    /**
     * Store a float unto said position.
     *
     * @param value a float of data
     */
    fun setStoreFloat(position: Int, value: Float)

    /**
     * Store a double unto said position.
     *
     * @param value a double of data
     */
    fun setStoreDouble(position: Int, value: Double)
}