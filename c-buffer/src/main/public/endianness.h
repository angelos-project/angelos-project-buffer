/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
#include <stdint.h>

#ifndef SRC_ENDIANNESS_H
#define SRC_ENDIANNESS_H

#define _LITTLE_ENDIAN 0x41424344UL
#define _BIG_ENDIAN    0x44434241UL
#define _PDP_ENDIAN    0x42414443UL
#define ENDIAN_ORDER  ('ABCD')

/**
 * Find out the native endianness.
 * @return enum int for the endian
 */
extern int endian();

/**
 * Copy between memory locations using max size integer per iteration.
 * @param dest pointer of destination memory location
 * @param src pointer of source memory location
 * @param n amount of bytes to copy
 * @return
 */
extern void speedmemcpy(void *dest, const void * src, uint32_t n);


/**
 * Reset memory bank using max size integer per iteration.
 * @param s pointer of memory location
 * @param n amount of bytes to reset
 * @return
 */
extern void speedbzero(void *s, uint32_t n);

#endif //SRC_ENDIANNESS_H
