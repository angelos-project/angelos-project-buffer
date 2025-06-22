package org.angproj.io.buf.mem

import org.angproj.io.buf.util.DataSize

public class SingleMemoryPool(
    allocationSize: DataSize,
    segmentSize: DataSize,
) : MemoryPool(allocationSize, segmentSize, segmentSize) {
    // This class is intentionally left empty. It serves as a concrete implementation of BytesPool
    // that can be used to create instances of BytesPool with arbitrary sizes.
    // The actual allocation and recycling logic is handled in the BytesPool class.
}