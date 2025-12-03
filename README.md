# Buffer for The Angelos Project™

[![CircleCI](https://circleci.com/gh/angelos-project/angelos-project-buffer/tree/release.svg?style=shield)](https://circleci.com/gh/angelos-project/angelos-project-buffer/tree/release)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/0a19e154711047e19fef3daf79864d9a)](https://www.codacy.com/gh/angelos-project/angelos-project-buffer/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=angelos-project/angelos-project-buffer&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/angelos-project/angelos-project-buffer/badge.svg?branch=release)](https://coveralls.io/github/angelos-project/angelos-project-buffer?branch=release)
[![Maven Central](https://img.shields.io/maven-central/v/org.angproj.io.buf/angelos-project-buffer.svg?label=Maven%20Central)](https://search.maven.org/artifact/org.angproj.io.buf/angelos-project-buffer)

A data-buffer made to be used without old legacy, in a modern asynchronous environment. With all necessary fundaments.

FIXME:
============================
org.angproj.io.buf.mem:
* ModelPool.subAllocate
* Default.allocate
* Memory.copyInto (fixed)

org.angproj.io.buf.util:
* UtilityAware.*.asNet

org.angproj.io.buf.seg:
* Segment.address
* ByteString.checksum

org.angproj.io.buf:
* *Buffer.constructor
* BinaryBuffer.constructor
* Text.textListOf/plusAssign/plusAssign
* Text.CodePointIterator._prev/_pos/_cnt
* SegmentBlock.limitAt
* NativeMemoryManager.release
* AbstractBuffer.copyInto
* AbstractBuffer._isRevOrder/byteSwapping/setEndian