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

#include "endianness.h"

enum {
    kLittleEndian = 2,
    kBigEndian = 1,
    kUnknownEndian = 0
};

int endian() {
#if ENDIAN_ORDER == _LITTLE_ENDIAN
    return kLittleEndian;
#elif ENDIAN_ORDER == _BIG_ENDIAN
    return kBigEndian;
#elif ENDIAN_ORDER==_PDP_ENDIAN
    return kUnknownEndian;
#else
    return kUnknownEndian;
#endif
}