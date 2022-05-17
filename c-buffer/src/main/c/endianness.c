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

void speedmemcpy(void *dest, const void * src, uint32_t n) {
#if INTPTR_MAX == INT64_MAX
    uint32_t big = n / sizeof(uint64_t);
    uint32_t small = n % sizeof(uint64_t);

    uint64_t *dest_big = (uint64_t *) dest;
    uint64_t *src_big = (uint64_t *) src;

    for (uint32_t i = 0; i < big; i++) {
        dest_big[i] = src_big[i];
    }

    char *dest_small = (char *) dest + big * sizeof(uint64_t);
    char *src_small = (char *) src + big * sizeof(uint64_t);
#elif INTPTR_MAX == INT32_MAX
    uint32_t big = n / sizeof(uint32_t);
    uint32_t small = n % sizeof(uint32_t);

    uint32_t *dest_big = (uint32_t *) dest;
    uint32_t *src_big = (uint32_t *) src;

    for (uint32_t i = 0; i < big; i++) {
        dest_big[i] = src_big[i];
    }

    char *dest_small = (char *) dest + big * sizeof(uint32_t);
    char *src_small = (char *) src + big * sizeof(uint32_t);
#else
#error "Environment must be 64-bit or 32-bit"
#endif
    for (uint32_t j = 0; j < small; j++) {
        dest_small[j] = src_small[j];
    }
}