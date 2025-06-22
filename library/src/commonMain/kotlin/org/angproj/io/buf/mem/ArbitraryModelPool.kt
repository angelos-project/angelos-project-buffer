package org.angproj.io.buf.mem

import org.angproj.io.buf.util.DataSize

public class ArbitraryModelPool(
    allocationSize: DataSize,
    minSize: DataSize,
    maxSize: DataSize
) : ModelPool(allocationSize, minSize, maxSize) {
    // This class is intentionally left empty. It serves as a concrete implementation of BytesPool
    // that can be used to create instances of BytesPool with arbitrary sizes.
    // The actual allocation and recycling logic is handled in the BytesPool class.
}