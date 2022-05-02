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

#ifndef SRC_ENDIANNESS_H
#define SRC_ENDIANNESS_H

#define _LITTLE_ENDIAN 0x41424344UL
#define _BIG_ENDIAN    0x44434241UL
#define _PDP_ENDIAN    0x42414443UL
#define ENDIAN_ORDER  ('ABCD')

extern int endian();

#endif //SRC_ENDIANNESS_H
