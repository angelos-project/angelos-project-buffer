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
package org.angproj.io.buf

import org.angproj.io.buf.mem.Default
import org.angproj.io.buf.seg.Bytes
import org.angproj.utf.Policy
import java.nio.file.FileSystems
import java.nio.file.Files


public fun BufMgr.textFile(path: String, policy: Policy = Policy.basic): TextBuffer {
    val realPath = FileSystems.getDefault().getPath(path)
    return TextBuffer(Bytes(Default, Files.readAllBytes(realPath)), policy = policy, endian = Platform.endian).also { it.asText().applyPolicy() }
}

